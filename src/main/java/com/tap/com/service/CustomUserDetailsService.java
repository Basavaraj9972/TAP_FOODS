package com.tap.com.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tap.com.model.UserEntity;
import com.tap.com.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	UserRepository userRepository;
	public CustomUserDetailsService(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Dummy User for testing. Replace this with DB lookup.
    	Optional<UserEntity> user1;
    	user1 = userRepository.findUserByUserName(username);
        if (user1.isPresent()) {
            return new User(user1.get().getUsername(), user1.get().getPassword(), Collections.emptyList());
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<UserEntity> user = userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//	    return new org.springframework.security.core.userdetails.User(
//	        user.getUsername(),
//	        user.getPassword(),
//	        List.of(new SimpleGrantedAuthority("ROLE_USER"))
//	    );
//	}


}
