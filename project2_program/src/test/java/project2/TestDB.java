package project2;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.*;

import project2.dao.AppUserMapper;
import project2.dto.AppUser;
import project2.service.AppUserSecurityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/root-context.xml", "classpath:config/security-context.xml" })
public class TestDB {
	@Autowired ApplicationContext context;
	@Autowired DataSource ds;
	@Autowired SqlSession sqlSession;
	@Autowired AppUserMapper dao;
	@Autowired PasswordEncoder pwencoder;
	@Autowired AppUserSecurityService service;
	
	@Ignore @Test
	public void test9() {
		AppUser dto = new AppUser();
		MockMultipartFile file = new MockMultipartFile("file","file.txt","text/plain","".getBytes());
		dto.setEmail("6@6");
		dto.setPassword("6");
		dto.setMbtiTypeId(1);
		dto.setUfile("1.png");
		dto.setMobile("010-3423-1233");
		dto.setNickname("한승현");
		System.out.println("9. " + service.insert(file,dto));
	}

	@Ignore @Test
	public void test1() {
		System.out.println(context);
	}

	@Ignore@Test
	public void test2() {
		System.out.println(ds);
	}

	@Ignore@Test
	public void test3() {
		System.out.println(sqlSession);
	}

	@Ignore @Test
	public void test4() {
		AppUser dto = new AppUser();
		dto.setEmail("b@b");
		dto.setPassword("1111");
		dto.setMbtiTypeId(1);
		dto.setUfile("1.png");
		dto.setMobile("010-3423-1233");
		dto.setNickname("한승현");
		System.out.println("4. " + dao.insert(dto));
	}

	@Test
	public void test5() {
		AppUser dto = new AppUser(); 
		dto.setPassword("1234");
		dto.setEmail("6@6");
		dto.setMbtiTypeId(1);
		MockMultipartFile file = new MockMultipartFile("file","file.txt","text/plain","".getBytes());
		dto.setUfile("2.png");
		dto.setMobile("010-3423-1233");
		dto.setNickname("현승한");
		dto.setAppUserId(79);
		System.out.println("5. " + service.update(file,dto));
	}

	@Ignore @Test
	public void test6() {
		AppUser dto = new AppUser();
		dto.setAppUserId(65);
		dto.setPassword("11111");
		dto.setEmail("4@4");
		System.out.println("6. " + service.delete(dto));
	}

	@Ignore @Test
	public void test7() {
		AppUser dto = new AppUser();
		dto.setEmail("1@1");
		System.out.println("7. " + dao.readAuth(dto));
	}

	@Ignore @Test
	public void test8() {
		AppUser dto = new AppUser();
		dto.setEmail("1@1");
		System.out.println("8. " + dao.select(dto));
	}
	

}
