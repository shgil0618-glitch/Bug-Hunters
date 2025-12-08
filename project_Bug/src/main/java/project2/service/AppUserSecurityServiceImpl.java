package project2.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.AppUserMapper;
import project2.dto.AppUserAuthDto;
import project2.dto.AppUserDto;
import project2.dto.AuthDto;

@Service
public class AppUserSecurityServiceImpl implements AppUserSecurityService {
    @Autowired private AppUserMapper dao;
    @Autowired private PasswordEncoder pwencoder;

    private String handleFileUpload(MultipartFile file) {
        String fileName;
        if (file != null && !file.isEmpty()) {
            fileName = file.getOriginalFilename();
            String uploadPath = "C:/file/";
            File img = new File(uploadPath + fileName);
            try {
                file.transferTo(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileName = "user" + ((int) ((Math.random() * 7) + 1)) + ".png";
        }
        return fileName;
    }

    @Override
    public int insert(MultipartFile file, AppUserDto dto) {
        // 권한 기본값 설정
        AuthDto adto = new AuthDto();
        adto.setEmail(dto.getEmail());
        adto.setAuth("ROLE_MEMBER");

        // 중복 권한 체크 후 insert
        int count = dao.checkAuth(adto);  
        if (count == 0) {
            dao.insertAuth(adto);
        }

        // 파일 처리
        String fileName = handleFileUpload(file);
        dto.setBfile(fileName);

        // 비밀번호 암호화
        dto.setPassword(pwencoder.encode(dto.getPassword()));

        return dao.insert(dto);
    }

    @Override
    public int update(MultipartFile file, AppUserDto dto) {
        // DB에서 현재 사용자 정보 조회
        AppUserAuthDto dbUser = dao.readAuth(dto);
        if (dbUser == null) return 0;

        // 현재 비밀번호 검증
        if (!pwencoder.matches(dto.getPassword(), dbUser.getPassword())) {
            return 0;
        }

        // 닉네임 수정
        dto.setNickname(dto.getNickname());

        // 새 비밀번호가 입력된 경우 변경
        if (dto.getNewPassword() != null && !dto.getNewPassword().isEmpty()) {
            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                return 0; // 새 비밀번호 불일치
            }
            dto.setPassword(pwencoder.encode(dto.getNewPassword()));
        } else {
            // 새 비밀번호 없으면 기존 비밀번호 유지
            dto.setPassword(dbUser.getPassword());
        }

        // 파일 업로드 처리 (선택적으로 유지 가능)
        String fileName = handleFileUpload(file);
        if (fileName != null) {
            dto.setBfile(fileName);
        }
        return dao.update(dto);
    }


    @Override
    public int delete(AppUserDto dto) {
        AppUserAuthDto dbUser = dao.readAuth(dto);
        if (dbUser == null) return 0;

        if (pwencoder.matches(dto.getPassword(), dbUser.getPassword())) {
            return dao.delete(dto);
        } else {
            return 0;
        }
    }

    @Override
    public AppUserDto selectEmail(String email) {
        AppUserDto dto = new AppUserDto();
        dto.setEmail(email);
        return dao.selectEmail(dto);
    }

    @Override
    public AppUserAuthDto readAuth(String email) {
        AppUserDto dto = new AppUserDto();
        dto.setEmail(email);
        return dao.readAuth(dto);
    }

    @Override
    public String findEmailByMobile(String mobile) {
        return dao.findEmail(mobile);
    }

    @Override
    public int findPassword(String email, String mobile, String newPassword) {
        AppUserDto dto = new AppUserDto();
        dto.setEmail(email.trim());
        dto.setMobile(mobile.trim());
        dto.setPassword(pwencoder.encode(newPassword));
        return dao.updatePassword(dto);
    }

    @Override
    public int selectNickname(String nickname) {
        return dao.nickdouble(nickname);
    }

    @Override
    public int selectMobile(String mobile) {
        return dao.selectMobile(mobile);
    }

    @Override
    public String selectUserNickname(int appUserId) {
        return dao.selectNicknameByUserId(appUserId);
    }

	@Override
	public String selectNicknameByUserId(int appUserId) {
		 return dao.selectNicknameByUserId(appUserId);
	}
    
    
}
