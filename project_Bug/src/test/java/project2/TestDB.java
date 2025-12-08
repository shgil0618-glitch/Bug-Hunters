package project2;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project2.dao.AppUserMapper;
import project2.dto.AppUserDto;
import project2.service.AppUserSecurityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:config/root-context.xml",
    "classpath:config/security-context.xml"
})
public class TestDB {
    @Autowired ApplicationContext context;
    @Autowired DataSource ds;      
    @Autowired SqlSession sqlSession;
    @Autowired AppUserMapper dao;
    @Autowired PasswordEncoder pwencoder;
    @Autowired AppUserSecurityService service;

    @Test
    public void test1() {
        // DTO 객체 생성
        AppUserDto dto = new AppUserDto();
        dto.setAppUserId(1);
        dto.setPassword(pwencoder.encode("1"));
        dto.setNickname("1");
        dto.setEmail("1@1");
        dto.setMobile("1");
        dto.setBfile(""); 

     
        int result = dao.insert(dto);
        System.out.println("회원가입 insert 결과: " + result);
   
    }
}
//	@Test
//	public void test() {
//		AppUserDto dto = new AppUserDto();
//		dto.setPassword( "1" );
//		dto.setNickname("name");
//		dto.setEmail("1@1");
//		dto.setMobile("1");
//		dto.setBfile("1.png");
//		MockMultipartFile file = new MockMultipartFile("file", "file.text", "text-plan", "".getBytes());
//		System.out.println(service.insert(file, dto));
//	}
	
//	@Test
//	public void test2() {
//		AppUserDto dto = new AppUserDto();
//		dto.setAppUserId(128);
//		dto.setPassword("3");
//		dto.setEmail("3@3");
//		System.out.println(service.delete(dto));
//	} 
//    @Test
//	public void test3() {
//    	AppUserDto dto = new AppUserDto();
//    	dto.setPassword( "1" );
//		dto.setNickname("name");
//		dto.setEmail("1@1");
//		dto.setMobile("1");
//		dto.setBfile("1.png");
//		MockMultipartFile file = new MockMultipartFile("file2", "file2.text", "text-plan", "".getBytes());
//		dto.setAppUserId(129);
//		System.out.println(service.update(file, dto));
//    }
    
	 

    ///// INSERT ///// 
	/*
	 * @Test public void test() { AppUserDto dto = new AppUserDto();
	 * dto.setAppUserId(2); dto.setPassword(pwencoder.encode("2"));
	 * dto.setNickname("name"); dto.setEmail("2@2"); dto.setMobile("01022111111");
	 * dto.setBfile(""); System.out.println(dao.insert(dto)); }
	 */
    
    ///// UPDEATE/////
	/*
	 * @Test public void test2() { AppUserDto dto = new AppUserDto();
	 * dto.setAppUserId(2); dto.setPassword(pwencoder.encode("2"));
	 * dto.setNickname("name"); dto.setMobile("01022211111"); dto.setBfile("");
	 * System.out.println(dao.update(dto)); }
	 */
    
    ///// DELETE /////
	
	/*
	 * @Test public void test3() { AppUserDto dto = new AppUserDto();
	 * dto.setAppUserId(109); dao.delete(dto); }
	 **/
    
    ///// SELECT /////

	
//	 @Test public void test4() { AppUserDto dto = new AppUserDto();
//	 dto.setEmail("2@2"); System.out.println(dao.selectEmail(dto)); }
	 
    
  
