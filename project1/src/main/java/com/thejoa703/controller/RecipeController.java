package com.thejoa703.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import com.thejoa703.service.*;

public class RecipeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RecipeController() {
        super();
    }

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
        System.out.println("[RecipeController] 요청 path: " + path);

        RecipeService service = null;

        // ------------------ 레시피 목록 ------------------
        if (path.equals("/recipeList.reci")) {
            service = new RecipeListService();
            service.exec(request, response);
            request.getRequestDispatcher("recipe/RecipeList.jsp").forward(request, response);

        // ------------------ 레시피 등록 폼 ------------------
        } else if (path.equals("/recipeInsertForm.reci")) {
            request.getRequestDispatcher("recipe/RecipeInsert.jsp").forward(request, response);

        // ------------------ 레시피 등록 ------------------
        } else if (path.equals("/recipeInsert.reci")) {
            service = new RecipeInsertService();
            service.exec(request, response);
            String result = (String) request.getAttribute("result");

            if ("1".equals(result)) {
                out.println("<script>alert('레시피 등록 성공'); location.href='recipeList.reci';</script>");
            } else {
                out.println("<script>alert('등록 실패, 관리자에게 문의하세요'); history.back();</script>");
            }

        // ------------------ 상세보기 ------------------
        } else if (path.equals("/recipeDetail.reci")) {
            service = new RecipeDetailService();
            service.exec(request, response);
            request.getRequestDispatcher("recipe/RecipeDetail.jsp").forward(request, response);

        // ------------------ 수정 폼 ------------------
        } else if (path.equals("/recipeUpdateForm.reci")) {
            service = new RecipeDetailService();
            service.exec(request, response);
            request.getRequestDispatcher("recipe/RecipeUpdate.jsp").forward(request, response);

        // ------------------ 수정 ------------------
        } else if (path.equals("/recipeUpdate.reci")) {
            service = new RecipeUpdateService();
            service.exec(request, response);
            String result = (String) request.getAttribute("result");

            if ("1".equals(result)) {
                out.println("<script>alert('레시피 수정 성공'); location.href='recipeDetail.reci?recipeId=" 
                            + request.getParameter("recipeId") + "';</script>");
            } else {
                out.println("<script>alert('수정 실패, 아이디를 확인하세요'); history.back();</script>");
            }

        // ------------------ 삭제 ------------------
        } else if (path.equals("/recipeDelete.reci")) {
            service = new RecipeDeleteService();
            service.exec(request, response);
            String result = (String) request.getAttribute("result");

            if ("1".equals(result)) {
                out.println("<script>alert('레시피 삭제 성공'); location.href='recipeList.reci';</script>");
            } else {
                out.println("<script>alert('삭제 실패, 아이디를 확인하세요'); history.back();</script>");
            }
        }
    }
}
