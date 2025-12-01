package com.thejoa703.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.SerchDao;
import com.thejoa703.dto.SerchDto;

public class RecommendSelectAll implements RecommendService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) {
        SerchDao dao = new SerchDao();
        
        List<SerchDto> list = dao.selectAll(); // DAO에서 전체 목록 가져오기
        System.out.println("....."+list);
        request.setAttribute("list", list); // JSP에서 사용할 수 있도록 request에 저장
    }
}
