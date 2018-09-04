package de.flashheart.rlgserver.backend.data.repositories;

import de.flashheart.rlgserver.backend.data.entity.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface DBUserRepository  extends JpaRepository<DBUser, Long> {
    Optional<DBUser> findByUsername(@NotNull String username);
}
