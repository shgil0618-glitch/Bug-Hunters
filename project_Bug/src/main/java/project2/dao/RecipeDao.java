package project2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
 // ⭐️ [페이징 추가] DB 조회 시작/끝 순번을 담은 Map을 받아 목록 조회
    public List<RecipeDto> selectRecipeListPaging(Map<String, Object> paramMap);
    
    // ⭐️ [페이징 추가] 전체 레시피 개수 조회
    public int selectRecipeTotalCount();
    
    // 카테고리 이름 조회
    public String selectCategoryNameById(int categoryId);

    // 3. **U P D A T E (수정)**
    public int update(RecipeDto dto);
    public int deleteRecipeImages(int recipeId);
    public int deleteIngredientMaps(int recipeId);

    // ---  

    // 4. **D E L E T E (삭제)**
    public int delete(int recipeId);
    
    // 5. search
    
    // 5. search dao
    public List<RecipeDto> selectSearchTitle(HashMap<String, String> para);

    public List<RecipeDto> selectSearchCategory(HashMap<String, String> para);
    
    public List<RecipeDto> searchBoth(HashMap<String, String> para);
    
 // ⭐️ [검색 페이징 추가]
    public int searchBothCount(HashMap<String, String> para);
    public List<RecipeDto> searchBothPaging(HashMap<String, String> para);
    


}

