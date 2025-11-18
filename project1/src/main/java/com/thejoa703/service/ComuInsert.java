package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thejoa703.dao.ComuDao;
import com.thejoa703.dto.ComuDto;



public class ComuInsert implements ComuService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 1. 데이터 넘겨받기
		//int app_user_id = Integer.parseInt(request.getParameter("app_user_id"));
		/*
		 * pstmt.setInt(1, dto.getId()); pstmt.setString(2, dto.getTitle());
		 * pstmt.setString(3, dto.getContent()); pstmt.setInt(4, dto.getCategoryId());
		 */
		HttpSession session = request.getSession();
		 // 
			/*
			 * if (session == null || session.getAttribute("id") == null) {
			 * response.setContentType("text/html; charset=UTF-8"); response.getWriter().
			 * println("<script>alert('로그인이 필요합니다.'); location.href='/login.jsp';</script>"
			 * ); return; }
			 */
		
			/* int id = (Integer)session.getAttribute("id"); */ 
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		
		// 2. 디커프리(PostDao) db처리
		ComuDao dao = new ComuDao();
		ComuDto dto = new ComuDto();
		dto.setId(id);
		dto.setTitle(title); dto.setContent(content); dto.setCategoryId(categoryId);
		String result = String.valueOf(dao.insert(dto));	//##
		// 3. 데이터 넘겨주기
		request.setAttribute("result", result);
		

	}

}
