package de.flashheart.rlgserver.backend.service;

import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.backend.data.entity.IncomingMessage;
import de.flashheart.rlgserver.backend.data.repositories.IncomingMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class IncomingMessageService extends CrudService<IncomingMessage> implements HasLogger {
    private final IncomingMessageRepository incomingMessageRepository;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

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

    public List<IncomingMessage> check_value_out_of_range(String key1, BigDecimal from, BigDecimal to, List<String> services) {
        List<IncomingMessage> incomingMessageList = incomingMessageRepository.findByKey1AndServiceInAndPitGreaterThanEqual(key1, services, LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        incomingMessageList.forEach(incomingMessage -> {
            getLogger().debug(incomingMessage.toString());
            BigDecimal value = null;
            try {
                value = new BigDecimal(incomingMessage.getValue());
            } catch (NumberFormatException nfe){
                value = null;
            }

            if (from.compareTo(value) >= 0 || value.compareTo(to) >= 0){
                getLogger().debug("out of Range");
            }

        });
        return incomingMessageList;
//        return incomingMessageRepository.findByKey1AndPitGreaterThanEqual(key1, LocalDateTime.now().minus(1, ChronoUnit.DAYS));
    }

    public Optional<IncomingMessage> create(String host, String service, String pit, String subject, String reference, String key1, String key2, String value) {
        IncomingMessage incomingMessage = new IncomingMessage();
        incomingMessage.setHost(host);

        LocalDateTime ldt;
        try {
            // Parsed auch ein Bash 'date -Is'
            ldt = LocalDateTime.from(FORMATTER.parse(pit));
        } catch (Exception dtpe) {
            getLogger().warn(dtpe.toString());
            ldt = LocalDateTime.now();
        }
        incomingMessage.setPit(ldt);
        incomingMessage.setService(service);
        incomingMessage.setSubject(subject);
        incomingMessage.setReference(reference);
        incomingMessage.setKey1(key1);
        incomingMessage.setKey2(key2);
        incomingMessage.setValue(value);
        return Optional.ofNullable(save(incomingMessage));
    }


}
