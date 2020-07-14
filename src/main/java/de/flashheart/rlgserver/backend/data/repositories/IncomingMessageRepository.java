package de.flashheart.rlgserver.backend.data.repositories;

import de.flashheart.rlgserver.backend.data.entity.IncomingMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomingMessageRepository extends JpaRepository<IncomingMessage, Long> {
    List<IncomingMessage> findByKey1AndPitGreaterThanEqual(String key1, LocalDateTime pit);

    List<IncomingMessage> findByKey1AndServiceInAndPitGreaterThanEqual(String key1, List<String> services, LocalDateTime pit);

}
