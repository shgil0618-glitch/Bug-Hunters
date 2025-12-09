package project2.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project2.dto.MaterialDto;
import project2.dto.PagingDto;
import project2.service.AppUserSecurityService;
import project2.service.MaterialService;
import project2.service.RecipeService;

@Controller
public class MaterialController {
	@Autowired 
	  RecipeService recipeService;
	
	@Autowired
    AppUserSecurityService userService;
	
	@Autowired MaterialService Service;
	
	@GetMapping("/materialdetail") //materialdetail?materialid=2
    public String detail(@RequestParam(value="materialid") int materialid, Model model) {
        MaterialDto dto = Service.selectMaterial(materialid);
        model.addAttribute("dto", dto);
        return "material/materialdetail";  //유저용 상세보기
    }
	//일반사용자용 상세보기
	 @GetMapping("/materialdetailAjax") //ajax상세보기 ajax 안쓰면 굳이 필요없음
	    public String materialdetailAjax(@RequestParam ("materialid")int materialid, Model model) {
	        MaterialDto dto = Service.selectMaterial(materialid);
	        model.addAttribute("dto", dto);
	        return "material/materialdetailAjax";   ///materialdetailAjax?materialid=1
	    }
	//관리자용 재료 전체 목록
//	 @GetMapping("/admin/materiallist")
//	    public String adminMaterialList(Model model, HttpSession session) {
//	        List<MaterialDto> list = Service.MaterialList();
//	        model.addAttribute("list", list);
//	        return "material/materiallist_admin"; ///admin/materiallist 
//	    }
	 @GetMapping("/admin/materiallist")
	    public String adminMaterialList(Model model,@RequestParam(value="pstartno", defaultValue="1") int pstartno
	    		) {
	        model.addAttribute("list", Service.select10(pstartno));
	        model.addAttribute("paging", new PagingDto(Service.selectTotalCnt(), pstartno ));
		        return "material/materiallist_admin"; ///admin/materiallist 
		    }
		 
	 //등록폼
	 @GetMapping("/admin/materialinsert")
	    public String materialinsertForm(HttpSession session) {
	        return "material/materialinsert"; //관리자용 등록 폼
	    }
	 
	 @PreAuthorize("isAuthenticated()")  //로그인했다면
	 @PostMapping(value="/admin/materialinsert", headers=("content-type=multipart/*"))
	    public String materialinsert(@RequestParam("file") MultipartFile file 
	    			, MaterialDto dto, RedirectAttributes rttr,  HttpSession session) {
		 	String result= "추가 실패";
		 	if(Service.insert2Material(file, dto) > 0 ) {result = "추가 성공";}
		 	rttr.addFlashAttribute("success", result);
	        return "redirect:/admin/materiallist"; ///admin/materiallist
	    }
	//수정폼
	 @GetMapping("/admin/materialedit")
	    public String materialeditForm(int materialid, Model model, HttpSession session) {
	        MaterialDto dto = Service.selectMaterial(materialid);
	        model.addAttribute("dto", dto);
	        return "material/materialedit"; //admin/materialedit?materialid=2
	    }
	 	@PreAuthorize("isAuthenticated()")
	    @PostMapping(value="/admin/materialedit", headers=("content-type=multipart/*"))
	    public String materialedit(@RequestParam("file") MultipartFile file
	    					, MaterialDto dto, HttpSession session, RedirectAttributes rttr) {
	    	String result ="수정 실패";
	        if(Service.update2Material(file, dto) > 0 ) {result = "수정 성공"; }
	        rttr.addFlashAttribute("success" , result);
	        return "redirect:/admin/materiallist";
	    }
	    //삭제폼
	    @GetMapping("/admin/materialdelete")
	    public String materialdelete(int materialid, RedirectAttributes rttr) {
	    	String result ="삭제 실패";
	    	if(Service.deleteMaterial(materialid) > 0) {result ="삭제 성공";} 
	    	rttr.addFlashAttribute("success", result);
	        return "redirect:/admin/materiallist"; //admin/materialdelete?materialid=2
	    }

	    @GetMapping("/materialtitle") //materialdetail?materialid=2
	    public String selecttitle(@RequestParam(value="title") String title, Model model) {
	        MaterialDto dto = Service.selectTitle(title);
	        model.addAttribute("dto", dto);
	        return "material/materialdetail";  //유저 연결용
	    }
//	    @GetMapping("/materialtitle")
//	    public String selecttitle(@RequestParam("title") String title, Model model) {
//
//	        MaterialDto dto = Service.selectTitle(title);
//
//	        // 재료가 없을 경우 404 에러 페이지로 리다이렉트
//	        if (dto == null) {
//	            return "error/404"; // 너의 404 JSP 파일 경로
//	        }
//
//	        model.addAttribute("dto", dto);
//	        return "material/materialdetail";  // 정상 조회 시 상세 페이지
//	    }
	    
	    @GetMapping("/materialsearch")
	    @ResponseBody
	    public   Map<String, Object>  detail(@RequestParam("recipeId") int recipeId ) { 
	    	Map<String, Object>   result = new HashMap<>();
	    	result.put("result", recipeService.selectRecipeDetail(recipeId));
	    	return result;
	    }
	    
	    @GetMapping("/material/check")
	    @ResponseBody
	    public boolean checkMaterial(@RequestParam String title) {
	        MaterialDto dto = Service.selectTitle(title);
	        return dto != null;
	    }
	    
	    ///materialtitle?title=
	  
//	    // 관리자 판별 함수
//	    private boolean isAdmin(HttpSession session) {
//	        Object role = session.getAttribute("role");
//	        return role != null && role.equals("ADMIN");
//	    }
	
}