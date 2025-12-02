package project2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import project2.dto.MaterialDto;
import project2.dto.UserDto;
import project2.service.MaterialService;

@Controller
public class MaterialController {
	@Autowired MaterialService service;
	
	@RequestMapping("/materiallist")
	public String materialList(Model model) {
		List<MaterialDto> list = service.MaterialList();
		model.addAttribute("list",list);
		return "member/materiallist";
	}
	
	@RequestMapping("/materialdetail")
		public String materialDetail(@RequestParam("materialid")int materialid, Model model) {
			MaterialDto dto = new MaterialDto();
			dto.setMaterialid(materialid);
			model.addAttribute("dto",service.selectMaterial(dto));
			return "member/materialdetail";
}

	@RequestMapping(value="/materialinsert",method=RequestMethod.GET)
	public String insertPage(HttpSession session) {
		UserDto loginUser = (UserDto)session.getAttribute("loginUser");
		if(loginUser==null || !loginUser.getEmail().equals("ADMIN")) {
			return "redirect:/login.user";
		}
		return "member/materialinsert";
	}
	
	@RequestMapping(value="/materialedit", method=RequestMethod.GET)
    public String editPage(@RequestParam("materialid") int materialid,
                           HttpSession session, Model model) {
		String msg = "글수정 실패";
        UserDto loginUser = (UserDto)session.getAttribute("loginUser");

        if(loginUser == null || !loginUser.getEmail().equals("ADMIN")) {
            return "redirect:/login.user";
        }

        MaterialDto dto = new MaterialDto();
        dto.setMaterialid(materialid);
        model.addAttribute("dto", service.selectMaterial(dto));

        return "member/materialedit";
    }
	
	@RequestMapping(value="/materialedit", method=RequestMethod.POST)
    public String editMaterial(MaterialDto dto, HttpSession session) {

        UserDto loginUser = (UserDto)session.getAttribute("loginUser");

        if(loginUser == null || !loginUser.getEmail().equals("ADMIN")) {
            return "redirect:/login.user";
        }

        service.updateMaterial(dto);
        return "redirect:/materialdetail?materialid=" + dto.getMaterialid();
    }
	
	 @RequestMapping(value="/materialdelete", method=RequestMethod.GET)
	    public String deleteMaterial(@RequestParam("materialid") int materialid, HttpSession session) {

	        UserDto loginUser = (UserDto)session.getAttribute("loginUser");

	        if(loginUser == null || !loginUser.getEmail().equals("ADMIN")) {
	            return "redirect:/login.user";
	        }

	        MaterialDto dto = new MaterialDto();
	        dto.setMaterialid(materialid);

	        service.deleteMaterial(dto);

	        return "redirect:/materiallist";
	    }
}