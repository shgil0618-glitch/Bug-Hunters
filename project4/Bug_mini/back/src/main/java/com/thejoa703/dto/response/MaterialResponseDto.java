package com.thejoa703.dto.response;

import com.thejoa703.domain.Material; 
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MaterialResponseDto {
    private Long materialid;
    private String title;
    private String imageurl;
    private String category;
    private String efficacy; 
    private String allergy;

    public MaterialResponseDto(Material entity) {
        this.materialid = entity.getMaterialid(); //
        this.title = entity.getTitle(); //
        this.imageurl = entity.getImageurl(); //
        this.category = entity.getCategory(); //
        this.efficacy = entity.getEfficacy(); //
        this.allergy = entity.getAllergy(); //
    }
}