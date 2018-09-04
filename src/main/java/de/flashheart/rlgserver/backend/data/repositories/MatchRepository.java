package de.flashheart.rlgserver.backend.data.repositories;


import de.flashheart.rlgserver.backend.data.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


public interface MatchRepository extends JpaRepository<Match, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    List<Match> findByUuidAndMatchid(@NotNull String uuid, @NotNull long matchid);

    List<Match> findByStartofgameBetween(@NotNull LocalDateTime from, @NotNull LocalDateTime to);

    @Lock(LockModeType.OPTIMISTIC)
    List<Match> findByEndofgame(LocalDateTime localDateTime);

}
