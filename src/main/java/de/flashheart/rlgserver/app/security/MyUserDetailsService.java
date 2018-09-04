package de.flashheart.rlgserver.app.security;

import de.flashheart.rlgserver.backend.data.entity.DBUser;
import de.flashheart.rlgserver.backend.data.repositories.DBUserRepository;
import de.flashheart.rlgserver.backend.service.DBUserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final DBUserService dbUserService;
    private final DBUserRepository dbUserRepository;

    public MyUserDetailsService(DBUserService dbUserService, DBUserRepository dbUserRepository) {
        this.dbUserService = dbUserService;
        this.dbUserRepository = dbUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<DBUser> dbUser = dbUserRepository.findByUsername(username);

        if (!dbUser.isPresent()) throw new UsernameNotFoundException("No user present with username: " + username);


        return new User(username, dbUser.get().getPassword(), Collections.singletonList(new SimpleGrantedAuthority(dbUser.get().getRole())));


    }
}