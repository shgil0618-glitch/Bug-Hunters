package project2.service;

import org.springframework.web.multipart.MultipartFile;

import project2.dto.AppUser;
import project2.dto.AppUserAuthDto;

public interface AppUserSecurityService {
	public int insert(MultipartFile file, AppUser dto);	// 유저삽입
	public int update(MultipartFile file, AppUser dto); // 유저수정
	public int delete( AppUser dto);	// 유저삭제
	public AppUserAuthDto readAuth(String email);	//로그인
	public AppUser 			selectEmail(String email);	//마이페이지


	
}
