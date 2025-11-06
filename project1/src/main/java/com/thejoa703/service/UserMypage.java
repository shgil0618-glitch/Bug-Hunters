package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thejoa703.dao.UserDao;
import com.thejoa703.dto.UserDto;

public class UserMypage implements UserService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		//1. 데이터받기
		HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
      
        if (email != null) {
            UserDao dao = new UserDao();
            UserDto user = dao.mypage(email);
            request.setAttribute("user", user);
            request.setAttribute("result", 1);
        }else {
            request.setAttribute("result", 0);
        }
        
        //2. 드커프리
        //3. 넘기기
   
	}
	
}
