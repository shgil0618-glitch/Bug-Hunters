package com.thejoa703.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.RecipeDao;
import com.thejoa703.dto.RecipeDto;

public class RecipeDetailService implements RecipeService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String recipeIdStr = request.getParameter("recipeId");
        if (recipeIdStr == null || recipeIdStr.isEmpty()) {
            request.setAttribute("dto", null);
            return;
        }

        int recipeId = Integer.parseInt(recipeIdStr);

        RecipeDao dao = new RecipeDao();
        RecipeDto dto = dao.detail(recipeId);

        request.setAttribute("recipe", dto);
    }
}
