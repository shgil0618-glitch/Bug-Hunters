package com.thejoa703.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thejoa703.dto.request.MaterialRequestDto;
import com.thejoa703.dto.response.MaterialResponseDto;
import com.thejoa703.service.MaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/material")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // React 포트 허용
public class MaterialController {

    private final MaterialService materialService;

    /**
     * 재료 목록 조회 (페이징 및 검색 포함)
     * GET http://localhost:8080/api/material/list?page=1&keyword=양파
     */
    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        
        Map<String, Object> response = new HashMap<>();
        
        // 1. 서비스로부터 DTO 리스트 받아오기
        List<MaterialResponseDto> list = materialService.getMaterialList(page, keyword);
        
        // 2. 검색 조건에 맞는 전체 데이터 개수 받아오기
        long totalCnt = materialService.getTotalCount(keyword); 
        
        // 3. 리액트에 전달할 데이터 구성
        response.put("list", list);
        response.put("totalCnt", totalCnt);
        response.put("currentPage", page);
        
        return response;
    }

    /**
     * 재료 등록
     * POST http://localhost:8080/api/material/insert
     */
    @PostMapping("/insert")
    public ResponseEntity<String> insert(@ModelAttribute MaterialRequestDto requestDto, 
                                         @RequestParam(value = "file", required = false) MultipartFile file) {
        
        materialService.saveMaterial(requestDto, file);
        return ResponseEntity.ok("success");
    }
}