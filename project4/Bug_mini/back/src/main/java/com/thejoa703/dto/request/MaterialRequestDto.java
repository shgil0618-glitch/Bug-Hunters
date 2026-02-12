package com.thejoa703.dto.request;

import com.thejoa703.domain.Material;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MaterialRequestDto { // 클래스명을 서비스와 일치시킴
    private String title;
    private String allergy;
    private String season;
    private String temperature;
    private String calories100g;
    private String efficacy;
    private String buyguide;
    private String trimguide;
    private String storeguide;
    private String category;

    public Material toEntity(String imageurl) {
        return Material.builder()
                .title(title)
                .allergy(allergy)
                .season(season)
                .temperature(temperature)
                .calories100g(calories100g)
                .efficacy(efficacy)
                .buyguide(buyguide)
                .trimguide(trimguide)
                .storeguide(storeguide)
                .category(category)
                .imageurl(imageurl)
                .build();
    }
}