package com.thejoa703.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thejoa703.dao.UserStatusDao;
import com.thejoa703.dto.AdminUserStatusDto;
import com.thejoa703.dto.UserStatusDto;
import com.thejoa703.external.ApiEmailNaver;

@Service
public class UserStatusServiceImpl implements UserStatusService {

	@Autowired
	private UserStatusDao userStatusDao;

	@Autowired
	private ApiEmailNaver apiEmailNaver; // ⭐ 여기 추가

	@Override
	@Transactional
	public void suspendUser(int appUserId, String reason, String untilDate) {

		// 1️⃣ 정지 상태 DB 업데이트
		UserStatusDto dto = new UserStatusDto();
		dto.setAppUserId(appUserId);
		dto.setStatus("SUSPEND");
		dto.setSuspendReason(reason);
		dto.setSuspendUntil(LocalDate.parse(untilDate));
		userStatusDao.update(dto);

		// 2️⃣ 사용자 이메일 조회 (관리자용 DTO 사용)
		AdminUserStatusDto user = userStatusDao.findAdminUserByAppUserId(appUserId);

		if (user == null || user.getEmail() == null)
			return;

		// 3️⃣ 메일 발송 ⭐⭐⭐ (← 이게 3번)
		String subject = "[오늘 뭐먹지?] 계정 이용 제한 안내";

		String content = "회원님의 계정이 관리자에 의해 정지되었습니다.<br><br>" + "<b>정지 사유:</b> " + reason + "<br>" + "<b>해제 예정일:</b> "
				+ untilDate + "<br><br>" + "자세한 사항은 관리자에게 문의해주세요.";

		apiEmailNaver.sendMail(subject, content, user.getEmail());
	}

	@Override
	@Transactional(readOnly = true)
	public List<AdminUserStatusDto> getAllUsers() {
		return userStatusDao.findAllUserStatus();
	}

	@Override
	@Transactional
	public void activateUser(int appUserId) {
		UserStatusDto dto = new UserStatusDto();
		dto.setAppUserId(appUserId);
		dto.setStatus("ACTIVE");
		dto.setSuspendReason(null);
		dto.setSuspendUntil(null);
		userStatusDao.update(dto);
	}

	@Override
	@Transactional
	public void createDefaultStatus(int appUserId) {
		UserStatusDto dto = new UserStatusDto();
		dto.setAppUserId(appUserId);
		dto.setStatus("ACTIVE");
		userStatusDao.insert(dto);
	}
}
