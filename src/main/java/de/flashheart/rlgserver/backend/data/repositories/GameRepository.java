package de.flashheart.rlgserver.backend.data.repositories;


import de.flashheart.rlgserver.backend.data.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;
import java.util.List;


public interface GameRepository extends JpaRepository<Game, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    List<Game> findByUuidAndMatchid(@NotNull String uuid, @NotNull long matchid);
}
