package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.PostDao;
import com.thejoa703.dto.PostDto;

public class MbtiDetail implements MbtiService {
	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 데이터 넘겨받기
		int id = Integer.parseInt( request.getParameter("id") );
		//2. dao
		PostDao dao = new PostDao();
		dao.update_hit(id);  //조회수올리기
		PostDto result = dao.select(id);  //해당번호의 값 가져오기   ctrl + shift + o
		//3. 데이터 넘겨주기
		request.setAttribute("dto", result);
	}
}



