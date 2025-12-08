package project2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import project2.controller.RecipeController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:config/root-context.xml",
    "classpath:config/security-context.xml"
})
public class TestController {

	@Autowired  ApplicationContext context; //3. ioc - Bean (스프링이 관리하는 객체) 생성~소멸
	@Autowired  DataSource   ds;
	@Autowired  SqlSession   session; 
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RecipeController())
                .build();
    }

    /**
     * 레시피 목록 페이지 테스트
     */
    @Test
    public void testRecipeList() throws Exception {
        mockMvc.perform(get("/recipe/list"))
               .andExpect(status().isOk())  // HTTP 상태 코드 확인
               .andExpect(view().name("/recipe/list"))  // 뷰 이름 확인
               .andExpect(model().attributeExists("list"));  // 모델에 "list" 속성이 존재하는지 확인
    }

    /**
     * 레시피 상세 조회 페이지 테스트
     */
    @Test
    public void testRecipeDetail() throws Exception {
        int recipeId = 1; // 존재하는 레시피 ID (실제 DB에 존재하는 ID로 변경)

        mockMvc.perform(get("/recipe/detail")
               .param("recipeId", String.valueOf(recipeId)))
               .andExpect(status().isOk())  // HTTP 상태 코드 확인
               .andExpect(view().name("/recipe/detail"))  // 뷰 이름 확인
               .andExpect(model().attributeExists("recipe"));  // 모델에 "recipe" 속성이 존재하는지 확인
    }

    /**
     * 레시피 등록 폼 페이지 테스트
     */
    @Test
    public void testRecipeRegisterForm() throws Exception {
        mockMvc.perform(get("/recipe/register"))
               .andExpect(status().isOk())  // HTTP 상태 코드 확인
               .andExpect(view().name("/recipe/register"));  // 뷰 이름 확인
    }

    /**
     * 레시피 등록 처리 테스트
     */
    @Test
    public void testRecipeRegister() throws Exception {
        mockMvc.perform(post("/recipe/register")
               .param("title", "Test Recipe")
               .param("category", "2")
               .param("cookTime", "30")
               .param("difficulty", "쉬움")
               .param("servings", "2")
               .param("description", "Test description"))
               .andExpect(status().is3xxRedirection())  // 리다이렉트 상태 코드 확인
               .andExpect(view().name("redirect:/recipe/list"));  // 리다이렉트 후 URL 확인
    }
}
