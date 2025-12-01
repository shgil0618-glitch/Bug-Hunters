package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.IngredientDao;

public class IngredientListService implements IngredientService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        int recipeId = Integer.parseInt(request.getParameter("recipeId"));

        IngredientDao dao = new IngredientDao();
        request.setAttribute("list", dao.list(recipeId));
    }
}
