package com.thejoa703.service;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.thejoa703.dao.RecipeDao;
import com.thejoa703.dto.RecipeDto;

public class RecipeInsertService implements RecipeService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String cookTimeStr = request.getParameter("cookTime");
        String difficulty = request.getParameter("difficulty");
        String servingsStr = request.getParameter("servings");
        String fileName = request.getParameter("bfile"); // 로컬 파일명

        // 파일 처리: 로컬 C:/file/에서 webapp/upload/로 복사
        if (fileName == null || fileName.isEmpty()) {
            fileName = "default.png";
        } else {
            String sourcePath = "C:/file/" + fileName;
            File sourceFile = new File(sourcePath);
            if (sourceFile.exists()) {
                String uploadPath = request.getServletContext().getRealPath("/upload/");
                File targetFile = new File(uploadPath, fileName);
                java.nio.file.Files.copy(sourceFile.toPath(), targetFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            } else {
                fileName = "default.png";
            }
        }
        System.out.println(fileName);

        // DTO 세팅
        RecipeDto dto = new RecipeDto();
        dto.setTitle(title != null ? title : "");
        dto.setCategory(category != null ? category : "");
        dto.setDescription(description != null ? description : "");
        dto.setCookTime(cookTimeStr != null && !cookTimeStr.isEmpty() ? Integer.parseInt(cookTimeStr) : 0);
        dto.setDifficulty(difficulty != null ? difficulty : "쉬움");
        dto.setServings(servingsStr != null && !servingsStr.isEmpty() ? Integer.parseInt(servingsStr) : 1);
        dto.setRfile(fileName);

        // DB insert
        RecipeDao dao = new RecipeDao();
        int result = dao.insert(dto);

        request.setAttribute("result", String.valueOf(result));
    }
}
