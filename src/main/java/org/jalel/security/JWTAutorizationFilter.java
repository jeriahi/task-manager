package org.jalel.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAutorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		/*response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", 
				" Origin, Accept, X-Requested-With, Content-Type, "
				+ "Access-Control-Request-Method, "
				+ "Access-Control-Request-Headers,"
				+ "Authorization ");
		
		response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin ,"
				+"Access-Control-Allow-Credentiels, Authorization");*/
		
		String jwt = request.getHeader(SecurityConstant.HEADER_STRING);
		System.out.println("*************************");
		System.out.println(jwt);
		System.out.println("*************************");
		
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
		else {
			if(jwt==null||!jwt.startsWith(SecurityConstant.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			Claims claims=Jwts.parser()
					.setSigningKey(SecurityConstant.SECRET)
					.parseClaimsJws(jwt.replace(SecurityConstant.TOKEN_PREFIX,""))
					.getBody();////////////////////////
			
			System.out.println("*************************");
			System.out.println(claims);
			System.out.println("*************************");
			
			String username=claims.getSubject();
			ArrayList<Map<String, String>> roles=(ArrayList<Map<String, String>>) claims.get("roles");
			
			Collection<GrantedAuthority> authorities=new ArrayList<>();
			roles.forEach(r->{
				authorities.add(new SimpleGrantedAuthority(r.get("authority")));
			});
			//////////////////////////
			
			System.out.println("*********authorityfinal****************");
			System.out.println(authorities);
			System.out.println("*************************");
			
			
			
			UsernamePasswordAuthenticationToken authenticationToken= 
					new UsernamePasswordAuthenticationToken(username, null, authorities); 
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			filterChain.doFilter(request,response);
		}
		
	}
	
}
