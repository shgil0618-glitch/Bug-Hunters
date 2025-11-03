package com.thejoa703.service;

import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thejoa703.dao.ComunityDao;
import com.thejoa703.dto.ComunityDto;


public interface ComunityService {
	public void exec(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException;
}
