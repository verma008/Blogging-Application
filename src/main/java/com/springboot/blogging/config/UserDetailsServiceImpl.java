package com.springboot.blogging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blogging.entities.User;
import com.springboot.blogging.repository1.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// get userdetails by username(emailid) from database
		User dbuser = this.userRepository.findByEmail(username);
		if(dbuser==null)
		{
			throw new UsernameNotFoundException("User doesn't exist in database");
		}
		
		CustomUserDetails customUserDetails=new CustomUserDetails(dbuser);
		return customUserDetails;
	}

}
