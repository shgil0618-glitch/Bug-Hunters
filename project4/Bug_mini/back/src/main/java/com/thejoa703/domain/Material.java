package com.thejoa703.domain;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "material4")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mat_seq")
    @SequenceGenerator(name = "mat_seq", sequenceName = "material4_seq", allocationSize = 1)
    private Long materialid; 

    private String title;
    private String imageurl;
    private String category;
    private String allergy;
    private String season;
    private String temperature;
    private String calories100g;

    @Column(length = 4000)
    private String efficacy;
    
    private String buyguide;
    private String trimguide;
    private String storeguide;
}