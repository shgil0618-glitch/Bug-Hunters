package com.company.jeongmin.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.jeongmin.Dao_Dto.FoodDao;
import com.company.jeongmin.Dao_Dto.FoodDto;

public class FoodUpdate implements FoodService {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 받기
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name"); 
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		double kcal = Double.parseDouble(request.getParameter("kcal"));
		double protein = Double.parseDouble(request.getParameter("protein"));
		double carb = Double.parseDouble(request.getParameter("carb"));
		double fat = Double.parseDouble(request.getParameter("fat"));
		String recipe = request.getParameter("recipe");
		String imageurl = request.getParameter("imageurl"); 
		int id = Integer.parseInt(request.getParameter("id"));
		
		
		FoodDao updateDao = new FoodDao();
        FoodDto updateDto = new FoodDto();
        updateDto.setName(name);
        updateDto.setCategoryId(categoryId); 
        updateDto.setKcal(kcal);
        updateDto.setProtein(protein);
        updateDto.setCarb(carb);
        updateDto.setFat(fat);
      	updateDto.setRecipe(recipe);
        updateDto.setImageUrl(imageurl);
        updateDto.setFoodId(id);
        
        //2. 처리 + 넘기기
		request.setAttribute("result",String.valueOf(updateDao.update(updateDto))  );
		request.setAttribute("dto", updateDto);
		
	}

	
}
