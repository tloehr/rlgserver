package de.flashheart.rlgserver.backend.service;

import de.flashheart.rlgserver.backend.data.entity.Game;
import de.flashheart.rlgserver.backend.data.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService extends CrudService<Game> {


    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        super();
        this.gameRepository = gameRepository;
    }

    @Override
    protected CrudRepository<Game, Long> getRepository() {
        return gameRepository;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        return gameRepository.count();
    }

    @Override
    public Page<Game> findAnyMatching(Optional<String> filter, Pageable pageable) {
        return gameRepository.findAll(pageable);
    }
}
