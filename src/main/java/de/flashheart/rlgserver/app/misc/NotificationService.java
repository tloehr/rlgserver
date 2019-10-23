package de.flashheart.rlgserver.app.misc;

import de.flashheart.rlgserver.backend.data.entity.CoolingDevice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService implements HasLogger {
    //    private HashMap<String, Triple> alerts = new HashMap<>();
    public static final int TOO_LOW = 0;
    public static final int NORMAL = 1;
    public static final int TOO_HIGH = 2;
    public static final int DEVICE_MISSING = 3;
    public static final int DEVICE_NEW_DEVICE = 4;
    public static final String[] SITUATIONEN = new String[]{"zu niedrig", "normal", "zu hoch", "Sensor fehlt", "Sensor neu"};
    public final EmailServiceImpl emailService;
    @Value("${notification.threshold}")
    private int THRESHOLD;
    @Value("${tcserver.devmode}")
    private boolean DEVMODE;

    private final Map<String, ArrayList<String>> watchlist;

    public NotificationService(EmailServiceImpl emailService) {
        this.emailService = emailService;
        watchlist = Collections.synchronizedMap(new HashMap<>());
    }

    private void addEvent(String uuid, int event, String description) {
        getLogger().debug(String.format("adding Event %s for device %s", description, uuid));
        watchlist.putIfAbsent(uuid, new ArrayList<>());
        if (event == NORMAL) {
            watchlist.get(uuid).clear();
        } else if (event == DEVICE_MISSING) {
            emailService.sendSimpleMessage("torsten.loehr@gmail.com", "Benachrichtigung", "Device %s is missing");
        } else {
            watchlist.get(uuid).add(description);
        }
    }

    public void addEvent(CoolingDevice coolingDevice, BigDecimal temperature) {
        int event = NotificationService.NORMAL;
        String message = "Wert normal für " + coolingDevice.getMachine() + ": " + temperature.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
        if (coolingDevice.getMin().compareTo(temperature) > 0) {
            event = NotificationService.TOO_LOW;
            message = "Wert zu niedrig für " + coolingDevice.getMachine() + ". Soll > " + coolingDevice.getMin() + "°C  Ist: " + temperature.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
        }
        if (temperature.compareTo(coolingDevice.getMax()) > 0) {
            event = NotificationService.TOO_HIGH;
            message = "Wert zu hoch für " + coolingDevice.getMachine() + ". Soll < " + coolingDevice.getMax() + "°C  Ist: " + temperature.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
        }
        addEvent(coolingDevice.getUuid(), event, message);
    }

    public void checkForNotifications() {
        if (DEVMODE) return;
        final StringBuilder notificationText = new StringBuilder();
        watchlist.forEach((uuid, events) -> {
            getLogger().debug(String.format("Number of events for device %s: %s", uuid, events.size()));
            if (events.size() > THRESHOLD) {
                getLogger().debug("Threshold reached. Notifiying.");
                events.forEach(message -> notificationText.append(message + " // [" + uuid + "]\n"));
                events.clear();
            }
        });
        if (notificationText.length() > 0) {
            emailService.sendSimpleMessage("torsten.loehr@gmail.com", "Benachrichtigung", notificationText.toString());
        }
    }

}
