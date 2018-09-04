package de.flashheart.rlgserver.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Optional;

@Service
public class MatchService extends CrudService<Match> {


    private static final long IDLE_MINUTES_UNTIL_CLEANUP = 10;
    private final MatchRepository matchRepository;
    private final ObjectMapper mapper;

    public static final int QUALITY_RUNNING = 0;
    public static final int QUALITY_FINISHED = 1;
    public static final int QUALITY_FIXED = 2;

    public static final String RESULT_STILL_RUNNING = "result_still_running";
    public static final String RESULT_ATTACKERS_WON = "result_attackers_won";
    public static final String RESULT_DEFENDERS_WON = "result_defenders_won";
    public static final String RESULT_GAME_ABORTED = "result_abort";
    
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
        match.setState(gameState.getState());
        match.setResult(getResult(gameState)); //todo: da fehlt noch was
        match.setPausingsince(gameState.getTimestamp_game_paused() > -1 ? Tools.toLocalDateTime(gameState.getTimestamp_game_paused(), gameState.getZoneid()) : null);
        match.setEndofgame(gameState.getTimestamp_game_ended() > -1 ? Tools.toLocalDateTime(gameState.getTimestamp_game_ended(), gameState.getZoneid()) : null);

        match.setColor(gameState.getColor());

        if (!matches.isEmpty()) match.setQuality(match.getEndofgame() != null ? QUALITY_FINISHED : QUALITY_RUNNING);

        save(match);
    }


    @Transactional
    /**
     * changes game records to "finished but broken", when the remote device on the field lost contact and could to finish the entry itself.
     */
    public void fixBrokenMatches() {
        final List<Match> listRunningMatches = matchRepository.findByEndofgame(null);
        listRunningMatches.forEach(match -> {
            if (match.getPit().plusMinutes(IDLE_MINUTES_UNTIL_CLEANUP).isAfter(LocalDateTime.now())) {
                LocalDateTime endOfGame = match.getStartofgame().plus(match.getMaxgametime(), ChronoUnit.MILLIS);
                match.setEndofgame(endOfGame);
                match.setQuality(QUALITY_FIXED);
                matchRepository.save(match);
            }
        });
    }

    /**
     * produces some result string according to the gamestate and the gametype
     *
     * @param gameState
     * @return
     */
    private static String getResult(GameState gameState) {
        return "noresultsyet";
    }
}
