package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.IngredientDao;

public class IngredientDeleteService implements IngredientService  {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {

        int ingredientId = Integer.parseInt(request.getParameter("ingredientId"));

        IngredientDao dao = new IngredientDao();
        int result = dao.delete(ingredientId);

        request.setAttribute("result", result);
    }
}
