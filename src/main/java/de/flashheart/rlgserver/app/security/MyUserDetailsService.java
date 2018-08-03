package de.flashheart.rlgserver.app.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

//	private final UserService userService;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (!username.equalsIgnoreCase("Torsten")) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			return new User("Torsten",new BCryptPasswordEncoder().encode("test1234"),Collections.singletonList(new SimpleGrantedAuthority("USER")));

		}
	}
}