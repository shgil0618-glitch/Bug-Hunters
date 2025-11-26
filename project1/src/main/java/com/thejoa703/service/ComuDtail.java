package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComuDao;
import com.thejoa703.dto.ComuDto;


public class ComuDtail implements ComuService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 데이터 넘겨받기
		int postId = Integer.parseInt(request.getParameter("postId"));
		System.out.println("....." + postId);
		
		// 2. dao
		ComuDao dao = new ComuDao();
		dao.update_views(postId);
		ComuDto result = dao.select(postId);
		System.out.println("....." + result);
		// 3. 데이터 넘겨주기
		request.setAttribute("dto", result);
		request.getRequestDispatcher("COMUboard/detail.jsp").forward(request, response);
	}

}
