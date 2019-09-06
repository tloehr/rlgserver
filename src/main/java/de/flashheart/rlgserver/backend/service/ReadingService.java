package de.flashheart.rlgserver.backend.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.backend.data.entity.Reading;
import de.flashheart.rlgserver.backend.data.repositories.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadingService extends CrudService<Reading> implements HasLogger {
    private final ReadingRepository readingRepository;
    private final ObjectMapper mapper;


    @Autowired
    public ReadingService(ReadingRepository readingRepository) {
        this.readingRepository = readingRepository;
        this.mapper = new ObjectMapper();
    }

    @Override
    protected CrudRepository<Reading, Long> getRepository() {
        return getRepository();
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        return readingRepository.count();
    }

    @Override
    public Page<Reading> findAnyMatching(Optional<String> filter, Pageable pageable) {
        return readingRepository.findAll(pageable);
    }
}

