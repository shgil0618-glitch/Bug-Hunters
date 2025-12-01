package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.SerchDao;
import com.thejoa703.dto.SerchDto;

public class RecommendDelete implements RecommendService {
	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//1.데이터넘겨받기		
		int recId = Integer.parseInt(request.getParameter("recId"));

		
		//2.드커프리
		SerchDao dao = new SerchDao();
		SerchDto dto = new SerchDto();
		dto.setRecId(recId);
		System.out.println(dto);
		
		//3.데이터넘겨주기		 
		request.setAttribute("recId", recId);
	    request.setAttribute("result", String.valueOf(dao.delete(dto)));

	    /*
	              //2.
         ComuDao dao = new ComuDao();
         ComuDto dto = new ComuDto();
         dto.setPostId(postId);

         // 3.
         request.setAttribute("postId", postId);
         request.setAttribute("result", String.valueOf(dao.delete(dto)));
          
	     
	     */
		

	}

}
