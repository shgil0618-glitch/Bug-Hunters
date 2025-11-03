package com.thejoa703.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComunityDao;

public class ComunityDelete implements ComunityService {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 데이터 받기
        int postId = Integer.parseInt(request.getParameter("postId"));

        // 2. DAO 호출
        ComunityDao dao = new ComunityDao();
        dao.deletePost(postId);
    }
}
