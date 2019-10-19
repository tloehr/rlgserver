package de.flashheart.rlgserver.backend.service;

import de.flashheart.rlgserver.backend.data.entity.DBUser;
import de.flashheart.rlgserver.backend.data.repositories.DBUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DBUserService extends CrudService<DBUser> {
    private final DBUserRepository dbUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DBUserService(DBUserRepository dbUserRepository, PasswordEncoder passwordEncoder) {
        this.dbUserRepository = dbUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected CrudRepository<DBUser, Long> getRepository() {
        return dbUserRepository;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        return dbUserRepository.count();
    }

    @Override
    public Page<DBUser> findAnyMatching(Optional<String> filter, Pageable pageable) {
        return dbUserRepository.findAll(pageable);
    }

    public DBUser createUserIfNecessary(String email, String name, String password, String role) {
        Optional<DBUser> myuser = dbUserRepository.findByUsername(name);

        if (myuser.isPresent()) return myuser.get();
        else return save(new DBUser(email, name, passwordEncoder.encode(password), role));
//        return dbUserRepository.findByUsername(name).orElse(save(new DBUser(email, name, passwordEncoder.encode(password), role)));
    }

}
