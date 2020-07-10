package de.flashheart.rlgserver.backend.data.repositories;

import de.flashheart.rlgserver.backend.data.entity.IncomingMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomingMessageRepository extends JpaRepository<IncomingMessage, Long> {

}
