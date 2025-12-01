package project2.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import project2.dto.AppUserAuthDto;
import project2.dto.AuthDto;
import project2.dto.UserDto;

@MyDao
public interface UserDao {

    public int insert(UserDto dto);
    public int update(UserDto dto);
    public int delete(UserDto dto);
    public List<UserDto> selectAll();
    public UserDto select(int appUserId);
    public UserDto  selectEmail(String email);
    public UserDto login(UserDto dto); // �̸���, ��й�ȣ�� �α��� ó��
    
    //�̹��� �߰�
    public int insert2(UserDto dto);
    public int update2(UserDto dto);
    
    public int insert3(MultipartFile file, UserDto dto);
    
	/* iddoutble */
    public int iddouble(String email);
    
    /*������ ���� ��ȸ*/
    public int deleteAdmin(UserDto dto);
    public int updateAdmin(UserDto dto);
    
    /* security */
    /* security */
    public int insertAuth(AuthDto dto);
    public AppUserAuthDto readAuth (AppUserAuthDto dto);
    
    
}
