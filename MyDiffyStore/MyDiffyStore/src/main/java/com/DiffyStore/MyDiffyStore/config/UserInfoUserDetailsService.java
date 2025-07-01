package com.DiffyStore.MyDiffyStore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.DiffyStore.MyDiffyStore.models.UserInfo;
import com.DiffyStore.MyDiffyStore.repository.UserInfoRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService{
		@Autowired
		private UserInfoRepository repository;
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			UserInfo user=repository.findByUsername(username).orElse(null);
			if(user!=null) {
				return  new UserInfoUserDetails(user);
			}
			else {
				throw new UsernameNotFoundException("User Not Found");
			}
		}
}	
