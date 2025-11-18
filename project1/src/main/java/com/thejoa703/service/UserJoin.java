package com.thejoa703.service;

import java.io.IOException;
import java.util.regex.Pattern;

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

        // ✅ 정규식 패턴 정의
        String nicknameRegex = "^[가-힣a-zA-Z0-9]{2,10}$"; // 닉네임: 한글/영문/숫자 2~10글자
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*]).{4,20}$"; // 비밀번호: 영문+숫자+특수문자 포함 8~20자리
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"; // 이메일 기본 형식
        String mobileRegex = "^01[0-9]{8,9}$"; // 휴대폰: 010으로 시작하는 10~11자리 숫자

        int result = 0;

        // ✅ 입력값 검증
        if (!Pattern.matches(nicknameRegex, nickname)) {
            result = -3; // 닉네임 형식 오류
            System.out.println("닉네임 형식 오류: " + nickname);
        } else if (!Pattern.matches(passwordRegex, password)) {
            result = -4; // 비밀번호 형식 오류
            System.out.println("비밀번호 형식 오류: " + password);
        } else if (!Pattern.matches(emailRegex, email)) {
            result = -5; // 이메일 형식 오류
            System.out.println("이메일 형식 오류: " + email);
        } else if (!Pattern.matches(mobileRegex, mobile)) {
            result = -6; // 휴대폰 형식 오류
            System.out.println("휴대폰 형식 오류: " + mobile);
        } else {
            UserDto dto = new UserDto();
            dto.setPASSWORD(password);
            dto.setNICKNAME(nickname);
            dto.setEMAIL(email);
            dto.setMOBILE(mobile);

            UserDao dao = new UserDao();

            // ✅ 중복검사 먼저 실행
            if (dao.checkEmail(email)) {
                result = -1; // 중복된 이메일
                System.out.println("중복된 이메일: " + email);
            } else {
                result = dao.insert(dto); // 회원가입 진행
                System.out.println("회원가입 결과: " + result);
            }
        }

        // 결과를 request에 담아서 JSP에서 메시지 출력 가능
        request.setAttribute("result", result);
    }
}
