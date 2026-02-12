package com.thejoa703.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "카테고리를 선택해주세요.")
    private String category;

    @NotNull(message = "인원수를 입력해주세요.")
    private Integer servingSize;

    @NotBlank(message = "난이도를 선택해주세요.")
    private String difficulty;

    private String description;

    @NotBlank(message = "재료 정보를 입력해주세요.")
    private String ingredients;

    @NotBlank(message = "상세 설명을 입력해주세요.")
    private String instructions;

    // ★ 추가: 프론트에서 content라는 키로 보낸 데이터를 담을 칸이 필요합니다!
    private String content; 
    
    private String hashtags;
}