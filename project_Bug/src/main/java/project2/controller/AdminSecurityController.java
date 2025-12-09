package project2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import project2.dto.AppUserDto;
import project2.dto.RecipeDto;
import project2.service.AdminSecurityService;
import project2.service.RecipeService;

@Controller
@RequestMapping("/security/admin")
public class AdminSecurityController {

    @Autowired
    private AdminSecurityService adminService;
    
    

    // 관리자 전용 페이지
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String adminList() {
        return "admin/list";
    }

    // 전체 유저 조회
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/selectAll", method=RequestMethod.GET)
    @ResponseBody
    public List<AppUserDto> selectAll(){
        return adminService.selectAll();
    }

    // 특정 유저 조회
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/select", method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> select(@RequestParam int appUserId){
        Map<String,Object> result = new HashMap<>();
        result.put("result", adminService.select(appUserId));
        return result;
    }

    // 유저 삭제
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/deleteAdmin", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
    @ResponseBody
    public Map<String,Object> deleteAdmin(@RequestParam int appUserId,
    									  @RequestParam String email){
        Map<String,Object> result = new HashMap<>();
        AppUserDto dto = new AppUserDto();
        dto.setEmail(email);
        dto.setAppUserId(appUserId);
        int cnt = adminService.delete(dto);
        result.put("result", cnt);
        return result;
    }
    
 // 유저 닉네임 수정
    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/updateNickname", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateNickname(@RequestParam int appUserId,
                                              @RequestParam String nickname) {
        Map<String, Object> map = new HashMap<>();
        try {      
            if(nickname == null || nickname.trim().isEmpty()) {
                map.put("result", 0);
                map.put("message", "닉네임은 비워둘 수 없습니다.");
                return map;
            }
            int result = adminService.updateNickname(appUserId, nickname);
            if(result > 0) {
                map.put("result", 1);
                map.put("message", "닉네임이 성공적으로 수정되었습니다.");
            } else {
                map.put("result", 0);
                map.put("message", "닉네임 수정 실패: 해당 유저를 찾을 수 없습니다.");
            }
        } catch(Exception e) {
            map.put("result", -1);
            map.put("message", "서버 오류 발생: " + e.getMessage());
        }
        return map;
    }
    
   
    
    
}
