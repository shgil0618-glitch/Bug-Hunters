package com.thejoa703.oauth2;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.thejoa703.entity.AppUser;
import com.thejoa703.security.JwtProperties;
import com.thejoa703.security.JwtProvider;
import com.thejoa703.security.TokenStore;
import com.thejoa703.service.AppUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AppUserService appUserService;
    private final JwtProvider jwtProvider;
    private final TokenStore tokenStore;
    private final JwtProperties props;

    // ✅ 프론트엔드 파일 경로(pages/oauth2/callback.js)에 맞춰 /callback으로 수정
    @Value("${app.oauth2.redirect-url:http://localhost:3000/oauth2/callback}") 
    private String redirectUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        
        log.info("OAuth2 Login Success! Processing handler...");

        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attrs = oAuth2User.getAttributes();

            String registrationId = ((OAuth2AuthenticationToken) authentication)
                    .getAuthorizedClientRegistrationId();

            UserInfoOAuth2 userInfo;
            switch (registrationId) {
                case "google": userInfo = new UserInfoGoogle(attrs); break;
                case "kakao":  userInfo = new UserInfoKakao(attrs); break;
                case "naver":  userInfo = new UserInfoNaver(attrs); break;
                default: throw new IllegalArgumentException("지원하지 않는 Provider: " + registrationId);
            }

            // DB 조회 또는 저장
            AppUser user = appUserService.findByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider())
                    .orElseGet(() -> appUserService.saveSocialUser(
                            userInfo.getEmail(),
                            userInfo.getProvider(),
                            userInfo.getProviderId(),
                            userInfo.getNickname(),
                            userInfo.getImage()
                    ));

            // ✅ ID 필드 확인 (user.getId()가 맞는지 체크하세요)
            String userIdStr = String.valueOf(user.getId()); 

            // JWT 발급
            String access = jwtProvider.createAccessToken(userIdStr, Map.of(
                    "nickname", user.getNickname(),
                    "provider", user.getProvider(),
                    "role", user.getRole(),
                    "email", user.getEmail()
            ));
            String refresh = jwtProvider.createRefreshToken(userIdStr);

            // Redis 저장
            tokenStore.saveRefreshToken(
                    userIdStr,
                    refresh,
                    (long) props.getRefreshTokenExpSeconds()
            );

            // RefreshToken 쿠키 설정
            Cookie refreshCookie = new Cookie("refreshToken", refresh);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setPath("/");
            refreshCookie.setMaxAge((int) props.getRefreshTokenExpSeconds());
            
            // 로컬 테스트 환경에서는 Secure(false)로 처리
            boolean isLocal = request.getServerName().equals("localhost") || request.getServerName().equals("127.0.0.1");
            refreshCookie.setSecure(!isLocal);
            
            response.addCookie(refreshCookie);

            // ✅ 리다이렉트 실행 (이제 /oauth2/callback?accessToken=... 형식으로 전송됨)
            String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                    .queryParam("accessToken", access)
                    .build().toUriString();
            
            log.info("Redirecting to: {}", targetUrl);
            response.sendRedirect(targetUrl);

        } catch (Exception e) {
            log.error("Error in OAuth2SuccessHandler: ", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "인증 처리 중 오류가 발생했습니다.");
        }
    }
}