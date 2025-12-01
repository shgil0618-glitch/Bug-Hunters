package com.thejoa703.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.UserDao;
import com.thejoa703.dto.UserDto;
import com.thejoa703.service.*;

public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String path = request.getServletPath();
		HttpSession session = request.getSession();
		UserService service = null;
/////////////////////////////////////////////////메인//////////////////////////////////////////////////////////
		if (path.equals("/main.do")) {
			request.getRequestDispatcher("view/Main.jsp").forward(request, response);

/////////////////////////////////////////////////회원가입////////////////////////////////////////////////////////
		} else if (path.equals("/joinForm.do")) {
			request.getRequestDispatcher("view/UserJoin.jsp").forward(request, response);

		} else if (path.equals("/join.do")) {
			service = new UserJoin();
			service.exec(request, response);
			Integer result = (Integer) request.getAttribute("result");

			if (result != null && result > 0) {
				out.println("<script>alert('회원가입이 완료되었습니다.'); location.href='loginForm.do';</script>");
			} else {
				out.println("<script>alert('회원가입에 실패했습니다.'); history.go(-1);</script>");
			}

//////////////////////////////////////////////로그인////////////////////////////////////////////////////////////            
		} else if (path.equals("/loginForm.do")) {
			request.getRequestDispatcher("view/UserLogin.jsp").forward(request, response);

		} else if (path.equals("/login.do")) {
			service = new UserLogin();
			service.exec(request, response);
			Integer result = (Integer) request.getAttribute("result");
			String email = (String) session.getAttribute("email");
			System.out.println("로그인된 사용자 이메일: " + email);
			
			if (result != null && result > 0) {
				UserDao dao = new UserDao();
		        UserDto user = dao.mypage(email);
		        session.setAttribute("nickname", user.getNICKNAME());
				out.println("<script>alert('로그인 성공!'); location.href='mypage.do';</script>");
			} else {
				out.println("<script>alert('로그인 실패! 이메일 또는 비밀번호를 확인하세요.'); history.go(-1);</script>");
			}

///////////////////////////////////////////////마이페이지///////////////////////////////////////////////////////////       
		} else if (path.equals("/mypage.do")) {
			service = new UserMypage();
			service.exec(request, response);

			Integer result = (Integer) request.getAttribute("result");
			if (result != null && result == 0) {
				out.println("<script>alert('로그인이 필요합니다.'); location.href='loginForm.do';</script>");
			} else {
				request.getRequestDispatcher("view/MyPage.jsp").forward(request, response);
			}

///////////////////////////////////////////////로그아웃///////////////////////////////////////////////////////////           
		} else if (path.equals("/logout.do")) {
			session.invalidate();
			response.sendRedirect("main.do");

//////////////////////////////////////////////수정//////////////////////////////////////////////////////////////            
		} else if (path.equals("/editForm.do")) {
			String email = (String) session.getAttribute("email");
			if (email == null) {
				response.sendRedirect("loginForm.do");
				return;

			}

			UserDao dao = new UserDao();
			UserDto user = dao.mypage(email);
			request.setAttribute("user", user);
			request.getRequestDispatcher("view/UserEdit.jsp").forward(request, response);

		} else if (path.equals("/edit.do")) {
			String email = request.getParameter("email");
			String nickname = request.getParameter("nickname");
			String mobile = request.getParameter("mobile");
			String password = request.getParameter("password");

			UserDto dto = new UserDto();
			dto.setEMAIL(email);
			dto.setNICKNAME(nickname);
			dto.setMOBILE(mobile);
			dto.setPASSWORD(password);

			UserDao dao = new UserDao();
			int result = dao.edit(dto);

			if (result > 0) {
				UserDto updatedUser = dao.mypage(email);
				session.setAttribute("loginUser", updatedUser);
				request.setAttribute("user", updatedUser);
				request.setAttribute("message", "회원정보가 성공적으로 수정되었습니다.");
				request.getRequestDispatcher("view/MyPage.jsp").forward(request, response);
			} else {
				request.setAttribute("message", "회원정보 수정에 실패했습니다. 다시 시도해주세요.");
				request.getRequestDispatcher("view/UserEdit.jsp").forward(request, response);
			}

/////////////////////////////////////////////회원탈퇴///////////////////////////////////////////////////////////
		} if (path.equals("/deleteForm.do")) {
			request.getRequestDispatcher("view/UserDelete.jsp").forward(request, response);

		} else if (path.equals("/delete.do")) {
			service = new UserDelete();
			service.exec(request, response);

			Integer result = (Integer) request.getAttribute("result");

			if (result == null || result == 0) {
				out.println("<script>alert('로그인이 필요한 서비스.'); location.href='loginForm.do';</script>");
			} else if (result == -2) {
				out.println("<script>alert('비밀번호가 일치하지 않습니다.'); history.go(-1);</script>");
			} else if (result == -1) {
				out.println("<script>alert('회원 탈퇴 실패.'); history.go(-1);</script>");
			} else if (result == 1) {
				out.println("<script>alert('회원 탈퇴 완료.'); location.href='main.do';</script>");
			}
		}

	}
}
