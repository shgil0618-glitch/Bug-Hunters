package com.thejoa703.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.SerchDao;
import com.thejoa703.dto.SerchDto;

public class RecommendSelect implements RecommendService {

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 1. 데이터 넘겨받기
        String id =request.getParameter("recId");

        // 2. DAO 호출
        SerchDao dao = new SerchDao();
        List<SerchDto> list = dao.selectByUser(id);
        System.out.println("...."+list);
		/* PostDto dto = new PostDto(); */
		/* dto.setRecId(recId); // 추천 하나 조회
		 */       
        //SerchDto result = dao.selectByUser(recId);
        
		// 3. 데이터 넘겨주기
		request.setAttribute("list", list);
		
		
		/*
		 * request.setAttribute("recId", recId); request.setAttribute("result",
		 * String.valueOf(dao.delete(dto)));
		 */
		/* request.setAttribute("dto", dto); */
    }
}

/*
  request.setCharacterEncoding("UTF-8");
			// 1. 데이터 넘겨받기
		int postId = Integer.parseInt(request.getParameter("postId"));
		System.out.println("....." + postId);
		
		// 2. dao
		ComuDao dao = new ComuDao();
		
		ComuDto result = dao.select(postId);
		System.out.println("....." + result);
		// 3. 데이터 넘겨주기
		request.setAttribute("dto", result);
		request.getRequestDispatcher("COMUboard/detail.jsp").forward(request, response);
*/
