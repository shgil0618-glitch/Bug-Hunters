package com.thejoa703.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thejoa703.domain.Material;
import com.thejoa703.dto.request.MaterialRequestDto;
import com.thejoa703.dto.response.MaterialResponseDto;
import com.thejoa703.repository.MaterialRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository repository;

    @Transactional(readOnly = true)
    public List<MaterialResponseDto> getMaterialList(int page, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("materialid").descending());
        return repository.findByTitleContaining(keyword, pageable)
                .stream()
                .map(MaterialResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long getTotalCount(String keyword) {
        return repository.countByTitleContaining(keyword);
    }

    @Transactional
    public void saveMaterial(MaterialRequestDto dto, MultipartFile file) {
        String fileName = handleFileUpload(file);
        
        Material material = dto.toEntity(fileName);
        repository.save(material);
    }

    private String handleFileUpload(MultipartFile file) {
        String uploadPath = "C:/upload/";
        
        if (file == null || file.isEmpty()) {
            return "default.png"; 
        }

        String savedFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        
        try {
            File folder = new File(uploadPath);
            if (!folder.exists()) {
                folder.mkdirs(); 
            }
           
            file.transferTo(new File(uploadPath + savedFileName));
            return savedFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "default.png";
        }
    }
}