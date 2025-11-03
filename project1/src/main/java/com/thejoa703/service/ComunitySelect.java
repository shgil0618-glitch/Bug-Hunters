package com.thejoa703.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComunityDao;
import com.thejoa703.dto.ComunityDto;

public class ComunitySelect implements ComunityService {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 파라미터 받기
        int postId = Integer.parseInt(request.getParameter("postId"));

        // 2. DAO 호출
        ComunityDao dao = new ComunityDao();
        ComunityDto dto = dao.getPost(postId);

        // 3. 조회수 증가 (옵션)
        dao.incrementViews(postId);

        // 4. 결과 전달
        request.setAttribute("dto", dto);
    }
}
