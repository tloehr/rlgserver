package de.flashheart.rlgserver.app.misc;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService implements HasLogger {
    //    private HashMap<String, Triple> alerts = new HashMap<>();
    public static final int TOO_LOW = 0;
    public static final int NORMAL = 1;
    public static final int TOO_HIGH = 2;
    public final EmailServiceImpl emailService;

    // anders mit eigener tabelle und last seen usw. damit man auch ausgefallene Geräte melden kann.
    // das aber später

    // uuid, true wenn bereits per mail über das ereignis berichtet wurde
    private final Map<String, Triple<LocalDateTime, Boolean, String>> notableEvents;

    public NotificationService(EmailServiceImpl emailService) {
        this.emailService = emailService;
        this.notableEvents = Collections.synchronizedMap(new HashMap<>());
        getLogger().debug("Contructor() is called");
    }


    public void addEvent(String uuid, int event, String description) {
        getLogger().debug("add Event" + uuid + " Event:" + event + "  " + description);
        // ein normles ereignis entfernt immer ein auffälliges Ereignis aus der Liste
        if (event == NORMAL) {
            notableEvents.remove(uuid);
        } else {
            if (!notableEvents.containsKey(uuid)) {
                notableEvents.put(uuid, new ImmutableTriple<>(LocalDateTime.now(), false, description));
                // das war uns neu, wir müssen bescheid in der nächsten Runde bescheid sagen.
            }
        }
        notableEvents.forEach((s, localDateTimeBooleanStringTriple) -> {
            getLogger().debug(s);
            getLogger().debug(localDateTimeBooleanStringTriple.toString());
        });
    }

    public void checkForNotifications() {
        final StringBuilder notificationText = new StringBuilder();
        notableEvents.forEach((uuid, eventDescription) -> {
            if (!eventDescription.getMiddle()) {
//                getLogger().debug(uuid + " macht Ärger " + eventDescription.getRight());
                notificationText.append(uuid + " macht Ärger " + eventDescription.getRight() + "\n");
                notableEvents.put(uuid, new ImmutableTriple<>(eventDescription.getLeft(), true, eventDescription.getRight()));
            }
        });
        if (notificationText.length() == 0) {
            emailService.sendSimpleMessage("torsten.loehr@gmail.com", "Temperatur-Problem festgestellt", notificationText.toString());
        }
    }

}
