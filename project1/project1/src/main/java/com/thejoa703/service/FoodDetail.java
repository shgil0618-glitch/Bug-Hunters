package com.company.jeongmin.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.jeongmin.Dao_Dto.FoodDao;
import com.company.jeongmin.Dao_Dto.FoodDto;

public class FoodDetail implements FoodService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//데이터 넘겨받기
		//이건 MbtiDetail에서처럼 조회수를 1 올리는 기능이 아닌 원래 있던 정보를 상세보기 하는 기능인데
		int id=Integer.parseInt(request.getParameter("id"));
		//드커프리
		FoodDao dao = new FoodDao();
		FoodDto result = dao.select(id);
		
		request.setAttribute("dto", result);
		
		
		//Dao를 잘못 적은건가... 
		
		//데이터 넘겨주기
	
	}

}
