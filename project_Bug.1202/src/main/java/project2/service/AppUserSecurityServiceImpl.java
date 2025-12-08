package project2.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.AppUserMapper;
import project2.dto.AppUserAuthDto;
import project2.dto.AppUserDto;
import project2.dto.AuthDto;

@Service
public class AppUserSecurityServiceImpl  implements AppUserSecurityService{
	@Autowired AppUserMapper dao;
	@Autowired PasswordEncoder pwencoder;
	

	@Override public int insert(MultipartFile file, AppUserDto dto) {
		
		  AuthDto adto = new AuthDto();
		  adto.setEmail(dto.getEmail()); adto.setAuth("ROLE_MEMBER");
		  dao.insertAuth(adto);
	      String fileName   = null;
	      if(  !file.isEmpty() ) {  // 파일이 비어있는게 아니라면
	         fileName   = file.getOriginalFilename(); // 원본파일이름
	         String uploadPath = "C:/file/";
	         File   img        = new File(uploadPath + fileName);  //java.io.File
	         try { file.transferTo(img); }//파일올리기 
	         catch (IOException e) { e.printStackTrace(); }
	         
	      }else { fileName = "user" + ((int)((Math.random()*7)+1)) + ".png"; }

	      dto.setBfile(fileName);
	      dto.setPassword(pwencoder.encode(dto.getPassword()));
		  return dao.insert(dto); }

	@Override public int update(MultipartFile file, AppUserDto dto) {
		AppUserAuthDto dbUser = dao.readAuth(dto);
		if(dbUser == null) {return 0;}
		
		if(pwencoder.matches(dto.getPassword(), dbUser.getPassword())) {
		//파일 올리기
		 String fileName   = null;
		   if(  !file.isEmpty() ) {  // 파일이 비어있는게 아니라면
			   fileName   = file.getOriginalFilename(); // 원본파일이름
			   String uploadPath = "C:/file/";
			   File   img        = new File(uploadPath + fileName);  //java.io.File
			   try { file.transferTo(img); //파일올리기 
			   }catch (IOException e) { e.printStackTrace(); }
			   
		   }else {fileName = "user" + ((int)((Math.random()*7)+1)) + ".png";}
		   
		   dto.setBfile(fileName); 
		   return dao.update(dto); 
		   
		}else {return 0;}
    }
	@Override public int delete(AppUserDto dto) {
		//db에서 사용자정보조회	
		AppUserAuthDto  dbUser =dao.readAuth(dto);
		if(dbUser == null) {   return 0; }
		System.out.println("........  delete>" + dbUser);
		System.out.println("........  delete> " + pwencoder.matches(dto.getPassword(), dbUser.getPassword()));
		
		//입력한 비밀번호와 db비밀번호를 비교해서 같다면
		if(pwencoder.matches(dto.getPassword(), dbUser.getPassword())) {  //## 두개비교
			return dao.delete(dto); 
		}else { return 0;  }
	}

	@Override public AppUserDto selectEmail(String email) 
	{ AppUserDto dto = new AppUserDto();    dto.setEmail(email);     return dao.selectEmail(dto); }

	@Override public AppUserAuthDto readAuth(String email) 
	{ AppUserDto dto = new AppUserDto();    dto.setEmail(email);     return dao.readAuth(dto); }

	
	
	
	 // 추가: 사용자 ID로 닉네임 조회
    @Override
    public String selectUserNickname(int appUserId) {
        return dao.selectNicknameByUserId(appUserId);
    }

	@Override
	public String selectNicknameByUserId(int appUserId) {
		 return dao.selectNicknameByUserId(appUserId);
	}


  
 

}
