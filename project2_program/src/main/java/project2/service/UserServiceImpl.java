package project2.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.UserDao;
import project2.dto.AppUserAuthDto;
import project2.dto.AuthDto;
import project2.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    
    @Autowired PasswordEncoder pwencoder;

    @Override
    public int insert(UserDto dto) {
        try {
            return dao.insert(dto);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return 0;  
        }
    }

    @Override
    public int update(UserDto dto) {
        try {
            return dao.update(dto);
        } catch (DataAccessException e) {
            e.printStackTrace(); 
            return 0;
        }
    }

    @Override public UserDto selectEmail(String email) { return dao.selectEmail(email); }
    
    @Override
    public int delete(UserDto dto) {
        try {
            return dao.delete(dto);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return 0;
        }
    }

    @Override
    public List<UserDto> selectAll() {
        try {
            return dao.selectAll();
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return null;
        }
    }

    @Override
    public UserDto select(int appUserId) {
        try {
            return dao.select(appUserId);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return null;
        }
    }

    @Override
    public UserDto login(UserDto dto) {
        try {
            return dao.login(dto);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return null; 
        }
    }

    @Override
    public int insert2(MultipartFile file, UserDto dto) {
        String fileName = file.getOriginalFilename(); // ���� ���� �̸�
        String uploadPath = "C:/file/";

        if(file.isEmpty()) { // ������ ����ִٸ�
            int randomNum = (int)(Math.random() * 7) + 1; // 1~7 ����
            fileName = randomNum + ".png"; // �⺻ Ȯ����
        }

        File img = new File(uploadPath + fileName);
        try {
            if(!file.isEmpty()) { 
                file.transferTo(img);
            }
            dto.setUfile(fileName);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return dao.insert2(dto);
    }


	@Override
	public int update2(MultipartFile file, UserDto dto) {
		if(!file.isEmpty()) {	// ������ ����ִ°� �ƴ϶��
			String fileName = file.getOriginalFilename();	//���� ���� �̸�
			String uploadPath = "C:/file/";
			File img = new File(uploadPath+fileName);
			try {
				file.transferTo(img);	// ���� �ø���
				dto.setUfile(fileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			return dao.update2(dto);
	}

	@Override
	public int iddouble(String email) {
		try {
            return dao.iddouble(email);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return 0;
        }
	}

	@Override
	public int deleteAdmin(UserDto dto) {
		try {
            return dao.deleteAdmin(dto);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return 0; 
        }
	}

	@Override
	public int updateAdmin(UserDto dto) {
		try {
            return dao.updateAdmin(dto);
        } catch (DataAccessException e) {
            e.printStackTrace();  
            return 0; 
        }
	}

	@Override
	public int insertAuth(AuthDto dto) {
		return dao.insertAuth(dto);
	}

	@Override
	public AppUserAuthDto readAuth(String email) {
		AppUserAuthDto dto = new AppUserAuthDto(); dto.setEmail(email);
		return dao.readAuth(dto);
	}
	
	

	@Transactional
	@Override
	public int insert3(MultipartFile file, UserDto dto) {

	    // 🔥 1) 이메일 중복 체크
	    if (dao.iddouble(dto.getEmail()) > 0) {
	        throw new RuntimeException("이미 존재하는 이메일입니다.");
	    }

	    // 2. 권한 등록
	    AuthDto adto = new AuthDto();
	    adto.setEmail(dto.getEmail());
	    adto.setAuth("ROLE_MEMBER");
	    dao.insertAuth(adto);

	    // 3. 파일 처리
	    String fileName = null;
	    if(!file.isEmpty()) {
	        fileName = file.getOriginalFilename();
	        String uploadPath = "C:/file/";
	        File img = new File(uploadPath + fileName);
	        try {
				file.transferTo(img);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } else {
	        fileName = "user" + ((int)((Math.random()*7)+1)) + ".png";
	    }

	    // 4. 패스워드 암호화
	    dto.setPassword(pwencoder.encode(dto.getPassword()));
	    dto.setUfile(fileName);

	    // 5. 유저 저장
	    return dao.insert2(dto);
	}

	
}
