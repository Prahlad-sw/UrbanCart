package com.DiffyStore.MyDiffyStore.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.DiffyStore.MyDiffyStore.config.UserInfoUserDetails;
import com.DiffyStore.MyDiffyStore.config.UserInfoUserDetailsService;
import com.DiffyStore.MyDiffyStore.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserInfoUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException {
		String authHeader=request.getHeader("Authorization");
		if(authHeader==null) {
			filterChain.doFilter(request, response);;
			return;
		}
		if(!authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token=authHeader.substring(7);
		String username=jwtService.extractUsername(token);
		
		System.out.println("User : "+username);
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserInfoUserDetails userDB=(UserInfoUserDetails) userDetailsService.loadUserByUsername(username);
			
			if(userDB!=null && jwtService.validateToken(token,userDB)) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username,null, userDB.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		return;
	}
}
