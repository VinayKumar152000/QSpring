package com.example.demo.services;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Role;
import com.example.demo.repository.UserRepository;


@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	UserRepository repo;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		com.example.demo.domain.User user= repo.findByName(username);
		System.out.println(user);
		
		if(user!=null) {
			return new User(user.getName(), user.getPassword(),getAuthority(user));
		}
		else {
			throw new UsernameNotFoundException(username);
		}
	}
	
	
	private Set<SimpleGrantedAuthority> getAuthority(com.example.demo.domain.User  user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

}
