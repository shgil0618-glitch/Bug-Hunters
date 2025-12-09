package project2;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project2.dao.MaterialDao;
import project2.dto.MaterialDto;
import project2.service.AppUserSecurityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:config/root-context.xml",
    "classpath:config/security-context.xml"
})
public class TestDB2 {
    @Autowired ApplicationContext context;
    @Autowired DataSource ds;      
    @Autowired SqlSession sqlSession;
    @Autowired MaterialDao dao;
    @Autowired PasswordEncoder pwencoder;
    @Autowired AppUserSecurityService service;
   
    
	

    ///// INSERT ///// 
	
    @Ignore @Test
	public void test() {
//		 insert into material(
//				    materialid, title, imageurl, season, temperature, calories100g,
//				    efficacy, buyguide, trimguide, storeguide
//				) values (
//				      material_seq.nextval,
//				    '메밀면',
//				    'https://대충주소.com/img.jpg',
//				    '연중제철',
//				    '1~5℃',
//				    142,
//				    '소화 촉진',
//				    '유통기한을 확인하고 구입한다.',
//				    '끓는 물에 삶아서 사용한다.',
//				    '직사광선을 피해 보관한다.'
//				);
	MaterialDto dto = new MaterialDto();
	dto.setTitle("메밀묵");
	dto.setImageurl("http://www.naver.com/asdf.png/");
	dto.setSeason("연중제철");
	dto.setTemperature("1~5℃");
	dto.setCalories100g(142);
	dto.setEfficacy("소화 촉진");
	dto.setBuyguide("유통기한울 확인하고 구입한다");
	dto.setTrimguide("끓는 물에 삶아서 사용한다");
	dto.setStoreguide("직사광선을 피해 보관한다");
	System.out.println(dao.insertMaterial(dto));
	} 
	
    @Ignore @Test public void test1() {
    	int materialid =20;
		MaterialDto dto = dao.selectMaterial(materialid);
		System.out.println(dto);
		//select * from material
		//where materialid = 1;
		
		//select * from material
		//order by materialid desc;
	}	
	
	@Ignore @Test public void test2() {
//		update material set 
//		title = '메밀면 (국산)',
//		imageurl = 'https://대충주소.com/defult.png',
//		season = '6월, 7월, 8월',
//		temperature ='1~5℃',
//		calories100g = 150,
//		efficacy = '소화 촉진, 장 건강 도움',
//		buyguide = '면의 색깔 및 상태를 확인후 구매한다',
//		trimguide= '면을 부서서 빻아 매밀 가루로 사용한다.',
//		storeguide = '서늘하고 통풍이 잘 되는 곳에 보관한다.'
//		where materialid =1;
		MaterialDto dto = new MaterialDto();
		dto.setTitle("메밀면(국산)");
		dto.setImageurl("https://대충주소.com/defult.png");
		dto.setSeason("6월, 7월, 8월");
		dto.setTemperature("1~5℃");
		dto.setCalories100g(150);
		dto.setEfficacy("소화촉진, 장건강 도움");
		dto.setBuyguide("면의 색깔 및 상태를 확인 후 구매한다");
		dto.setTrimguide("면을 부서서 매밀 가루로 사용한다.");
		dto.setStoreguide("서늘하고 통풍이 잘 되는 곳에 보관한다.");
		System.out.println(dao.updateMaterial(dto));
	}
	
	@Ignore @Test public void test3() {
//		delete from material where materialid=1;
		int materialid =17;
		System.out.println(dao.deleteMaterial(materialid));		
	}

	@Test public void test4() {
		
	 String title = "메밀묵";
	 MaterialDto dto = dao.selectTitle(title);
	 if(dto != null) {
	        System.out.println("조회 성공!");
	        System.out.println(dto);
	    } else {
	        System.out.println("조회 실패: 데이터 없음");
	    }
	 
	 
 }
	
}
/*
 insert into material(
    materialid, title, imageurl, season, temperature, calories100g,
    efficacy, buyguide, trimguide, storeguide
) values (
      material_seq.nextval,
    '메밀면',
    'https://대충주소.com/img.jpg',
    '연중제철',
    '1~5℃',
    142,
    '소화 촉진',
    '유통기한을 확인하고 구입한다.',
    '끓는 물에 삶아서 사용한다.',
    '직사광선을 피해 보관한다.'
);

select * from material
where materialid = 1;

select * from material
order by materialid desc;



update material set 
title = '메밀면 (국산)',
imageurl = 'https://대충주소.com/defult.png',
season = '6월, 7월, 8월',
temperature ='1~5℃',
calories100g = 150,
efficacy = '소화 촉진, 장 건강 도움',
buyguide = '면의 색깔 및 상태를 확인후 구매한다',
trimguide= '면을 부서서 빻아 매밀 가루로 사용한다.',
storeguide = '서늘하고 통풍이 잘 되는 곳에 보관한다.'
where materialid =1;

delete from material where materialid=1;
 */