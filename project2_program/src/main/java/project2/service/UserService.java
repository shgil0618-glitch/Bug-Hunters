package project2.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import project2.dto.AppUserAuthDto;
import project2.dto.AuthDto;
import project2.dto.UserDto;


public interface UserService {
	public int insert(UserDto dto);	
	public int update(UserDto dto);	
	public int delete(UserDto dto);			 
	public List<UserDto> selectAll();	
	public UserDto select(int appUserId);	
	public UserDto  selectEmail(String email);
	public UserDto login(UserDto dto);
	
	//�̹��� ���ε�
	public int insert2( MultipartFile file ,UserDto dto);
	public int update2( MultipartFile file ,UserDto dto);
	 
	/* iddouble */
	public int iddouble(String email);
	
	/* �����ڿ� */
	public int deleteAdmin(UserDto dto);
    public int updateAdmin(UserDto dto);
    
    public int insertAuth(AuthDto dto);
	/* public AppUserAuthDto readAuth (AppUserAuthDto dto); */
	public AppUserAuthDto readAuth(String email);
	
	public int insert3(MultipartFile file, UserDto dto);
	
    
}
