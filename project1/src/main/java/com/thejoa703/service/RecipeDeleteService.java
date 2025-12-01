package com.thejoa703.service;

import javax.servlet.http.*;
import com.thejoa703.dao.RecipeDao;

public class RecipeDeleteService implements RecipeService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) {

        String recipeIdStr = request.getParameter("recipeId");

        // recipeId 없으면 삭제 불가
        if (recipeIdStr == null || recipeIdStr.isEmpty()) {
            request.setAttribute("result", "0");
            return;
        }

        int recipeId = Integer.parseInt(recipeIdStr);

        RecipeDao dao = new RecipeDao();
        int result = dao.delete(recipeId);
        request.setAttribute("result", String.valueOf(result));
    }
}
