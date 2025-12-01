package project2.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project2.dto.AppUser;
import project2.service.AppUserSecurityService;
import project2.service.UserService;




@Controller
@RequestMapping("/security/*")
public class AppUserSecurityController {
	
	@Autowired AppUserSecurityService service;
	
	
	@RequestMapping("/all")
	public String all() {return"/member/all";}
		
	@RequestMapping("/member")
	public String member() {return"/member/member";}
	
	// 회원가입
	@RequestMapping("/join")	//회원가입 폼
	public String joinForm() {return"/member/join";}
	
	@PreAuthorize("isAnonymous()")
	@RequestMapping(value="/join", method=RequestMethod.POST, headers=("content-type=multipart/*"))	//회원가입 기능
	public String join(@RequestParam("file") MultipartFile file, AppUser dto, RedirectAttributes rttr) { 
		
		String result = "회원가입 실패";
		
		if(service.insert(file, dto) > 0 ) {result = "회원가입 성공";}
		rttr.addFlashAttribute("success",result);
		
		return"redirect:/security/login";
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
	@RequestMapping("/mypage")	//마이페이지 - 로그인정보 Principal
	public String mypage(Principal principal, Model model) {
		model.addAttribute("dto", service.selectEmail( principal.getName() ));
		return"/member/mypage";
		}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/edit")
	public String updateForm( Principal principal, Model model) {
		model.addAttribute("dto", service.selectEmail(principal.getName() ));
		return "/member/edit";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/edit", method=RequestMethod.POST, headers=("content-type=multipart/*"))	//수정 
	public String update(@RequestParam("file") MultipartFile file, AppUser dto, RedirectAttributes rttr) {
		String result = "비밀번호를 확인해주세요";
		if(service.update(file, dto) > 0) {
			result = "수정 성공";
		}
		rttr.addFlashAttribute("success", result);

		return"redirect:/security/mypage";
		}

	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/delete")
	public String deleteForm( Principal principal, Model model) {
		model.addAttribute("dto", service.selectEmail(principal.getName() ));
		return "/member/delete";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/delete", method=RequestMethod.POST)	//삭제 
	public String delete( AppUser dto, RedirectAttributes rttr, HttpServletRequest request, HttpServletResponse response) {
		
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
