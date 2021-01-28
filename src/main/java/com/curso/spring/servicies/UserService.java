package com.curso.spring.servicies;

import org.springframework.security.core.context.SecurityContextHolder;

import com.curso.spring.security.UserSS;

public class UserService {
	
	// metodo para trazer o usuario logado do sistema
	public static UserSS authenticated() {
		try {
			return(UserSS) SecurityContextHolder.
					getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
}
