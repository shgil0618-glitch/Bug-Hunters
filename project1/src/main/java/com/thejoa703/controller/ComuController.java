package com.thejoa703.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.service.ComuDelete;
import com.thejoa703.service.ComuDtail;
import com.thejoa703.service.ComuEdit;
import com.thejoa703.service.ComuEditView;
import com.thejoa703.service.ComuInsert;
import com.thejoa703.service.ComuList;
import com.thejoa703.service.ComuService;



public class ComuController extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComuController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String path = request.getServletPath();			// 어디로 접속했는지 알려
		System.out.println(path);
		
		ComuService service = null; //##
		
///////////////////////////////////////////////////////
		if(path.equals("/list.co")) {
			// ■ MbtiList - 데이터 받고 처리 /         MBTIboard/list.jsp
			System.out.println("[Controller] /list.co 요청 받음");
			service = new ComuList();  service.exec(request, response);
			 System.out.println("[Controller] list 조회 완료 → JSP 포워딩");
			request.getRequestDispatcher("COMUboard/list.jsp").forward(request, response);
///////////////////////////////////////////////////////
		} else if(path.equals("/writeView.co")) {
			//□           /         MbTIBoard/write.jsp
			request.getRequestDispatcher("COMUboard/write.jsp").forward(request, response);
		} else if(path.equals("/write.co")) {
			// ■ MbtiInsert /알림창 + list.do
			service = new ComuInsert();  service.exec(request, response);
			//알림창+response.sendRedirect (x)
			//out.println("<script>alert('글쓰기에 성공했습니다.');</script>");
			//response.sendRedirect("list.do");
			String result = (String)request.getAttribute("result");
			if(result.equals("1")) {
			out.println("<script>alert('글쓰기에 성공했습니다.'); location.href='list.co';</script>");
			}else {
				out.println("<script>alert('관리자에게 문의 바랍니다.'); location.href='list.co';</script>");
			}
///////////////////////////////////////////////////////			
		} else if(path.equals("/detail.co")) {
			//■ MbtiDetail /        MbTIBoard/detail.jsp
			service = new ComuDtail();  service.exec(request, response);
			request.getRequestDispatcher("COMUboard/detail.jsp").forward(request, response);
///////////////////////////////////////////////////////			
		} else if(path.equals("/editView.co")) {
			//■ MbtiUpdateView /    MbTIBoard/edit.jsp
			service = new ComuEditView();  service.exec(request, response);
			request.getRequestDispatcher("COMUboard/edit.jsp").forward(request, response);
		} else if(path.equals("/edit.co")) {
			//■ MbtiUpdate /알림창 + MbTIBoard/detail.jsp
			service = new ComuEdit();  service.exec(request, response);
			int postId = Integer.parseInt(request.getParameter("postId"));
			System.out.println(postId);
			String result = (String)request.getAttribute("result");
			if(result.equals("1")) {
			out.println("<script>alert('글수정에 성공했습니다.'); location.href='detail.co?postId="+request.getParameter("postId")+"';</script>");
			}else {
				out.println("<script>alert('아이디를 확인해주세요.'); history.go(-1);'</script>");
			}
			///////////////////////////////////////////////////////			
		} else if(path.equals("/deleteView.co")) {
			//□           /         MbTIBoard/delete.jsp
			request.getRequestDispatcher("COMUboard/delete.jsp").forward(request, response);
		} else if (path.equals("/deleteView.co")) {
			
		}
		else if(path.equals("/delete.co")) {
			//■ MbtiDelete /알림창 + list.do
			service = new ComuDelete();  service.exec(request, response);
			int postId = Integer.parseInt(request.getParameter("postId"));
			System.out.println(postId);
			String result = (String) request.getAttribute("result");
			if(result.equals("1")) {
				 out.println("<script>alert('글삭제에 성공했습니다.'); location.href='list.co'; </script>");
				 } else {
					 out.println("<script>alert('아이디를 확인해주세요.'); history.go(-1) </script>");
				 }
		}
		
		}
}

/*
  ㄴ [전체글보기]/MBTIboard/list.do           ■ MbtiList /           MbTIBoard/list.jsp
            ㄴ [글쓰기폼]/MBTIboard/writeView.do        □           /         MbTIBoard/write.jsp
            ㄴ [글쓰기기능]/MBTIboard/write.do          ■ MbtiInsert /알림창 + list.do
            ㄴ [상세보기]/MBTIboard/detail.do           ■ MbtiDetail /        MbTIBoard/detail.jsp
            ㄴ [글수정폼]/MBTIboard/editView.do         ■ MbtiUpdateView /    MbTIBoard/edit.jsp
            ㄴ [글수정기능]/MBTIboard/edit.do           ■ MbtiUpdate /알림창 + MbTIBoard/detail.jsp
            ㄴ [글수정폼]/MBTIboard/deleteView.do       □           /         MbTIBoard/delete.jsp
            ㄴ [글수정삭제]/MBTIboard/delete.do         ■ MbtiDelete /알림창 + list.do
             
 */


