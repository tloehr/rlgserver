package de.flashheart.rlgserver.backend.data.repositories;


import de.flashheart.rlgserver.backend.data.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRepository extends JpaRepository<Game, Long> {
}
