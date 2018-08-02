package de.flashheart.rlgserver.backend.data.pojo;

import org.joda.time.DateTime;

/**
 * Created by tloehr on 25.04.15.
 */
public class GameEvent {

    protected DateTime pit;
    protected String event;
    protected long gametime;
    protected long remaining;


    public GameEvent(DateTime pit, String event, long gametime, long remaining) {
        this.pit = pit;
        this.event = event;
        this.gametime = gametime;
        this.remaining = remaining;
    }

    public DateTime getPit() {
        return pit;
    }


    public String getEvent() {
        return event;
    }

    public long getGametime() {
        return gametime;
    }

    public long getRemaining() {
        return remaining;
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "pit=" + pit +
                ", event='" + event + '\'' +
                ", gametime=" + gametime +
                ", remaining=" + remaining +
                '}';
    }
}
