package project2.dto;

import lombok.Data;

@Data
public class MaterialDto {
	   	private int materialid;
	    private String title;
	    private String imageurl;
	    private String season;
	    private String temperature;
	    private int calories100g;
	    private String efficacy;
	    private String buyguide;
	    private String trimguide;
	    private String storeguide;
}
