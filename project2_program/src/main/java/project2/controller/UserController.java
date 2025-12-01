package project2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project2.dto.AppUserAuthDto;
import project2.dto.AuthDto;
import project2.dto.UserDto;
import project2.service.UserService;

@Controller
public class UserController {

    @Autowired 
    UserService service;
   

    ///////////////////////////////////////////////////////////
    // ȸ�� ��� ������
    @RequestMapping("/list.user")
    public String listuser(Model model) {
        model.addAttribute("List", service.selectAll()); 
        return "member/list"; 
    }

    ///////////////////////////////////////////////////////////
    // ȸ������ ��
    @RequestMapping(value="/join.user", method=RequestMethod.GET)
    public String joinPage() {
        return "member/join"; 
    }

    // ȸ������ ���
    @RequestMapping(value="/join.user", method=RequestMethod.POST)
    public String joinUser(UserDto dto, RedirectAttributes rttr) {
    	
    	
    	
        String msg = "회원가입에 실패했습니다";
        if(service.insert(dto) > 0) {
            msg = "회원가입에 성공했습니다.";
        }
        rttr.addFlashAttribute("success", msg);
        return "redirect:/login.user";
    }
    
   


    ///////////////////////////////////////////////////////////
    // �α��� ��
    @RequestMapping(value="/login.user", method=RequestMethod.GET)
    public String loginPage() {
        return "member/login"; 
    }

	/*
	 * // �α��� ���
	 * 
	 * @RequestMapping(value="/login.user", method=RequestMethod.POST) public String
	 * loginUser(UserDto dto, HttpSession session, RedirectAttributes rttr) {
	 * UserDto loginUser = service.login(dto);
	 * 
	 * if(loginUser == null) { rttr.addFlashAttribute("error",
	 * "���̵� �Ǵ� ��й�ȣ�� Ʋ���ϴ�."); return "redirect:/login.user"; }
	 * 
	 * session.setAttribute("loginUser", loginUser); return "redirect:/mypage.user";
	 * }
	 */
    
    // �α��� ���
    @RequestMapping(value="/login.user", method=RequestMethod.POST)
    public String loginUser(UserDto dto, HttpSession session, RedirectAttributes rttr) {

        UserDto loginUser = service.login(dto);

        if(loginUser == null) {
            rttr.addFlashAttribute("error", "아이디/비밀번호를 확인해주세요.");
            return "redirect:/login.user"; 
        }

        
        session.setAttribute("loginUser", loginUser);
        rttr.addFlashAttribute("success", "로그인에 성공했습니다.");
        return "redirect:/mypage.user";  
    }



    ///////////////////////////////////////////////////////////
    // ����������
    @RequestMapping("/mypage.user")
    public String myPage(HttpServletRequest  request , Model model) {
    	HttpSession session = request.getSession(); 
		String email = (String)session.getAttribute("email"); 
		model.addAttribute("dto", service.selectEmail(email)); 
		return "member/mypage"; 
//        UserDto loginUser = (UserDto)session.getAttribute("loginUser");
//        if(loginUser == null) return "redirect:/login.user"; 
//
//        model.addAttribute("dto", service.select(loginUser.getAppUserId()));
//        return "member/mypage"; 
    }

    ///////////////////////////////////////////////////////////
    // ȸ������ ���� ��
    @RequestMapping(value="/edit.user", method=RequestMethod.GET)
    public String editPage(HttpSession session, Model model) {
        UserDto loginUser = (UserDto)session.getAttribute("loginUser");
        if(loginUser == null) return "redirect:/login.user";

        model.addAttribute("dto", service.select(loginUser.getAppUserId()));
        return "member/edit"; 
    }

    // ȸ������ ���� ���
    @RequestMapping(value="/edit.user", method=RequestMethod.POST)
    public String editUser(UserDto dto, RedirectAttributes rttr) {
        String msg = "글수정에 실패했습니다.";
        if(service.update(dto) > 0) msg = "글수정에 성공했습니다.";

        rttr.addFlashAttribute("success", msg);
        return "redirect:/mypage.user"; 
    }

    ///////////////////////////////////////////////////////////
	/*
	 * // ȸ�� Ż�� ���
	 * 
	 * @RequestMapping(value="/delete.user", method=RequestMethod.GET) public String
	 * deletePage() { return "member/delete"; }
	 * 
	 * // ȸ�� Ż�� �� ���𷺼�
	 * 
	 * @RequestMapping(value="/delete.user", method=RequestMethod.POST) public
	 * String deleteUser(UserDto dto, HttpSession session, RedirectAttributes rttr)
	 * { String msg = "��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
	 * 
	 * if(service.delete(dto) > 0) { msg = "ȸ��Ż�� ����"; session.invalidate(); return
	 * "redirect:/list.user"; }
	 * 
	 * rttr.addFlashAttribute("error", msg); return "redirect:/mypage.user"; }
	 */
    @RequestMapping(value="/delete.user", method=RequestMethod.POST)
    public String deleteUser(UserDto dto, HttpSession session, RedirectAttributes rttr) {
        String msg = "비밀번호를 확인해주세요."; // �⺻ �޽���

        // service.delete(dto) ȣ���ϱ� ���� ��й�ȣ Ȯ���� �ؾ� �մϴ�.
        UserDto user = service.select(dto.getAppUserId()); // appUserId�� ����� ���� ��������

        if (user != null && user.getPassword().equals(dto.getPassword())) {
            // ��й�ȣ�� ������ ���� ����
            if (service.delete(dto) > 0) {
                msg = "ȸ��Ż�� ����";
                session.invalidate(); 
                return "redirect:/list.user"; 
            }
        }
		rttr.addFlashAttribute("error", "글삭제에 성공했습니다.");
        return "redirect:/list.user"; 
    }


    ///////////////////////////////////////////////////////////
    // �α׾ƿ�
    @RequestMapping("/logout.user")
    public String logout(HttpSession session,RedirectAttributes rttr) {
    	rttr.addFlashAttribute("success", "로그아웃 되었습니다.");
    	
        session.invalidate(); 
        return "redirect:/list.user"; 
    }
///////////////////////////////////////////////////////////
 // ȸ������ ���ε� ���
	
	  @RequestMapping(value="/join2.user", method=RequestMethod.POST) public String
	  joinUser2(@RequestParam("file")MultipartFile file,UserDto dto,
	  RedirectAttributes rttr) {
	  
	  String msg = "회원가입 실패"; if(service.insert2(file,dto) > 0) { msg =
	  "회원가입에 성공했습니다."; } rttr.addFlashAttribute("success", msg); return
	  "redirect:/login.user"; }
	  
	  
    
///////////////////////////////////////////////////////////
    // ȸ������ ���� ���ε� ���
    @RequestMapping(value="/edit2.user", method=RequestMethod.POST)
    public String editUser(@RequestParam("file")MultipartFile file,UserDto dto, RedirectAttributes rttr) {
        String msg = "글수정 실패";
        if(service.update2(file,dto) > 0) msg = "글수정 성공";

        rttr.addFlashAttribute("success", msg);
        return "redirect:/mypage.user"; 
    }
///////////////////////////////////////////////////////////
    
    
///////////////////////////////////////////////////////////
    
///////////////////////////////////////////////////////////
    
}
