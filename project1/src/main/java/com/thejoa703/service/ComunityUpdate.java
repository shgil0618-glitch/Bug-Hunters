package com.thejoa703.service;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComunityDao;
import com.thejoa703.dto.ComunityDto;

public class ComunityUpdate implements ComunityService {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 데이터 받기
        int postId = Integer.parseInt(request.getParameter("postId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        ComunityDto dto = new ComunityDto();
        dto.setPostId(postId);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setCategoryId(categoryId);
        dto.setUpdatedAt(LocalDateTime.now());

        // 2. DAO 호출
        ComunityDao dao = new ComunityDao();
        dao.updatePost(dto);
    }
}
