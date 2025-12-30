package com.thejoa703.service;

import java.util.List;
import com.thejoa703.dto.AdminUserStatusDto;

public interface UserStatusService {

    /** 관리자 페이지용 전체 사용자 조회 */
    List<AdminUserStatusDto> getAllUsers();

    /** 정지 해제 */
    void activateUser(int appUserId);

    /** 관리자 정지 */
    void suspendUser(int appUserId, String reason, String untilDate);

    /** 최초 회원 생성 시 상태 생성 */
    void createDefaultStatus(int appUserId);
}
