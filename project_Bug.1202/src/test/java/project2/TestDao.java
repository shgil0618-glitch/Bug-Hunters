package project2;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project2.dao.RecipeDao;
import project2.dto.RecipeDto;
import project2.dto.RecipeImage;
import project2.dto.RecipeIngre;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
 "classpath:config/root-context.xml", "classpath:config/security-context.xml"
})
public class TestDao {

    @Autowired
    ApplicationContext context; // 3. IoC - Bean (스프링이 관리하는 객체) 생성 ~ 소멸
    @Autowired
    DataSource ds;
    @Autowired
    SqlSession session;
    @Autowired
    private RecipeDao dao;

    private static final int TEST_APP_USER_ID = 1; // 테스트용 사용자 ID (실제 DB에 존재하는 값으로 설정)

    // 1. 레시피 등록 테스트
     @Test
    public void testInsertRecipe() {
        System.out.println("--- 레시피 등록 테스트 시작 ---");

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeId(2);
        recipeDto.setAppUserId(TEST_APP_USER_ID);
        recipeDto.setTitle("JUnit 테스트 레시피: 매콤 짜장면");
        recipeDto.setCategory(2); // 중식 카테고리
        recipeDto.setImage("default_zazang.jpg"); // 이미지 URL
        recipeDto.setCookTime(35); // 조리시간
        recipeDto.setDifficulty("보통"); // 난이도
        recipeDto.setServings(2); // 인분
        recipeDto.setDescription("간단하게 만들 수 있는 퓨전 짜장 레시피입니다.");
        recipeDto.setInstructions("1. 재료 준비\\n2. 비비기\\n3. 맛있게 먹기");
        
     

        int result = dao.insert(recipeDto);

        // 레시피가 성공적으로 등록되었는지 확인
        assert result > 0 : "레시피 등록 실패";
        assert recipeDto.getRecipeId() > 0 : "등록 후 레시피 ID를 반환받지 못했습니다.";

        System.out.println("등록된 레시피 ID: " + recipeDto.getRecipeId());
    }

    // 2. 레시피 조회 테스트
    @Ignore @Test
    public void testSelectRecipe() {
        System.out.println("--- 레시피 조회 테스트 시작 ---");

        int recipeId = 1;  // 실제 DB에 존재하는 레시피 ID로 변경

        RecipeDto recipeDto = dao.select(recipeId);

        // 레시피 정보가 정상적으로 조회되는지 확인
        assert recipeDto != null : "레시피 정보가 조회되지 않았습니다.";
        System.out.println("레시피 제목: " + recipeDto.getTitle());
    }

    // 3. 레시피 이미지 조회 테스트
    @Ignore @Test
    public void testSelectRecipeImages() {
        System.out.println("--- 레시피 이미지 조회 테스트 시작 ---");

        int recipeId = 1; // 실제 레시피 ID

        List<RecipeImage> images = dao.selectRecipeImages(recipeId);

        // 이미지 목록이 정상적으로 조회되는지 확인
        assert images != null && !images.isEmpty() : "레시피 이미지가 조회되지 않았습니다.";

        System.out.println("조회된 이미지 수: " + images.size());
    }

    // 4. 레시피 재료 조회 테스트
    @Ignore @Test
    public void testSelectRecipeIngredients() {
        System.out.println("--- 레시피 재료 조회 테스트 시작 ---");

        int recipeId = 1; // 실제 레시피 ID

        List<RecipeIngre> ingredients = dao.selectRecipeIngredients(recipeId);

        // 재료 목록이 정상적으로 조회되는지 확인
        assert ingredients != null && !ingredients.isEmpty() : "레시피 재료가 조회되지 않았습니다.";

        System.out.println("조회된 재료 수: " + ingredients.size());
    }

    // 5. 레시피 조회수 증가 테스트
    @Ignore @Test
    public void testIncrementRecipeViews() {
        System.out.println("--- 레시피 조회수 증가 테스트 시작 ---");

        int recipeId = 1; // 실제 레시피 ID

        // 조회수 증가 전 레시피 조회
        RecipeDto beforeRecipe = dao.select(recipeId);
        int beforeViews = beforeRecipe.getViews();
        System.out.println("조회수 증가 전: " + beforeViews);

        // 조회수 증가
        int result = dao.incrementRecipeViews(recipeId);
        assert result > 0 : "조회수 증가 실패";

        // 조회수 증가 후 레시피 조회
        RecipeDto afterRecipe = dao.select(recipeId);
        int afterViews = afterRecipe.getViews();
        System.out.println("조회수 증가 후: " + afterViews);

        // 조회수가 증가했는지 확인
        assert afterViews > beforeViews : "조회수가 증가하지 않았습니다.";
    }

    // 6. 레시피 삭제 테스트 (Cascade 적용 확인)
    @Ignore @Test
    public void testDeleteRecipe() {
        System.out.println("--- 레시피 삭제 테스트 시작 ---");

        int recipeId = 2; // 삭제할 레시피 ID (실제 DB에 존재하는 값으로 변경)

        // 레시피 삭제
        int result = dao.delete(recipeId);

        // 레시피 삭제 후 이미지와 재료가 삭제되었는지 확인
        List<RecipeImage> images = dao.selectRecipeImages(recipeId);
        assert images == null || images.isEmpty() : "이미지가 삭제되지 않았습니다.";

        List<RecipeIngre> ingredients = dao.selectRecipeIngredients(recipeId);
        assert ingredients == null || ingredients.isEmpty() : "재료가 삭제되지 않았습니다.";

        // 레시피가 성공적으로 삭제되었는지 확인
        assert result > 0 : "레시피 삭제 실패";

        System.out.println("삭제된 레시피 ID: " + recipeId);
    }

    // 7. 전체 레시피 목록 조회 테스트
    @Ignore @Test
    public void testSelectAllRecipes() {
        System.out.println("--- 전체 레시피 목록 조회 테스트 시작 ---");

        List<RecipeDto> recipeList = dao.selectAll();

        // 전체 레시피 목록이 정상적으로 조회되는지 확인
        assert recipeList != null && !recipeList.isEmpty() : "전체 레시피 목록 조회 실패";

        System.out.println("조회된 레시피 수: " + recipeList.size());
    }
}
