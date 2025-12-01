package project2.dao;

import project2.dto.AppUser;
import project2.dto.AppUserAuthDto;
import project2.dto.AuthDto;

@MyDao
public interface AppUserMapper {
	public int insert(AppUser dto);
	public AppUserAuthDto readAuth(AppUser dto);	//이메일, 패스워드, 권한들
	public AppUser 		  select(AppUser dto);
	public AppUser 		  selectPassword(AppUser dto);
	public int update(AppUser dto);
	public int delete(AppUser dto);
	
	public int insertAuth(AuthDto dto);


}
