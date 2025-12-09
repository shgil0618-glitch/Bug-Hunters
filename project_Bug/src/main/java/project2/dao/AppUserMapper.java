package project2.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import project2.dto.AppUserAuthDto;
import project2.dto.AppUserDto;
import project2.dto.AuthDto;

@MyDao
public interface AppUserMapper {
	public int  insert(AppUserDto dto);
	public int  update(AppUserDto dto);
	public int  delete(AppUserDto dto);
	public List<AppUserDto>  selectAll();
	public AppUserDto  select(int appUserId);
	public AppUserDto  selectEmail(AppUserDto dto);
	public int  selectLogin(AppUserDto dto);
	public AppUserAuthDto readAuth(AppUserDto email);
	public interface AppUserDao { int iddouble(String email); }
	public void insertAuth(AuthDto adto);
	public String findEmail(String mobile);
	public int updatePassword(AppUserDto dto);
	public int nickdouble(@Param("nickname") String nickname);
	public int selectMobile(String mobile);
	public void deleteAuthByEmail(String email);
	public int checkAuth(AuthDto adto);
	public  String selectNicknameByUserId(int appUserId);
	
	public int updateNickname(@Param("appUserId") int appUserId,
            @Param("nickname") String nickname);
}
