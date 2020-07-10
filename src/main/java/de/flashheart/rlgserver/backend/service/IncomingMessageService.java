package de.flashheart.rlgserver.backend.service;

import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.backend.data.entity.IncomingMessage;
import de.flashheart.rlgserver.backend.data.entity.Reading;
import de.flashheart.rlgserver.backend.data.repositories.IncomingMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IncomingMessageService extends CrudService<IncomingMessage> implements HasLogger {
    private final IncomingMessageRepository incomingMessageRepository;

    @Autowired
    public IncomingMessageService(IncomingMessageRepository incomingMessageRepository) {
        this.incomingMessageRepository = incomingMessageRepository;
    }


    @Override
    protected CrudRepository<IncomingMessage, Long> getRepository() {
        return incomingMessageRepository;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        return incomingMessageRepository.count();
    }

    @Override
    public Page<IncomingMessage> findAnyMatching(Optional<String> filter, Pageable pageable) {
        return incomingMessageRepository.findAll(pageable);
    }

    public Optional<IncomingMessage> create(String host, String service, String subject, String reference, String type_of_value, Optional<String> additionalid, String the_value) {

        IncomingMessage incomingMessage = new IncomingMessage();
        incomingMessage.setHost(host);
        incomingMessage.setPit(LocalDateTime.now());
        incomingMessage.setService(service);
        incomingMessage.setReference();
        incomingMessage.setKey_part1(null);
        incomingMessage.setKey_part2(null);
        incomingMessage.setSubject(null);
        incomingMessage.setValue();

        return Optional.ofNullable(save(incomingMessage));
    }


}
