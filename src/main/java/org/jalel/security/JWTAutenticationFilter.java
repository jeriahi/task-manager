package org.jalel.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jalel.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAutenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	
	public JWTAutenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		AppUser appuser= null;
		try {
			appuser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		} catch (Exception e) {

			throw new RuntimeException(e);
		} 
		
		System.out.println("*************************");
		System.out.println("username :"+appuser.getUsername());
		System.out.println("mot de pass :"+appuser.getPassword());
		System.out.println("*************************");
		
		return authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(
						appuser.getUsername(),
						appuser.getPassword())
				);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		User springUser=(User) authResult.getPrincipal() ;
		
		String jwt=Jwts.builder()
				.setSubject(springUser.getUsername())
				.setExpiration(new 
						Date(System.currentTimeMillis()+SecurityConstant.EXPIRETION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET)
				.claim("roles", springUser.getAuthorities())
				.compact();
		
		response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX+jwt);
		
		//super.successfulAuthentication(request, response, chain, authResult);
	}
	
}




