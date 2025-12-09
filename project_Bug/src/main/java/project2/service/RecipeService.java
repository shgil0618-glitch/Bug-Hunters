package project2.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import project2.dto.RecipeDto;

public interface RecipeService {

    /**
     * 🟢 레시피 등록
     * 레시피 기본 정보(RecipeDto)와 여러 개의 레시피 이미지 파일을 받아서 처리합니다.
     * 이 메서드는 RECIPES, recipes_img, recipes_ingre_map, recipes_ingre 테이블에 걸쳐 데이터 삽입을 처리합니다.
     * @param dto 레시피 기본 정보 (재료 목록 포함)
     * @param files 레시피 순서 이미지 파일 목록
     * @return 성공적으로 등록된 레코드 수
     */
    public int insert(RecipeDto dto, List<MultipartFile> files);

    /**
     * 🔵 전체 레시피 조회 (목록)
     * 모든 레시피의 간단 정보를 조회합니다. (메인 페이지 등)
     * @return 레시피 DTO 목록
     */
    public List<RecipeDto> selectAll();

    /**
     * 🔵 내가 작성한 레시피 조회 (마이페이지)
     * 특정 사용자 ID로 작성된 레시피 목록을 조회합니다.
     * @param appUserId 사용자 ID
     * @return 해당 사용자의 레시피 DTO 목록
     */
    public List<RecipeDto> selectMyRecipes(int appUserId);

    /**
     * 🔵 레시피 상세 조회
     * 특정 레시피의 기본 정보, 이미지, 재료 목록을 모두 포함하여 조회하고, 조회수를 증가시킵니다.
     * @param recipeId 레시피 ID
     * @return 레시피 상세 정보 DTO
     */
    public RecipeDto selectRecipeDetail(int recipeId);

    /**
     * 🟠 레시피 수정
     * 레시피 기본 정보, 재료, 이미지를 수정합니다.
     * 이미지는 기존 것을 삭제하고 새로 업로드하는 방식으로 처리하며, 재료 정보도 갱신합니다.
     * @param dto 수정할 레시피 정보 (recipeId 필수)
     * @param files 새로 업로드할 레시피 순서 이미지 파일 목록 (없으면 null)
     * @return 성공적으로 수정된 레코드 수
     */
    public int update(RecipeDto dto, List<MultipartFile> files);

    /**
     * 🔴 레시피 삭제
     * 레시피를 삭제하고 CASCADE 기능을 통해 모든 연관 데이터(이미지, 재료)를 제거합니다.
     * @param recipeId 삭제할 레시피 ID
     * @param appUserId 삭제를 요청한 사용자 ID (권한 확인용)
     * @return 성공적으로 삭제된 레코드 수
     */
    public int delete(int recipeId, int appUserId);

    /**
     * 레시피 조회수를 증가시킵니다.
     * @param recipeId 레시피 ID
     * @return 조회수가 증가한 레시피의 수
     */
    public int incrementRecipeViews(int recipeId);
    
 // ⭐️ [페이징 추가] 전체 레시피 개수 조회
    int getTotalRecipeCount();
    
    // ⭐️ [페이징 추가] 현재 페이지 번호를 받아 목록을 조회 (Sboard1 방식)
    List<RecipeDto> selectRecipeListPaging(int currentPage);
    
    // 카테고리 이름 조회
    public String selectCategoryNameById(int categoryId);
    
 // search service
    public List<RecipeDto> selectSearchTitle(String keyword);
    public List<RecipeDto> selectSearchCategory(String keyword);
    public List<RecipeDto> searchBoth(String keyword);
    
    public Map<String, Object> searchBothPaging(String category, String keyword, int page);

}
