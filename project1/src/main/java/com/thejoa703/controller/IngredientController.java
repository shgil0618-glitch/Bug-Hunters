package com.thejoa703.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import com.thejoa703.service.*;

public class IngredientController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public IngredientController() {
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

    protected void doAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String path = request.getServletPath();
        IngredientService service = null;

        // 재료 목록
        if (path.equals("/ingredientList.ingre")) {
            service = new IngredientListService();
            service.exec(request, response);
            request.getRequestDispatcher("ingredient/IngredientList.jsp").forward(request, response);

        // 재료 등록 폼
        } else if (path.equals("/ingredientInsertForm.ingre")) {
            request.getRequestDispatcher("ingredient/IngredientInsert.jsp").forward(request, response);

        // 재료 등록
        } else if (path.equals("/ingredientInsert.ingre")) {
            service = new IngredientInsertService();
            service.exec(request, response);

            int result = (int) request.getAttribute("result");
            if (result > 0)
                out.println("<script>alert('등록 완료'); history.back();</script>");
            else
                out.println("<script>alert('등록 실패'); history.back();</script>");

        // 재료 수정 폼
        } else if (path.equals("/ingredientUpdateForm.ingre")) {
            // 재료 상세 서비스가 필요하면 만들 수 있음
            request.getRequestDispatcher("ingredient/IngredientUpdate.jsp").forward(request, response);

        // 재료 수정
        } else if (path.equals("/ingredientUpdate.ingre")) {
            service = new IngredientUpdateService();
            service.exec(request, response);
            int result = (int) request.getAttribute("result");

            if (result > 0)
                out.println("<script>alert('수정 완료'); history.back();</script>");
            else
                out.println("<script>alert('수정 실패'); history.back();</script>");

        // 재료 삭제
        } else if (path.equals("/ingredientDelete.ingre")) {
            service = new IngredientDeleteService();
            service.exec(request, response);
            int result = (int) request.getAttribute("result");

            if (result > 0)
                out.println("<script>alert('삭제 완료'); history.back();</script>");
            else
                out.println("<script>alert('삭제 실패'); history.back();</script>");
        }
    }
}
