package com.company.jeongmin.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.jeongmin.Dao_Dto.FoodDao;
import com.company.jeongmin.Dao_Dto.FoodDto;

public class FoodInsert implements FoodService{

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String name = request.getParameter("name");
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		double kcal = Double.parseDouble(request.getParameter("kcal"));
		double protein = Double.parseDouble(request.getParameter("protein"));
		double carb = Double.parseDouble(request.getParameter("carb"));
		double fat = Double.parseDouble(request.getParameter("fat"));
		String recipe = request.getParameter("recipe");
		String imageurl = request.getParameter("imageurl"); 

		
		FoodDao dao = new FoodDao();
		FoodDto dto = new FoodDto();
	
		dto.setName(name); dto.setCategoryId(categoryId); dto.setKcal(kcal); dto.setProtein(protein);
		dto.setCarb(carb); dto.setFat(fat); dto.setRecipe(recipe); dto.setImageUrl(imageurl); 
		String result = String.valueOf(dao.insert(dto));
		
		request.setAttribute("result", result);
		
		
		
		
	}

}
