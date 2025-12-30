package com.thejoa703.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thejoa703.external.ApiEmailNaver;

@Controller
@RequestMapping("/api")
public class ApiController {

   ///////////////////////// Email
   @Autowired  ApiEmailNaver   apiEmailNaver;
   
   @GetMapping("/mail")
   public String mail_get() { return "external/mail"; }
   
   @PostMapping(value="/mail" ) 
   public String mail(String subject, String content, String email){ 
      apiEmailNaver.sendMail(subject , content , email);
      return "external/mail_result";
   } 
   ///////////////////////// Chatbot
   @GetMapping("/chatbot")
   public String chatbot() { return "external/chatbot"; }

  
}









