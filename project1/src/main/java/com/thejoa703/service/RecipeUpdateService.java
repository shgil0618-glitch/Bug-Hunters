package com.thejoa703.service;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.RecipeDao;
import com.thejoa703.dto.RecipeDto;

public class RecipeUpdateService implements RecipeService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int recipeId = Integer.parseInt(request.getParameter("recipeId"));
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String cookTimeStr = request.getParameter("cookTime");
        String difficulty = request.getParameter("difficulty");
        String servingsStr = request.getParameter("servings");
        String fileName = request.getParameter("bfile"); // 기존 파일명

        // 새로운 파일 선택시 처리
        String newFile = request.getParameter("file"); // 사용자가 새로 선택한 파일
        if (newFile != null && !newFile.isEmpty()) {
            String sourcePath = "C:/file/" + newFile;
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) {
                String uploadPath = request.getServletContext().getRealPath("/upload/");
                File targetFile = new File(uploadPath, newFile);
                java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                fileName = newFile; // 변경
            }
        }

        RecipeDto dto = new RecipeDto();
        dto.setRecipeId(recipeId);
        dto.setTitle(title != null ? title : "");
        dto.setCategory(category != null ? category : "");
        dto.setDescription(description != null ? description : "");
        dto.setCookTime(cookTimeStr != null && !cookTimeStr.isEmpty() ? Integer.parseInt(cookTimeStr) : 0);
        dto.setDifficulty(difficulty != null ? difficulty : "쉬움");
        dto.setServings(servingsStr != null && !servingsStr.isEmpty() ? Integer.parseInt(servingsStr) : 1);
        dto.setRfile(fileName);

        RecipeDao dao = new RecipeDao();
        int result = dao.update(dto);

        request.setAttribute("result", String.valueOf(result));
    }
}
