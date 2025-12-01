package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thejoa703.dao.UserDao;
import com.thejoa703.dto.UserDto;

public class UserLogin implements UserService {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        

		request.setCharacterEncoding("UTF-8");
    	String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDao dao = new UserDao();
        UserDto dto = dao.login(email, password);

        System.out.println(dto);
        
        if (dto != null) {
            request.setAttribute("result", 1);
<<<<<<< HEAD
            request.setAttribute("email", dto.getEmail());
=======
            request.setAttribute("email", dto.getEMAIL());
>>>>>>> 78738172590cf0a8bddf80d29292165e29240d33
            HttpSession  session = request.getSession();           
            session.setAttribute("email", email);  
        } else {
            request.setAttribute("result", 0);
        }
    }
}

 

 