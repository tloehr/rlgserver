package de.flashheart.rlgserver.backend.data.entity;

import de.flashheart.rlgserver.app.misc.Tools;
import de.flashheart.rlgserver.backend.data.pojo.GameState;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
public class Game extends AbstractEntity {

    @NotNull
    LocalDateTime pit; // pit of the last entry
    @NotNull
    String bombname;
    @NotNull
    String uuid; // of the machine
    @NotNull
    String gametype;
    @NotNull
    String state; // the current state of the box.
    @NotNull
    LocalDateTime startofgame;
    LocalDateTime pausingsince;
    LocalDateTime endofgame;
    long remaining;
    @NotNull
    String result; // short summary of the result
    @NotNull
    String color; // the bgcolor to show for the match
    @NotNull
    long matchid; // of the match
    @NotNull
    private String zoneid; // for timezone handling
    @NotNull
    @Lob
    String json;


    public Game() {
        pit = LocalDateTime.now();
        color = Tools.getHTMLColor(Color.WHITE);
        result = "noresultsyet";
        state = "null";
        this.zoneid = ZoneId.systemDefault().getId();
    }

    public Game(GameState gameState) {
        this();
        bombname = gameState.getBombname();
        remaining = gameState.getRemaining();
        matchid = gameState.getMatchid();
        uuid = gameState.getUuid();
        gametype = gameState.getGametype();
        state = gameState.getState();
        zoneid = gameState.getZoneid();
        pit = Tools.toLocalDateTime(gameState.getTimestamp(), zoneid);
        startofgame = Tools.toLocalDateTime(gameState.getTimestamp_game_started(), zoneid);
        pausingsince = gameState.getTimestamp_game_paused() > -1 ? Tools.toLocalDateTime(gameState.getTimestamp_game_paused(), zoneid) : null;
        endofgame = gameState.getTimestamp_game_ended() > -1 ? Tools.toLocalDateTime(gameState.getTimestamp_game_ended(), zoneid) : null;
    }

    public String getBombname() {
        return bombname;
    }

    public void setBombname(String bombname) {
        this.bombname = bombname;
    }

    public LocalDateTime getPit() {
        return pit;
    }

    public void setPit(LocalDateTime pit) {
        this.pit = pit;
    }

    public LocalDateTime getStartofgame() {
        return startofgame;
    }

    public void setStartofgame(LocalDateTime startofgame) {
        this.startofgame = startofgame;
    }

    public LocalDateTime getEndofgame() {
        return endofgame;
    }

    public void setEndofgame(LocalDateTime endofgame) {
        this.endofgame = endofgame;
    }

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getPausingsince() {
        return pausingsince;
    }

    public void setPausingsince(LocalDateTime pausingsince) {
        this.pausingsince = pausingsince;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGametype() {
        return gametype;
    }

    public void setGametype(String gametype) {
        this.gametype = gametype;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getMatchid() {
        return matchid;
    }

    public void setMatchid(long matchid) {
        this.matchid = matchid;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Game{" +
                "pit=" + pit +
                ", startofgame=" + startofgame +
                ", endofgame=" + endofgame +
                ", remaining=" + remaining +
                ", bombname='" + bombname + '\'' +
                ", uuid='" + uuid + '\'' +
                ", result='" + result + '\'' +
                ", gametype='" + gametype + '\'' +
                ", color='" + color + '\'' +
                ", matchid=" + matchid +
                ", json='" + json + '\'' +
                ", state=" + state +
                "} " + super.toString();
    }
}