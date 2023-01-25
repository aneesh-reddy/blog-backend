package com.aneesh.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aneesh.blog.entities.User;
import com.aneesh.blog.exceptions.ResourceNotFoundException;
import com.aneesh.blog.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user details from database
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email",username));
		return user;
	}

}
