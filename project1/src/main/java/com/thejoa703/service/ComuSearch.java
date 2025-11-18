package com.thejoa703.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComuDao;
import com.thejoa703.dto.ComuDto;

public class ComuSearch implements ComuService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
    	request.setCharacterEncoding("UTF-8");
        String target = request.getParameter("search_target");   // title / content / nick_name
        String keyword = request.getParameter("search_keyword");

        System.out.println("[SearchService] target=" + target + ", keyword=" + keyword);

        ComuDao dao = new ComuDao();
        ArrayList<ComuDto> list = dao.search(target, keyword);

        // JSP로 넘기는 데이터
        request.setAttribute("list", list);
    }
}
