package project2.dto;

import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private int recipeId;
    private int appUserId;
    private String title;
    private int category;
    private String image;
    private int cookTime;
    private String difficulty;
    private int servings;
    private String description;
    private String instructions;           // hidden에 합쳐진 전체 텍스트
    private List<String> instructionTexts; // 개별 단계 텍스트 (폼 바인딩용)
    private Date createdAt;
    private Date updatedAt;
    private int views;
    private String nickname;
    private String categoryName;

    private List<RecipeImage> images;
    private List<RecipeIngre> ingredients; // List 바인딩
}
