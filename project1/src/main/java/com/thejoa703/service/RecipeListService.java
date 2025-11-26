package com.thejoa703.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.RecipeDao;


public class RecipeListService implements RecipeService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RecipeDao dao = new RecipeDao();
        request.setAttribute("list", dao.list());
    }
}
