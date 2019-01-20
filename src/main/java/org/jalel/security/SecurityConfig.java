package org.jalel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService ;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*BCryptPasswordEncoder encoder = passwordEncoder();
		
		auth.inMemoryAuthentication()
		    .withUser("jalel").password(encoder.encode("1234")).roles("ADMIN","USER")
		    .and()
		    .withUser("user").password(encoder.encode("1234")).roles("USER");*/
		
	/*3em technique d'autentification celui que j l'utiliser **personaliser le travail ds la couche metier**/
		
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(bCryptPasswordEncoder);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
		//desactive le jeton de synchronisation (csrf)
		http.csrf().disable()
		
		//desactive la session 
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) ;
		
		http.formLogin();
		http.authorizeRequests().antMatchers("/register/**","/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/tasks/**").hasAuthority("ADMIN");
		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JWTAutenticationFilter(authenticationManager()));
		http.addFilterBefore(new JWTAutorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

}


