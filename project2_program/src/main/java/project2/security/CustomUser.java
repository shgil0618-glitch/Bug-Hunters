package project2.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import project2.dto.AppUserAuthDto;

import lombok.Getter;

@Getter
public class CustomUser extends User{
	
	project2.dto.AppUserAuthDto dto;
	
	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		
	}

	public CustomUser(project2.dto.AppUserAuthDto dto) {
		super(dto.getEmail(),
			  dto.getPassword(),
			  dto.getAuthList().stream()
								 .map(auth-> new SimpleGrantedAuthority(auth.getAuth())) 
								 .collect(Collectors.toList())
		);
		this.dto = dto;
	}
}
