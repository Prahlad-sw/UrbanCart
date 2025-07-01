package com.DiffyStore.MyDiffyStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DiffyStore.MyDiffyStore.config.UserInfoUserDetailsService;
import com.DiffyStore.MyDiffyStore.dto.AuthRequest;
import com.DiffyStore.MyDiffyStore.dto.JwtResponse;
import com.DiffyStore.MyDiffyStore.service.JwtService;

@RestController 
@RequestMapping("/api/public")
public class LoginController {
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired 
	private UserInfoUserDetailsService userDetailsService;
	@Autowired 
	private JwtService jwtService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticatateAndGetToken(@RequestBody AuthRequest authRequest){
		try {
			
			UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
			Authentication authentication=authenticationProvider.authenticate(authToken);//manager
			
			if(authentication.isAuthenticated()) {
				String token=jwtService.generateToken(authRequest.getUsername());
				//JwtResponse jwtResponse=new JwtResponse(token,200);
				
				return ResponseEntity.ok(token);
			}
			else {
				return ResponseEntity.status(401).build();
			}
		}
		catch(Exception e) {
			System.out.println("SanityCheck exception"+e.getMessage());
			return ResponseEntity.status(401).build();
		}
	}
}
