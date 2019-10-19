package de.flashheart.rlgserver.backend.data.repositories;

import de.flashheart.rlgserver.backend.data.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    Optional<Reading> findTopByUuidOrderByPitDesc(String uuid);
}

