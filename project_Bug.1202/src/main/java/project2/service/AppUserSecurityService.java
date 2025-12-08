package project2.service;
import org.springframework.web.multipart.MultipartFile;

import project2.dto.AppUserAuthDto;
import project2.dto.AppUserDto;

public interface AppUserSecurityService {
	public int  insert(MultipartFile file, AppUserDto dto); // 삽입
	public int  update(MultipartFile file, AppUserDto dto); // 수정
	public int  delete(AppUserDto dto);                    
	public AppUserAuthDto readAuth(   String email);        // 로그인
	public AppUserDto     selectEmail(String email);        // 마이페이지
	
	  // 추가: 사용자 ID로 닉네임 조회
    public String selectUserNickname(int appUserId);
    public String selectNicknameByUserId(int appUserId);
	
}
