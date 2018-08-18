package de.flashheart.rlgserver.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.backend.data.entity.Game;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.data.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class GameService extends CrudService<Game> {


    private final GameRepository gameRepository;
    private final ObjectMapper mapper;

    @Autowired
    public GameService(GameRepository gameRepository) {
        super();
        this.gameRepository = gameRepository;
        mapper = new ObjectMapper();
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


       public List<Game> findGamesBetween(LocalDateTime from, LocalDateTime to) {
           return gameRepository.findByStartofgameBetween(from, to);
       }

    /**
     * Aktualisiert ein Game Objekt in der Datenbank. Dabei wird anhand der UUID und der matchid
     * die Identität des Objektes festgestellt. Also eine bestimmte Box in einem bestimmten Match hat immer
     * dasselbe Game Objekt.
     * Wenn es das noch nicht gibt, wird ein neues erstellt. Ansonsten werden die Attribute des Objektes anhand
     * des neuesten GameStates aktualisiert.
     *
     * @param gameState das jeweils neueste Update der Box auf dem Feld.
     * @throws JsonProcessingException
     */
    @Transactional
    public void update(GameState gameState) throws JsonProcessingException {
        List<Game> games = gameRepository.findByUuidAndMatchid(gameState.getUuid(), gameState.getMatchid());
        String json = mapper.writeValueAsString(gameState);
        if (games.isEmpty()) {
            Game game = new Game(gameState);
            game.setJson(json);
            save(game);
        } else {
            Game myGame = games.get(0);
            myGame.setJson(json);
            myGame.setRemaining(gameState.getRemaining());
            myGame.setState(gameState.getState());
            myGame.setEndofgame(gameState.getTimestamp_game_ended() > -1 ? LocalDateTime.ofInstant(Instant.ofEpochMilli(gameState.getTimestamp_game_ended()), TimeZone.getDefault().toZoneId()) : null);
            myGame.setPausingsince(gameState.getTimestamp_game_paused() > -1 ? LocalDateTime.ofInstant(Instant.ofEpochMilli(gameState.getTimestamp_game_paused()), TimeZone.getDefault().toZoneId()) : null);
            myGame.setResult(getResult(gameState));
            save(myGame);
        }
    }

    /**
     * produces some result string according to the gamestate and the gametype
     * @param gameState
     * @return
     */
    private static String getResult(GameState gameState){
        return "noresultsyet";
    }
}
