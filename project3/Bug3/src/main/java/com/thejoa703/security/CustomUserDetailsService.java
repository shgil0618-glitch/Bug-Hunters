package com.thejoa703.security;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thejoa703.dao.AppUserDao;
import com.thejoa703.dao.UserStatusDao;
import com.thejoa703.dto.AppUserAuthDto;
import com.thejoa703.dto.AppUserDto;
import com.thejoa703.dto.UserStatusDto;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserDao userDao;

    @Autowired
    private UserStatusDao userStatusDao;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        /*
         * username 형식
         *  - local 로그인  : email
         *  - 통합 처리용  : email:provider
         */
        String[] parts = username.split(":");
        String email = parts[0];
        String provider = parts.length > 1 ? parts[1] : "local";

        AppUserDto param = new AppUserDto();
        param.setEmail(email);
        param.setProvider(provider);

        // 🔹 인증 정보 조회
        AppUserAuthDto authDto = userDao.readAuthByEmail(param);
        if (authDto == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        // 🔹 사용자 기본 정보 조회
        AppUserDto user = userDao.findByEmail(param);
        if (user == null) {
            throw new UsernameNotFoundException("사용자 기본정보를 찾을 수 없습니다.");
        }

        Integer appUserId = user.getAppUserId();

        // 🔥 회원 상태 조회
        UserStatusDto status = userStatusDao.findByAppUserId(appUserId);

        // 🔥🔥 정지 기간 만료 시 자동 복구
        if (
            status != null
            && "SUSPEND".equals(status.getStatus())
            && status.getSuspendUntil() != null
            && status.getSuspendUntil().isBefore(LocalDate.now())
        ) {
            userStatusDao.recoverExpiredSuspension(appUserId);
            status = userStatusDao.findByAppUserId(appUserId);
        }

        // 🔴 아직 정지 상태면 로그인 차단
        if (status != null && "SUSPEND".equals(status.getStatus())) {

            String reason =
                    status.getSuspendReason() != null
                            ? status.getSuspendReason()
                            : "사유 없음";

            throw new DisabledException(
                "활동 정지 상태입니다. 관리자에게 문의해주세요. (사유: " + reason + ")"
            );
        }

        // ✅ 정상 사용자
        return new CustomUserDetails(user, authDto);
    }
}
