package project2.dto;

import lombok.Data;

@Data
public class MaterialDto {
    private int materialId;
    private String title;
    private String imageUrl;
    private String season;
    private String temperature;
    private int calories100g;
    private String efficacy;
    private String buyGuide;
    private String trimGuide;
    private String storeGuide;
}
