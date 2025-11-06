package com.thejoa703.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.UserDao;
import com.thejoa703.dto.UserDto;

public class UserDelete implements UserService {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        String inputPassword = request.getParameter("password");

        if (email == null || inputPassword == null || inputPassword.trim().isEmpty()) {
            request.setAttribute("result", 0); 
        }

        UserDao dao = new UserDao();
        UserDto user = dao.mypage(email);

        if (user != null && user.getPASSWORD() != null && inputPassword.equals(user.getPASSWORD())) {
            int deleteResult = dao.delete(email);
            if (deleteResult > 0) {
                session.invalidate();
                request.setAttribute("result", 1);
            } else {
                request.setAttribute("result", -1);
            }
        	} else {
        		request.setAttribute("result", -2);
        }
    }
}
