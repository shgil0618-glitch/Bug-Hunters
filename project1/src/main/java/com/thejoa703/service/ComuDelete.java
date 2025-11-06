package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComuDao;
import com.thejoa703.dto.ComuDto;



public class ComuDelete implements ComuService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 
		   request.setCharacterEncoding("UTF-8");
		   int postId = Integer.parseInt(request.getParameter("postId"));
		   
		   //2.
		   ComuDao dao = new ComuDao();
		   ComuDto dto = new ComuDto();
		   dto.setPostId(postId);

		   // 3.
		   request.setAttribute("postId", postId);
	       request.setAttribute("result", String.valueOf(dao.delete(dto)));
		   
	}

}
