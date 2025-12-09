package project2.controller;
 
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project2.dto.RecipeDto;
import project2.service.RecipeService;

@RestController
@RequestMapping("/recipe/*")
public class SearchController {

	@Autowired   RecipeService service;
	
	@RequestMapping("/searchTest") 
	public String hi() { 
		// 처리하고
		return "Hi"; //값줄께
	} 
	
	@RequestMapping("/selectSearchTitle")
    public List<RecipeDto> selectSearchTitle(@RequestParam("search") String search) {
        return service.selectSearchTitle(search);
    }

    @RequestMapping("/selectSearchCategory")
    public List<RecipeDto> selectSearchCategory(@RequestParam("search") String search) {
        return service.selectSearchCategory(search);
    }
    
//    @RequestMapping("/searchBoth")
//    public List<RecipeDto> searchBoth(@RequestParam("search") String search) {
//        return service.searchBoth(search);
//    }
    
    @RequestMapping("/searchBothpaging") // 이 경로는 list.jsp의 AJAX URL과 동일해야 합니다.
    public Map<String, Object> searchBoth(

        @RequestParam("category") String category, 

        @RequestParam("keyword") String keyword,

        @RequestParam(value = "pstartno", defaultValue = "1") int pstartno // 현재 페이지 번호 받기

    ) {

        return service.searchBothPaging(category, keyword, pstartno); 

    }

    
    
}



 
//////// step1)
/*
@Controller
public class SearchController {

	@Autowired   Sboard1Service service;
	
	@RequestMapping("/searchTest")
	@ResponseBody
	public String hi() {
		// 처리하고
		return "Hi";  //값줄께
	} 
	
}
*/