package project2.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project2.dao.AppUserMapper;
import project2.dto.AppUserDto;
import project2.dto.PagingDto;
import project2.dto.RecipeDto;
import project2.service.AppUserSecurityService;
import project2.service.RecipeService;

@Controller
@RequestMapping("/security/*")
public class AppUserSecurityController {
	
	@Autowired AppUserSecurityService service;
	@Autowired RecipeService recipeService;
	
	/*
	@GetMapping("/main")
	public String main(Model model,
	                   @RequestParam(value = "pstartno", defaultValue = "1") int pstartno) {
	        
	    int realTotalCount = recipeService.getTotalRecipeCount();
	   
	    int adjustedTotalCount = realTotalCount;
	    if (realTotalCount > 0 && realTotalCount %  8!= 0) {
	        adjustedTotalCount = ((realTotalCount / 8) + 1) * 8;
	    }

	    PagingDto paging = new PagingDto(adjustedTotalCount, pstartno);
	    model.addAttribute("paging", paging);

	    List<RecipeDto> recipeList = recipeService.selectRecipeListPaging(pstartno);
	    model.addAttribute("list", recipeList);
	    
	    return "/member/main";
	}
	*/

	// 이메일 찾기 페이지 (GET, JSP 렌더링)
    @RequestMapping(value="/findEmail", method=RequestMethod.GET)
    public String findEmailPage() {
        return "/member/findEmail"; // JSP 경로에 맞게 조정
    }

    // 이메일 찾기 처리 (POST, JSON 반환)
    @RequestMapping(value="/findEmail", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> findEmail(@RequestParam("mobile") String mobile) {
        Map<String, Object> result = new HashMap<>();
        String email = service.findEmailByMobile(mobile);
        result.put("emailResult", email != null ? email : "");
        return result;
    }


    // 비밀번호 찾기 페이지 (GET, JSP 렌더링)
    @RequestMapping(value="/findPassword", method=RequestMethod.GET)
    public String findPasswordPage() {
        return "/member/findPassword"; // JSP 경로에 맞게 조정
    }

    // 비밀번호 찾기 처리 (POST, JSON 반환)
    @RequestMapping(value="/findPassword", method=RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> findPassword(@RequestParam("email") String email,
                                            @RequestParam("mobile") String mobile,
                                            @RequestParam("newPassword") String newPassword) {
        Map<String, Object> result = new HashMap<>();
        int updated = service.findPassword(email, mobile, newPassword);
        result.put("resetResult", updated > 0 ? "success" : "fail");
        return result;
    
    }  
    
    // 닉네임 중복 체크
    @RequestMapping(value="/security/nickdouble", method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> nickdouble(@RequestParam String nickname){
		Map<String,Object> result = new HashMap<>();
		result.put("cnt", service.selectNickname(nickname));
		return result;
	}

	// 아이디 중복 체크
	@RequestMapping(value="iddouble", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> iddouble(@RequestParam String email){
		Map<String,Object> result = new HashMap<>();
		result.put("cnt", service.selectEmail(email));
		return result;
	}
	
	// 휴대폰 번호 중복 체크
	@RequestMapping(value="mobiledouble", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> mobiledouble(@RequestParam String mobile){
	    Map<String,Object> result = new HashMap<>();
	    result.put("cnt", service.selectMobile(mobile));
	    return result;
	}
	
	// 멤버 등급 페이지
	@RequestMapping("/all")
	public String all() { return "/member/all";}
	@RequestMapping("/member")
	public String member() { return "/member/all";}
	
	
	// 회원가입
	@RequestMapping("/join")
	public String joinForm() { 
	    return "/member/join";
	}

	@PreAuthorize("isAnonymous()")
	@RequestMapping(
	    value = "/join",
	    method = RequestMethod.POST,
	    headers = ("content-type=multipart/*"),
	    produces = "application/json;charset=UTF-8"
	)
	@ResponseBody
	public Map<String, Object> join(@RequestParam("file") MultipartFile file, AppUserDto dto) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        int inserted = service.insert(file, dto);
	        if (inserted > 0) {
	            result.put("cnt", 1);  
	        } else {
	            result.put("cnt", 0); 
	        }
	    } catch (DuplicateKeyException e) {
	        result.put("cnt", -1);   
	    } catch (Exception e) {
	        result.put("cnt", 0);    
	    }
	    return result;
	}

	
	
	// 로그인
	@RequestMapping("/login") //로그인 폼
	   public String loginForm() {return"/member/login";}
	   
	   @RequestMapping("/fail") // 로그인 실패
	   public String fail(HttpServletRequest response , RedirectAttributes rttr) {
	      String result = "로그인 실패";
	      rttr.addFlashAttribute("loginError", result);
	      
	      return"redirect:/security/login";
	      }
	   
	   // 마이페이지
	   @RequestMapping("/mypage")   //마이페이지 - 로그인정보 Principal
	   public String mypage(Principal principal, Model model) {
	      model.addAttribute("dto", service.selectEmail(principal.getName()));
	      
	      return"/member/mypage";
	      }
	   
	   // 수정
	   @PreAuthorize("isAuthenticated()")
	   @RequestMapping("/update")
	   public String updateForm( Principal principal, Model model) {
	      model.addAttribute("dto", service.selectEmail(principal.getName() ));
	      return "/member/edit";
	   }
	   
	   
	   @PreAuthorize("isAuthenticated()")
	      @RequestMapping(value="/update", method=RequestMethod.POST, headers=("content-type=multipart/*"))   //수정 
	      public String update(@RequestParam("file") MultipartFile file, AppUserDto dto, RedirectAttributes rttr) {
	         String result = "비밀번호를 확인해주세요";
	         if(service.update(file, dto) > 0) {
	            result = "수정 성공";
	         }
	         rttr.addFlashAttribute("success", result);

	         return"redirect:/security/mypage";
	         }

		// 삭제
		@RequestMapping("/delete") 
		public String delete( Principal principal, Model model) {
			model.addAttribute("dto", service.selectEmail(principal.getName()));
			return "/member/delete";}
		
		@PreAuthorize("isAuthenticated()")
		   @RequestMapping(value="/delete", method=RequestMethod.POST)   //삭제 
		   public String delete( AppUserDto dto, RedirectAttributes rttr, HttpServletRequest request, HttpServletResponse response) {
		      
		      String result = "비밀번호를 확인해주세요";
		      if(service.delete(dto) > 0) {
		         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		         if(auth != null) {
		            new SecurityContextLogoutHandler().logout(request, response, auth);}
		         
		         result = "회원 탈퇴 성공";
		         rttr.addFlashAttribute("success", result);
		         return "redirect:/security/login";
		      }else {
		         result = "비밀번호를 확인해주세요";
		      rttr.addFlashAttribute("deleteError", result);
		      return"redirect:/security/mypage";
		      }
		   }
	
}
