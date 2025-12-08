package project2.dto;

import lombok.Data;

@Data
public class AppUserDto {
	private Integer appUserId;
	private String  password;
	private String  email;	
	private String  nickname;
	private String  mobile;
	private String  joindate;
	private String  bfile;
	private String  newPassword;    // 새 비밀번호
    private String  confirmPassword; // 새 비밀번호 확인
		
}

/*

APPUSERID NOT NULL NUMBER        
PASSWORD  NOT NULL VARCHAR2(100) 
NICKNAME  NOT NULL VARCHAR2(50)  
EMAIL              VARCHAR2(100) 
MOBILE             VARCHAR2(20)  
JOINDATE           DATE          
BFILE              VARCHAR2(255) 
*/