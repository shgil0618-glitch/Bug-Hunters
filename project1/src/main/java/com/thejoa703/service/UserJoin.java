package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.UserDao;
import com.thejoa703.dto.UserDto;

public class UserJoin implements UserService {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
    	String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        UserDto dto = new UserDto();
        dto.setPASSWORD(password);
        dto.setNICKNAME(nickname);
        dto.setEMAIL(email);
        dto.setMOBILE(mobile);
        System.out.println("비밀번호: " + password);
        System.out.println("닉네임: " + nickname);
        System.out.println("이메일: " + email);
        System.out.println("휴대폰: " + mobile);
        
 

        UserDao dao = new UserDao();
        int result = dao.insert(dto);
        System.out.println("회원가입 결과: " + result);
        request.setAttribute("result", result);
    }
}

