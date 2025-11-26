package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.IngredientDao;
import com.thejoa703.dto.IngredientDto;

public class IngredientUpdateService implements IngredientService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {

        IngredientDto dto = new IngredientDto();

        dto.setIngredientId(Integer.parseInt(request.getParameter("ingredientId")));
        dto.setIngredientName(request.getParameter("ingredientName"));
        dto.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        dto.setUnit(request.getParameter("unit"));
        dto.setKcal(Integer.parseInt(request.getParameter("kcal")));
        dto.setCarbs(Integer.parseInt(request.getParameter("carbs")));
        dto.setProtein(Integer.parseInt(request.getParameter("protein")));
        dto.setFat(Integer.parseInt(request.getParameter("fat")));
        dto.setAllergens(request.getParameter("allergens"));

        IngredientDao dao = new IngredientDao();
        int result = dao.update(dto);

        request.setAttribute("result", result);
    }
}
