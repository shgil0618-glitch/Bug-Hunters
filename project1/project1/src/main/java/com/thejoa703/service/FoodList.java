package com.company.jeongmin.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.jeongmin.Dao_Dto.FoodDao;

public class FoodList implements FoodService{

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//1. 받기
		//2. 드커프리
			FoodDao dao = new FoodDao(); 
			  
			
		//3. 주기	
			request.setAttribute("list", dao.selectAll());
	}
	
	

}
