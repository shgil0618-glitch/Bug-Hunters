package com.thejoa703.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.SerchDao;
import com.thejoa703.dto.SerchDto;

class RecommendList    implements RecommendService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//1. 데이터 넘겨받기	
		    	     
		//2. 드커프리 (PostDao)
		
		 SerchDao dao = new SerchDao();
	        List<SerchDto> list = dao.selectAll();
		
		//3. 데이터 넘겨주기
	        request.setAttribute("list", list);


	}

}
