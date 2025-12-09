package project2.service;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.AppUserMapper;
import project2.dto.AppUserAuthDto;
import project2.dto.AppUserDto;

public interface AppUserSecurityService {
	public int  insert(MultipartFile file, AppUserDto dto); // 삽입
	public int  update(MultipartFile file, AppUserDto dto); // 수정
	public int  delete(AppUserDto dto);                    
	public AppUserAuthDto readAuth(   String email);        // 로그인
	public AppUserDto     selectEmail(String email);        // 마이페이지
	public String findEmailByMobile(String mobile);         // 아이디 찾기
	public int findPassword(String email, String mobile, String newPassword);  // 비밀번호 찾기
	public int selectNickname(String email);
	public int selectMobile(String mobile);
	
    public String selectUserNickname(int appUserId);
    public String selectNicknameByUserId(int appUserId);

}
