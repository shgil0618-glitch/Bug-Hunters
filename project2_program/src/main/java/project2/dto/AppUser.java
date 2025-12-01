package project2.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
	 private int appUserId;          
	    private String email;           
	    private String password;       
	    private Integer mbtiTypeId; 
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	    private LocalDateTime createdAt; // ������
//	    private String createdAt;
	    private String ufile;
	    private String mobile;
	    private String nickname;
}

/*
 create table appuser(
    APP_USER_ID                                NUMBER(5) primary key,
      EMAIL                                    VARCHAR2(100) NOT NULL,
      PASSWORD                                           VARCHAR2(100),
      MBTI_TYPE_ID                                       NUMBER(3),
      CREATED_AT                                   TIMESTAMP(6) default sysdate,
      UFILE                                       VARCHAR2(255),
      MOBILE                                    VARCHAR2(50),
      NICKNAME                                   VARCHAR2(50)
);
 
Q1. 테이블 : appuser    mobile 필드추가 - null             --  스크린샷
Q2. dto            [com.thejoa703.dto] - AppUser
Q3. Dao 필요한 sql   [com.thejoa703.dao] - AppUserMapper
- create (회원가입)                      :
   insert into appuser (APP_USER_ID , EMAIL , PASSWORD, MBTI_TYPE_ID ,UFILE   ,MOBILE ,  NICKNAME)  
   values    (  appuser_seq.nextval , '1@1' , '1',     1            ,'1.png' ,'1'    , 'nick'   )
- read   (로그인, 이메일찾기 , 비밀번호 찾기) :
    * 해당이메일로  email, password, 권한들 가져오기
    select     u.email, password, auth
    from       appuser u    left join   authorities a   on  u.email = a.email  
    where      u.email='1@1'
    
    * 해당이메일로  정보가져오기 - 
    select * from appuser  where  email='1@1'
    
    * 해당이메일로  비밀번호찾기
    select password from appuser  where  email='1@1'
    
- update (정보수정) 해당id의 유저업데이트    : 
    update  appuser  set  MBTI_TYPE_ID=2 ,UFILE='2.png'   ,MOBILE='1' ,  NICKNAME='1'  where   APP_USER_ID=143;
   
- delete (정보삭제) 해당id의 유저삭제       : 
    delete from   appuser   where   APP_USER_ID=143;

*/