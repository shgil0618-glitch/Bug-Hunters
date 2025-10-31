package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.PostDao;
import com.thejoa703.dto.PostDto;

public class MbtiUpdate implements MbtiService {
	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//1. 데이터 넘겨받기 
		String title   = request.getParameter("title");
		String content = request.getParameter("content");
		String pass    = request.getParameter("pass");
		int    id      = Integer.parseInt(   request.getParameter("id")  );
	    System.out.println("............1 : " + id);
		//2. 드커프리
	    PostDao dao = new PostDao(); 
	    PostDto dto = new PostDto();
	    dto.setTitle(title);
	    dto.setContent(content);
	    dto.setPass(pass);
	    dto.setId(id);
	    System.out.println("............2 : " + dto);
		//3. 데이터 넘기기
	    request.setAttribute("id", id);
	    request.setAttribute("result",  String.valueOf( dao.update(dto)) );
	}
}
