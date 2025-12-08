package project2;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import project2.dao.RecipeDao;
import project2.dto.RecipeDto;
import project2.dto.RecipeIngre;
import project2.service.RecipeService;

// Spring 컨텍스트 로딩 설정 (기존 TestDB와 동일)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:config/root-context.xml" , "classpath:config/security-context.xml"
})
public class TestDB {
    
    @Autowired RecipeService recipeService;
    @Autowired  ApplicationContext context; //3. ioc - Bean (스프링이 관리하는 객체) 생성~소멸
	@Autowired  DataSource   ds;
	@Autowired  SqlSession   session; 
	@Autowired  RecipeDao   dao;
	
	@Test
    public void test1() {
		System.out.println(context);
	}
	@Test
    public void test2() {
		System.out.println(ds);
	}
	@Test
    public void test3() {
		System.out.println(session);
	}

    private static final int TEST_APP_USER_ID = 50; // 테스트를 위한 APP_USER_ID (실제 DB에 존재하는 ID로 변경 필요)

    /**
     * 레시피 등록 테스트 (insertRecipe)
     * - RECIPES, recipes_img, recipes_ingre_map, recipes_ingre 테이블에 데이터가 모두 삽입되는지 검증.
     * - MultipartFile 처리 로직 포함.
     */
   @Ignore @Test
    public void testInsertRecipe() {
        System.out.println("--- 레시피 등록 테스트 시작 (testInsertRecipe) ---");

        // 1. DTO 기본 정보 설정
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setAppUserId(TEST_APP_USER_ID); 
        recipeDto.setTitle("JUnit 테스트 레시피: 매콤 짜장면");
        recipeDto.setCategory(2); // 중식 카테고리 ID
        recipeDto.setImage("default_zazang.jpg"); // 대표 이미지 URL
        recipeDto.setCookTime(35);
        recipeDto.setDifficulty("보통");
        recipeDto.setServings(2);
        recipeDto.setDescription("간단하게 만들 수 있는 퓨전 짜장 레시피입니다.");
        // recipeDto.setInstructions 는 단계별로 나뉘어 있어 서비스에서 처리됩니다.

        // 2. 재료 목록 설정 (RecipeIngre DTO 리스트)
        List<RecipeIngre> ingredients = Arrays.asList(
            new RecipeIngre(2, "돼지고기 (다짐육)", "200g"),
            new RecipeIngre(2, "양파", "1개"),
            new RecipeIngre(2, "춘장", "100g"),
            new RecipeIngre(2, "감자", "1개")
        );
        recipeDto.setIngredients(ingredients);
        
        // 3. Mock MultipartFile 설정 (단계별 이미지 시뮬레이션)
        MockMultipartFile file1 = new MockMultipartFile(
            "file1", "step1_chop.png", "image/png", "step 1 content".getBytes()
        );
        MockMultipartFile file2 = new MockMultipartFile(
            "file2", "step2_fry.png", "image/png", "step 2 content".getBytes()
        );

        // List<MultipartFile>로 타입 맞추기
        List<MultipartFile> files = Arrays.asList(file1, file2);

        // 4. Service 호출
        int result = recipeService.insert(recipeDto, files);

        
        // 5. 결과 검증
        // 1 (RECIPES) + 2 (IMAGES) + 4 (INGREDIENT_MAP/DETAIL 쌍) = 7 이상의 값이 나와야 함
        System.out.println("DB 작업 성공 레코드 수 (예상 7 이상): " + result);
        assert result > 0 : "레시피 등록 실패"; 
        assert recipeDto.getRecipeId() > 0 : "등록 후 RECIPE_ID를 반환받지 못했습니다.";
        
        System.out.println("새로 생성된 RECIPE_ID: " + recipeDto.getRecipeId());
    }


    /**
     * 레시피 상세 조회 테스트 (selectRecipeDetail)
     * - 레시피 기본 정보, 이미지 목록, 재료 목록이 모두 DTO에 담겨 오는지 검증.
     * - 조회수 증가 로직이 작동하는지 검증.
     */
    @Ignore @Test
    public void testSelectRecipeDetail() {
        System.out.println("--- 레시피 상세 조회 테스트 시작 (testSelectRecipeDetail) ---");
        
        // **주의**: 아래 recipeId는 위 testInsertRecipe 실행 후 DB에 실제 존재하는 ID로 수동 변경해야 합니다.
        int existingRecipeId = 1; // 테스트용 ID (반드시 실제 DB ID로 변경)
        
        RecipeDto detail = recipeService.selectRecipeDetail(existingRecipeId);

        // 1. 기본 검증
        assert detail != null : "레시피 ID " + existingRecipeId + "에 대한 상세 정보를 찾을 수 없습니다.";
        System.out.println("레시피 제목: " + detail.getTitle());
        System.out.println("작성자 닉네임: " + detail.getNickname()); // NICKNAME 매핑 확인

        // 2. 이미지 목록 검증
        List<?> images = detail.getImages();
        assert images != null : "이미지 목록이 null입니다.";
        System.out.println("이미지 수: " + images.size());
        
        // 3. 재료 목록 검증
        List<?> ingredients = detail.getIngredients();
        assert ingredients != null : "재료 목록이 null입니다.";
        assert ingredients.size() > 0 : "재료 목록이 비어 있습니다.";
        System.out.println("재료 수: " + ingredients.size());
        
        // 4. 조회수 증가 확인 (테스트 실행 전/후 비교는 Service 단에서 이루어지므로, 여기서 데이터가 잘 들어왔는지 확인)
        System.out.println("조회수 (증가 후): " + detail.getViews());
        
        System.out.println("--- 레시피 상세 조회 테스트 성공 ---");
    }

    /**
     * 레시피 삭제 테스트 (deleteRecipe)
     * - RECIPES와 모든 연관 데이터(이미지, 재료)가 삭제되는지 검증.
     */
    @Ignore @Test
    public void testDeleteRecipe() {
        System.out.println("--- 레시피 삭제 테스트 시작 (testDeleteRecipe) ---");
        
        // **주의**: 삭제할 레시피 ID와 작성자 ID (권한 확인용)를 설정해야 합니다.
        int recipeIdToDelete = 1; // 삭제할 레시피 ID
        
        int result = recipeService.delete(recipeIdToDelete, TEST_APP_USER_ID);
        
        System.out.println("삭제된 레코드 수: " + result);
        assert result > 0 : "레시피 삭제 실패";
        
        // 삭제 후 조회하여 null인지 확인 (추가적인 검증 로직)
        RecipeDto deleted = recipeService.selectRecipeDetail(recipeIdToDelete);
        assert deleted == null : "레시피 삭제 후에도 데이터가 남아 있습니다.";

        System.out.println("--- 레시피 삭제 테스트 성공 ---");
    }

}