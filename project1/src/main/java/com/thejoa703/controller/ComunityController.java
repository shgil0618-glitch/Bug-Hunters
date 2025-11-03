package com.thejoa703.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.service.ComunityDelete;
import com.thejoa703.service.ComunityInsert;
import com.thejoa703.service.ComunityList;
import com.thejoa703.service.ComunityService;
import com.thejoa703.service.ComunityUpdate;

//@WebServlet("*.co")
public class ComunityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ComunityController() { super(); }

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
		System.out.println(path);

		ComunityService service = null;

		//////////////////////////////////////////////////////////////////////////////////////////
		// 🌐 커뮤니티 게시판 (Community)
		//////////////////////////////////////////////////////////////////////////////////////////

		if (path.equals("/communityList.co")) {
			// ■ 목록 조회
			service = new ComunityList();
			service.exec(request, response);
			request.getRequestDispatcher("communityBoard/list.jsp").forward(request, response);

		} else if (path.equals("/communityWriteView.co")) {
			// ■ 글쓰기 화면
			request.getRequestDispatcher("communityBoard/write.jsp").forward(request, response);

		} else if (path.equals("/communityWrite.co")) {
			// ■ 글쓰기 처리
			service = new ComunityInsert();
			service.exec(request, response);

			String result = (String) request.getAttribute("result");
			if ("1".equals(result)) {
				out.println("<script>alert('글쓰기에 성공했습니다.'); location.href='communityList.co'; </script>");
			} else {
				out.println("<script>alert('글쓰기 실패! 관리자에게 문의바랍니다.'); location.href='communityList.co'; </script>");
			}

		}  else if (path.equals("/communityEdit.co")) {
			// ■ 글 수정
			service = new ComunityUpdate();
			service.exec(request, response);

			int postId = Integer.parseInt(request.getParameter("postId"));
			String result = (String) request.getAttribute("result");

			if ("1".equals(result)) {
				out.println("<script>alert('글 수정에 성공했습니다.'); location.href='communityDetail.co?postId=" + postId + "'; </script>");
			} else {
				out.println("<script>alert('수정 실패. 비밀번호를 확인해주세요.'); history.go(-1); </script>");
			}

		} else if (path.equals("/communityDeleteView.co")) {
			// ■ 삭제 화면
			request.getRequestDispatcher("communityBoard/delete.jsp").forward(request, response);

		} else if (path.equals("/communityDelete.co")) {
			// ■ 글 삭제
			service = new ComunityDelete();
			service.exec(request, response);

			String result = (String) request.getAttribute("result");
			if ("1".equals(result)) {
				out.println("<script>alert('글 삭제에 성공했습니다.'); location.href='communityList.co'; </script>");
			} else {
				out.println("<script>alert('삭제 실패. 비밀번호를 확인해주세요.'); history.go(-1); </script>");
			}
		}
	}
}
