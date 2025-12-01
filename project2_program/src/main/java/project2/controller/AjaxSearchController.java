package project2.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project2.service.AppUserSecurityService;
import project2.service.UserService;

@RestController
public class AjaxSearchController {
	@Autowired UserService service;
	
	@RequestMapping("/iddouble")
	public Map<String,Object> iddouble(@RequestParam("search") String email){
	    Map<String,Object> result = new HashMap<>();
	    result.put("cnt", service.iddouble(email));
	    return result;
	}


}
