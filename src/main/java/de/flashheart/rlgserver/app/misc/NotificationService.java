package de.flashheart.rlgserver.app.misc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private final ArrayList<String> notifiedAlready; // enthält die UUID aller Geräte, die bereits benachrichtigt wurden.

    public NotificationService(EmailServiceImpl emailService) {
        this.emailService = emailService;
        watchlist = Collections.synchronizedMap(new HashMap<>());
        notifiedAlready = new ArrayList<>();
    }

    private void addEvent(String uuid, int event, String description) {
        getLogger().debug(String.format("adding Event %s for device %s", description, uuid));
        watchlist.putIfAbsent(uuid, new ArrayList<>());
        if (event == NORMAL) {
            watchlist.get(uuid).clear();
            notifiedAlready.remove(uuid);
        } else if (event == DEVICE_MISSING) {
            if (!DEVMODE) emailService.sendSimpleMessage("torsten.loehr@gmail.com", "Benachrichtigung", String.format(description, uuid));
        } else {
            watchlist.get(uuid).add(description);
        }
    }

//    public void addMissingDeviceEvent(CoolingDevice coolingDevice) {
//        addEvent(coolingDevice.getUuid(), DEVICE_MISSING, "Device %s is missing");
//    }
//
//    public void addEvent(CoolingDevice coolingDevice, BigDecimal temperature) {
//        int event = NotificationService.NORMAL;
//        String message = "Wert normal für " + coolingDevice.getMachine() + ": " + temperature.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
//        if (coolingDevice.getMin().compareTo(temperature) > 0) {
//            event = NotificationService.TOO_LOW;
//            message = "Wert zu niedrig für " + coolingDevice.getMachine() + ". Soll > " + coolingDevice.getMin() + "°C  Ist: " + temperature.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
//        }
//        if (temperature.compareTo(coolingDevice.getMax()) > 0) {
//            event = NotificationService.TOO_HIGH;
//            message = "Wert zu hoch für " + coolingDevice.getMachine() + ". Soll < " + coolingDevice.getMax() + "°C  Ist: " + temperature.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
//        }
//        addEvent(coolingDevice.getUuid(), event, message);
//    }

    public void checkForNotifications() {
        if (DEVMODE) return;
        final StringBuilder notificationText = new StringBuilder();
        watchlist.forEach((uuid, events) -> {
            if (!notifiedAlready.contains(uuid)) { // einmal benachrichtigen reicht
                getLogger().debug(String.format("Number of events for device %s: %s", uuid, events.size()));
                if (events.size() > THRESHOLD) {
                    getLogger().debug("Threshold reached. Notifiying.");
                    events.forEach(message -> notificationText.append(message + " // [" + uuid + "]\n"));
                    events.clear();
                    notifiedAlready.add(uuid);
                }
            }
        });
        if (notificationText.length() > 0) {
            emailService.sendSimpleMessage("torsten.loehr@gmail.com", "Benachrichtigung", notificationText.toString());
        }
    }

}
