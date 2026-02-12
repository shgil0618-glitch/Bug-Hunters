package com.thejoa703.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.thejoa703.oauth2.CustomOAuth2User;
import java.util.Collection;

/**
 * JWT 인증사용자 정보 서비스
 * - Authentication 에서 사용자 정보를 추출하여 제공
 */
@Component
public class AuthUserJwtService { 

    /** 현재 로그인한 사용자 id 반환 */
    public Long getCurrentUserId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            return ((CustomOAuth2User) authentication.getPrincipal()).getId();
        }
        return null; // 혹은 예외 처리
    }

    /** 현재 로그인한 사용자 email 반환 */
    public String getCurrentUserEmail(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            return ((CustomOAuth2User) authentication.getPrincipal()).getEmail();
        }
        return authentication.getName(); // 일반 유저의 경우 username(email) 반환
    }
 
    /** 현재 로그인한 사용자 nickname 반환 */
    public String getCurrentUserNickname(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomOAuth2User) {
            return ((CustomOAuth2User) authentication.getPrincipal()).getNickname();
        }
        return "Unknown";
    }

    /** * ✅ [추가] 현재 로그인한 사용자의 권한(Role) 반환 
     * 리액트에서 user.role을 확인할 때 이 값이 필요합니다.
     */
    public String getCurrentUserRole(Authentication authentication) {
        if (authentication == null) return null;
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER"); // 권한이 없으면 기본 유저 권한 반환
    }
}