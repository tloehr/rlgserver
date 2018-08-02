package de.flashheart.rlgserver.backend.data.pojo;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameState {

    private String bombname;
    private String gametype;
    private String uuid;
    private long matchid;
    private DateTime timestamp;
    private DateTime timestamp_game_started;
    private DateTime timestamp_game_paused;
    private DateTime timestamp_game_ended;
    private boolean bombfused;
    private long remaining;
    private long capturetime;
    private long maxgametime;
    private long gametime;
    private List<GameEvent> gameEvents;

    public GameState() {
        gameEvents = new ArrayList<>();
    }

    public String getBombname() {
        return bombname;
    }

    public void setBombname(String bombname) {
        this.bombname = bombname;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getMatchid() {
        return matchid;
    }

    public void setMatchid(long matchid) {
        this.matchid = matchid;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    public DateTime getTimestamp_game_started() {
        return timestamp_game_started;
    }

    public void setTimestamp_game_started(DateTime timestamp_game_started) {
        this.timestamp_game_started = timestamp_game_started;
    }

    public DateTime getTimestamp_game_paused() {
        return timestamp_game_paused;
    }

    public void setTimestamp_game_paused(DateTime timestamp_game_paused) {
        this.timestamp_game_paused = timestamp_game_paused;
    }

    public DateTime getTimestamp_game_ended() {
        return timestamp_game_ended;
    }

    public void setTimestamp_game_ended(DateTime timestamp_game_ended) {
        this.timestamp_game_ended = timestamp_game_ended;
    }

    public boolean isBombfused() {
        return bombfused;
    }

    public void setBombfused(boolean bombfused) {
        this.bombfused = bombfused;
    }

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public long getCapturetime() {
        return capturetime;
    }

    public void setCapturetime(long capturetime) {
        this.capturetime = capturetime;
    }

    public long getMaxgametime() {
        return maxgametime;
    }

    public void setMaxgametime(long maxgametime) {
        this.maxgametime = maxgametime;
    }

    public long getGametime() {
        return gametime;
    }

    public void setGametime(long gametime) {
        this.gametime = gametime;
    }

    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }

    public void setGameEvents(List<GameEvent> gameEvents) {
        this.gameEvents = gameEvents;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "bombname='" + bombname + '\'' +
                ", gametype='" + gametype + '\'' +
                ", uuid='" + uuid + '\'' +
                ", matchid=" + matchid +
                ", timestamp=" + timestamp +
                ", timestamp_game_started=" + timestamp_game_started +
                ", timestamp_game_paused=" + timestamp_game_paused +
                ", timestamp_game_ended=" + timestamp_game_ended +
                ", bombfused=" + bombfused +
                ", remaining=" + remaining +
                ", capturetime=" + capturetime +
                ", maxgametime=" + maxgametime +
                ", gametime=" + gametime +
                ", gameEvents=" + gameEvents +
                '}';
    }
}
