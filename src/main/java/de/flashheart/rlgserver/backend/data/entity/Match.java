package de.flashheart.rlgserver.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Games")
public class Match extends AbstractEntity {

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
    long maxgametime; // longest possible matchtime. used to identify broken entries.
    @NotNull
    private String zoneid; // for timezone handling
    @NotNull
    int quality; // the quality of this match. whether it is running, finished or broken.
    @NotNull
    @Lob
    String json;

    public Match() {
    }

    public Match(String uuid, long matchid, String zoneid, String bombname, String gametype, LocalDateTime startofgame, long maxgametime) {
        this.uuid = uuid;
        this.matchid = matchid;
        this.bombname = bombname;
        this.gametype = gametype;
        this.maxgametime = maxgametime;
        pit = LocalDateTime.now();
        this.zoneid = zoneid;
        this.endofgame = null;
        this.pausingsince = null;
        this.startofgame = startofgame;
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

    public long getMaxgametime() {
        return maxgametime;
    }

    public void setMaxgametime(long maxgametime) {
        this.maxgametime = maxgametime;
    }

    public String getZoneid() {
        return zoneid;
    }

    public void setZoneid(String zoneid) {
        this.zoneid = zoneid;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public String toString() {
        return "Game{" +
                "pit=" + pit +
                ", bombname='" + bombname + '\'' +
                ", uuid='" + uuid + '\'' +
                ", gametype='" + gametype + '\'' +
                ", state='" + state + '\'' +
                ", startofgame=" + startofgame +
                ", pausingsince=" + pausingsince +
                ", endofgame=" + endofgame +
                ", remaining=" + remaining +
                ", result='" + result + '\'' +
                ", color='" + color + '\'' +
                ", matchid=" + matchid +
                ", maxgametime=" + maxgametime +
                ", zoneid='" + zoneid + '\'' +
                ", json='" + json + '\'' +
                "} " + super.toString();
    }
}
