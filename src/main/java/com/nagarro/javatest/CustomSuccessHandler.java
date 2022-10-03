package com.nagarro.javatest;


import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("Inside CustomSuccessHandler");
		String redirectUrl = null;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			log.info("role " + grantedAuthority.getAuthority());
			if (grantedAuthority.getAuthority().equals("USER")||grantedAuthority.getAuthority().equals("ROLE_USER")) {
				redirectUrl = "/user-statement";
				break;
			} else if (grantedAuthority.getAuthority().equals("ADMIN")||grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				redirectUrl = "/statement";
				break;
			}
		}
		System.out.println("redirectUrl " + redirectUrl);
		if (redirectUrl == null) {
			throw new IllegalStateException();
		}
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		log.info("Leaving CustomSuccessHandler");

	}
}



