package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComuDao;


public class ComuEditView implements ComuService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 데이터 넘겨받기
		int postId = Integer.parseInt(request.getParameter("postId"));
		// 2. 드커프리
		ComuDao dao = new ComuDao();
		// 3. 데이터 넘겨받기
		request.setAttribute("dto", dao.select(postId));

	}

}
