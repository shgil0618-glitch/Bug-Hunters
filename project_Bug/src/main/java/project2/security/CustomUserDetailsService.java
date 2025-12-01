package project2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import project2.dto.AppUserAuthDto;
import project2.service.AppUserSecurityService;



public class CustomUserDetailsService implements UserDetailsService  {
   @Autowired AppUserSecurityService service;
   
   @Override                     // 아이디(email)
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // email,password,권한
	  System.out.println("...................." + username);
      AppUserAuthDto dto = service.readAuth(username);   // 해당유저정보를 가져오기
      
      if(dto == null) {
    	  System.out.println( "................... error"  );
    	  throw new UsernameNotFoundException("User not found: " + username);
      }
      
      System.out.println("...................." + dto);
      return  new CustomUser(dto) ;
   }

}
