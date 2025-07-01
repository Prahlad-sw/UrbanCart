package com.DiffyStore.MyDiffyStore.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.DiffyStore.MyDiffyStore.models.UserInfo;

public class UserInfoUserDetails implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2622912051636872696L;
	private UserInfo userInfo;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public UserInfoUserDetails(UserInfo userInfo) {
		this.name=userInfo.getUsername();
		this.password=userInfo.getPassword();
		this.authorities=List.of(new SimpleGrantedAuthority(userInfo.getRoles()));
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}
}
