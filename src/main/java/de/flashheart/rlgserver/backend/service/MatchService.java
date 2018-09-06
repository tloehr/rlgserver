package de.flashheart.rlgserver.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.app.misc.Tools;
import de.flashheart.rlgserver.backend.data.entity.Match;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.data.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService extends CrudService<Match> implements HasLogger {

    public static final String RESULT_GAME_RUNNING = "still_running";
    public static final String RESULT_GAME_OVER = "game_over";
    public static final String RESULT_GAME_ABORTED = "abort";
    public static final String RESULT_GAME_FIXED = "fixed";

    private static final long IDLE_MINUTES_UNTIL_CLEANUP = 10;
    private final MatchRepository matchRepository;
    private final ObjectMapper mapper;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        super();
        this.matchRepository = matchRepository;
        mapper = new ObjectMapper();
    }

    @Override
    protected CrudRepository<Match, Long> getRepository() {
        return matchRepository;
    }

    @Override
    public long countAnyMatching(Optional<String> filter) {
        return matchRepository.count();
    }

    @Override
    public Page<Match> findAnyMatching(Optional<String> filter, Pageable pageable) {
        return matchRepository.findAll(pageable);
    }

    public Optional<Match> findById(long id) {
        return matchRepository.findById(id);
    }


    public List<Match> findGamesBetween(LocalDateTime from, LocalDateTime to) {
        return matchRepository.findByStartofgameBetween(from, to);
    }

    public List<Match> findRunningMatches() {
        return matchRepository.findByMatchrecordEquals(RESULT_GAME_RUNNING);
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
        List<Match> matches = matchRepository.findByUuidAndMatchid(gameState.getUuid(), gameState.getMatchid());

        String json = mapper.writeValueAsString(gameState);
        long maxgametime = gameState.getMaxgametime();
        if (gameState.getGametype().equals(GameState.TYPE_FARCRY)) {
            maxgametime = gameState.getMaxgametime() + gameState.getCapturetime();
        }

        Match match = matches.isEmpty() ? new Match(gameState.getUuid(), gameState.getMatchid(), gameState.getZoneid(), gameState.getBombname(), gameState.getGametype(), Tools.toLocalDateTime(gameState.getTimestamp_game_started(), gameState.getZoneid()), maxgametime) : matches.get(0);

        match.setPit(Tools.toLocalDateTime(gameState.getTimestamp(), gameState.getZoneid()));
        match.setJson(json);
        match.setRemaining(gameState.getRemaining());

        match.setPausingsince(gameState.getTimestamp_game_paused() > -1 ? Tools.toLocalDateTime(gameState.getTimestamp_game_paused(), gameState.getZoneid()) : null);
        match.setEndofgame(gameState.getTimestamp_game_ended() > -1 ? Tools.toLocalDateTime(gameState.getTimestamp_game_ended(), gameState.getZoneid()) : null);

        match.setColor(gameState.getColor());

        match.setMatchrecord(RESULT_GAME_RUNNING);
        match.setResult(RESULT_GAME_RUNNING);
        match.setState(gameState.getState());
        if (match.getEndofgame() != null) {
            match.setMatchrecord(RESULT_GAME_OVER);
            if (gameState.getState().equals(GameState.EVENT_GAME_ABORTED)) match.setMatchrecord(RESULT_GAME_ABORTED);
            if (Arrays.asList(GameState.GAME_OVER_STATES).contains(gameState.getState())) {
                match.setResult(gameState.getState());
            }
        }

        save(match);
    }


    /**
     * changes game records to "finished but broken", when the remote device on the field lost contact and could to finish the entry itself.
     */
    @Transactional
    public void fixBrokenMatches() {
        final List<Match> listRunningMatches = matchRepository.findByEndofgame(null);
        if (listRunningMatches.isEmpty()) getLogger().info("nothing to fix");

        listRunningMatches.forEach(match -> {
            if (match.getPit().plusMinutes(IDLE_MINUTES_UNTIL_CLEANUP).isAfter(LocalDateTime.now())) {
                LocalDateTime endOfGame = match.getStartofgame().plus(match.getMaxgametime(), ChronoUnit.MILLIS);
                match.setEndofgame(endOfGame);
                match.setMatchrecord(RESULT_GAME_FIXED);
                getLogger().info("fixing uuid/match: " + match.getUuid() + "/" + match.getMatchid());
                matchRepository.save(match);
            }
        });
    }


}
