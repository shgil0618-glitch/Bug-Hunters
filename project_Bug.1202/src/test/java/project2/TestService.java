package project2;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project2.service.RecipeService;
import project2.dto.RecipeDto;
import project2.dto.RecipeIngre;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
 "classpath:config/root-context.xml" , "classpath:config/security-context.xml"
})
public class TestService {

    @Autowired RecipeService recipeService;
    @Autowired ApplicationContext context;
    
    private static final int TEST_APP_USER_ID = 1;

     @Test
    public void testInsertRecipe() {
        System.out.println("--- 레시피 등록 테스트 시작 ---");

        // 1. DTO 기본 정보 설정
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeId(15);
        recipeDto.setAppUserId(TEST_APP_USER_ID);
        recipeDto.setTitle("JUnit 테스트 레시피: 매콤 짜장면");
        recipeDto.setCategory(2); // 중식 카테고리
        recipeDto.setImage("default_zazang.jpg"); // 이미지 URL
        recipeDto.setCookTime(35); // 조리시간
        recipeDto.setDifficulty("보통"); // 난이도
        recipeDto.setServings(2); // 인분
        recipeDto.setDescription("간단하게 만들 수 있는 퓨전 짜장 레시피입니다.");
        recipeDto.setInstructions("1. 재료 준비\\n2. 비비기\\n3. 맛있게 먹기");

        // 2. 재료 목록 설정 (RecipeIngre DTO 리스트)
        List<RecipeIngre> ingredients = Arrays.asList(
            new RecipeIngre(3, "돼지고기 (다짐육)", "200g"),
            new RecipeIngre(3, "양파", "1개"),
            new RecipeIngre(3, "춘장", "100g"),
            new RecipeIngre(3, "감자", "1개")
        );
        recipeDto.setIngredients(ingredients);
        
        // 3. Mock MultipartFile 설정 (단계별 이미지 시뮬레이션)
        MockMultipartFile file1 = new MockMultipartFile(
            "file1", "step1_chop.png", "image/png", "step 1 content".getBytes()
        );
        MockMultipartFile file2 = new MockMultipartFile(
            "file2", "step2_fry.png", "image/png", "step 2 content".getBytes()
        );

        List<MultipartFile> files = Arrays.asList(file1, file2);

        // 4. Service 호출
        int result = recipeService.insert(recipeDto, files);

        assert result > 0 : "레시피 등록 실패"; 
        assert recipeDto.getRecipeId() > 0 : "등록 후 RECIPE_ID를 반환받지 못했습니다.";
        
        System.out.println("새로 생성된 RECIPE_ID: " + recipeDto.getRecipeId());
    }

    @Ignore @Test
    public void testSelectRecipeDetail() {
        System.out.println("--- 레시피 상세 조회 테스트 시작 ---");

        int existingRecipeId = 7; // 테스트용 ID (반드시 실제 DB ID로 변경)

        RecipeDto detailBefore = recipeService.selectRecipeDetail(existingRecipeId);

        assert detailBefore != null : "레시피 ID " + existingRecipeId + "에 대한 상세 정보를 찾을 수 없습니다.";
        System.out.println("레시피 제목: " + detailBefore.getTitle());

        int viewsBefore = detailBefore.getViews();
        System.out.println("조회수 (조회 전): " + viewsBefore);

        RecipeDto detailAfter = recipeService.selectRecipeDetail(existingRecipeId);

        int viewsAfter = detailAfter.getViews();
        System.out.println("조회수 (조회 후): " + viewsAfter);

        assert viewsAfter > viewsBefore : "조회수 증가 실패";
        
        System.out.println("--- 레시피 상세 조회 테스트 성공 ---");
    }

    @Ignore @Test
    public void testDeleteRecipe() {
        System.out.println("--- 레시피 삭제 테스트 시작 ---");

        int recipeIdToDelete = 8;

        int result = recipeService.delete(recipeIdToDelete, TEST_APP_USER_ID);

        System.out.println("삭제된 레코드 수: " + result);
        assert result > 0 : "레시피 삭제 실패";

        RecipeDto deleted = recipeService.selectRecipeDetail(recipeIdToDelete);
        assert deleted == null : "레시피 삭제 후에도 데이터가 남아 있습니다.";

        System.out.println("--- 레시피 삭제 테스트 성공 ---");
    }
}
