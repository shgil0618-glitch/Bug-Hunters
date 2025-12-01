package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComuDao;




public class ComuList implements ComuService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 1. 데이터 넘겨받기 x
		// 2. 드커프리(PostDao)
		ComuDao dao = new ComuDao();
		// 3. 데이터 넘겨주기
		request.setAttribute("list", dao.selectAll());

	}

}
