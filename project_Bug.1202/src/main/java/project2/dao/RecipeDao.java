package project2.dao;

import java.util.List;
import project2.dto.RecipeDto;
import project2.dto.RecipeImage;
import project2.dto.RecipeIngre;
import project2.dto.RecipeIngreMap;

@MyDao
public interface RecipeDao {

    // 1. **C R E A T E (등록)**
    public int insert(RecipeDto dto);  // nickname을 포함한 레시피 등록
    public int insertRecipeImage(RecipeImage image);
    public int insertIngredientMap(RecipeIngreMap ingreMap);
    public int insertIngredientDetail(RecipeIngre ingre);

    // ---  

    // 2. **R E A D (조회)**
    public List<RecipeDto> selectAll();
    public List<RecipeDto> selectMyRecipes(int appUserId);
    public RecipeDto select(int recipeId);
    public List<RecipeImage> selectRecipeImages(int recipeId);
    public List<RecipeIngre> selectRecipeIngredients(int recipeId);
    public int incrementRecipeViews(int recipeId);
    public int selectMaxRecipeId();
    
    // 카테고리 이름 조회
    public String selectCategoryNameById(int categoryId);

    // 3. **U P D A T E (수정)**
    public int update(RecipeDto dto);
    public int deleteRecipeImages(int recipeId);
    public int deleteIngredientMaps(int recipeId);

    // ---  

    // 4. **D E L E T E (삭제)**
    public int delete(int recipeId);
}


/*
    // 1. **C R E A T E (등록)**

     * 레시피 기본 정보를 등록하고 생성된 RECIPE_ID를 DTO에 반환합니다.
     * @param dto 레시피 정보를 담은 DTO
     * @return 생성된 레시피의 ID

    public int insert(RecipeDto dto);
    
  
     * 레시피 이미지를 등록합니다.
     * @param image 레시피 이미지 정보를 담은 DTO
     * @return 등록된 이미지 수
    public int insertRecipeImage(RecipeImage image);

 
     * 재료 매핑 정보를 등록하고 생성된 INGRE_MAP_ID를 Map DTO에 반환합니다.
     * @param ingreMap 재료 매핑 정보를 담은 DTO
     * @return 생성된 INGRE_MAP_ID
   
    public int insertIngredientMap(RecipeIngreMap ingreMap);

    
     * 재료 상세 정보를 등록합니다.
     * @param ingre 재료 정보를 담은 DTO
     * @return 등록된 재료 수
  
    public int insertIngredientDetail(RecipeIngre ingre);

    // ---  

    // 2. **R E A D (조회)**

 
     * 전체 레시피 목록을 최신 순으로 조회합니다.
     * @return 레시피 목록

    public List<RecipeDto> selectAll();

  
     * 특정 사용자(appUserId)가 작성한 레시피 목록을 조회합니다.
     * @param appUserId 사용자의 ID
     * @return 특정 사용자가 작성한 레시피 목록

    public List<RecipeDto> selectMyRecipes(int appUserId);  // 사용자별 레시피 조회

   
     * 특정 RECIPE_ID의 레시피 상세 정보를 조회합니다.
     * @param recipeId 레시피 ID
     * @return 레시피 상세 정보
 
    public RecipeDto select(int recipeId);
    
    * 특정 레시피의 이미지 목록을 조회합니다.
     * @param recipeId 레시피 ID
     * @return 레시피 이미지 목록
   
    public List<RecipeImage> selectRecipeImages(int recipeId);
    
    * 특정 레시피의 재료 목록을 조회합니다.
     * @param recipeId 레시피 ID
     * @return 레시피 재료 목록

    public List<RecipeIngre> selectRecipeIngredients(int recipeId);


     * 레시피 조회수를 1 증가시킵니다.
     * @param recipeId 레시피 ID
     * @return 조회수가 증가한 레시피의 수

    public int incrementRecipeViews(int recipeId);

    // ---  

    // 3. **U P D A T E (수정)**


     * 레시피 기본 정보를 수정합니다. (작성자 ID 조건 포함)
     * @param dto 수정된 레시피 정보를 담은 DTO
     * @return 수정된 레시피의 수

    public int update(RecipeDto dto);
    

     * 레시피의 모든 이미지를 삭제합니다. (수정 전 클리어 목적)
     * @param recipeId 레시피 ID
     * @return 삭제된 이미지 수

    public int deleteRecipeImages(int recipeId);
    

     * 레시피의 모든 재료 매핑 정보를 삭제합니다. (CASCADE로 상세 재료도 삭제)
     * @param recipeId 레시피 ID
     * @return 삭제된 재료 매핑 수

    public int deleteIngredientMaps(int recipeId);

    // ---  

    // 4. **D E L E T E (삭제)**


     * 특정 RECIPE_ID의 레시피를 삭제합니다. (CASCADE로 이미지/재료도 삭제)
     * @param recipeId 레시피 ID
     * @return 삭제된 레시피 수
 
    public int delete(int recipeId);
}
*/
