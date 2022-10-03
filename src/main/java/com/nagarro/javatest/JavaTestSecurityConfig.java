package com.nagarro.javatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@Slf4j
public class JavaTestSecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private AuthenticationSuccessHandler successHandler;
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			// TODO Auto-generated method stub
			log.info("Inside JavaTestSecurityConfig.configure");
			auth.inMemoryAuthentication()
				.withUser("user")
				.password("user")
				.roles("USER")
				.and()
				.withUser("admin")
				.password("admin")
				.roles("ADMIN");
				
		}
		
		@Bean
		public PasswordEncoder getPasswordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// TODO Auto-generated method stub
			http.csrf().disable().authorizeRequests()
			.antMatchers("/statement-user").hasAnyRole("USER")
			.antMatchers("/statement").hasRole("ADMIN")
			.antMatchers("/api/*").hasAnyRole("ADMIN")
				.and().formLogin().loginPage("/login")
				.successHandler(successHandler)
				.permitAll()
				.and()
				 .logout()
                 .invalidateHttpSession(true)
                 .clearAuthentication(true)
                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                 .logoutSuccessUrl("/login?logout")
                     .permitAll()
                     .and()
                     .rememberMe()
                         .key("unique-and-secret")
                         .rememberMeCookieName("remember-me-cookie-name")
                         .tokenValiditySeconds(86400)
                         .and()
          	.sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
              	.and()
          	.sessionManagement()
          	.maximumSessions(1)
          	.and()
          	.invalidSessionUrl("/?sessionexpired=true");
				;
			
			

		}
		
		
		
		
		
		
		
		
		
		
		
		
}
