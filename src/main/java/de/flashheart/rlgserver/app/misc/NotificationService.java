package de.flashheart.rlgserver.app.misc;

import de.flashheart.rlgserver.backend.data.entity.CoolingDevice;
import org.javatuples.Quartet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public static final int DEVICE_MISSING = 3;
    public static final int DEVICE_NEW_DEVICE = 4;
    public static final String[] SITUATIONEN = new String[]{"zu niedrig", "normal", "zu hoch", "Sensor fehlt", "Sensor neu"};
    public final EmailServiceImpl emailService;

    // anders mit eigener tabelle und last seen usw. damit man auch ausgefallene Geräte melden kann.
    // das aber später

    // uuid, true wenn bereits per mail über das ereignis berichtet wurde
    // (wann, event, gesendet?, description)
    private final Map<String, Quartet<LocalDateTime, Integer, Boolean, String>> currentSituation;

    public NotificationService(EmailServiceImpl emailService) {
        this.emailService = emailService;
        this.currentSituation = Collections.synchronizedMap(new HashMap<>());
    }

    private void addEvent(String uuid, int event, String description) {
        getLogger().debug("add Event" + uuid + " Event:" + event + "  " + description);

        if (!currentSituation.containsKey(uuid) || currentSituation.get(uuid).getValue1() != event) {
            currentSituation.put(uuid, new Quartet(LocalDateTime.now(), event, false, description));
        }

        currentSituation.forEach((s, localDateTimeBooleanStringTriple) -> {
            getLogger().debug(s);
            getLogger().debug(localDateTimeBooleanStringTriple.toString());
        });
    }

//    public void addMissingDeviceEvent(CoolingDevice coolingDevice) {
//        addEvent(coolingDevice.getUuid(), DEVICE_MISSING, coolingDevice.getMachine() + "// Dieser Sensor antwortet nicht mehr.");
//    }
//
//    public void addNewDeviceEvent(CoolingDevice coolingDevice) {
//        addEvent(coolingDevice.getUuid(), DEVICE_NEW_DEVICE, coolingDevice.getMachine() + "// Neuer Sensor gefunden.");
//    }

    public void addEvent(CoolingDevice coolingDevice, BigDecimal currentReading) {
        int event = NotificationService.NORMAL;
        String message = "Wert normal für " + coolingDevice.getMachine() + ": " + currentReading.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
        if (coolingDevice.getMin().compareTo(currentReading) > 0) {
            event = NotificationService.TOO_LOW;
            message = "Wert zu niedrig für " + coolingDevice.getMachine() + ". Soll > " + coolingDevice.getMin() + "°C  Ist: " + currentReading.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
        }
        if (currentReading.compareTo(coolingDevice.getMax()) > 0) {
            event = NotificationService.TOO_HIGH;
            message = "Wert zu hoch für " + coolingDevice.getMachine() + ". Soll < " + coolingDevice.getMax() + "°C  Ist: " + currentReading.setScale(2, RoundingMode.HALF_UP).toString() + "°C";
        }
        addEvent(coolingDevice.getUuid(), event, message);
    }

    public void checkForNotifications() {
        final StringBuilder notificationText = new StringBuilder();
        currentSituation.forEach((uuid, eventDescription) -> {
            // value2 ist der boolean der anzeigt ob das schon reported wurde.
            if (!eventDescription.getValue2()) {
                notificationText.append(eventDescription.getValue3() + " // [" + uuid + "]\n");
                currentSituation.put(uuid, new Quartet(eventDescription.getValue0(), eventDescription.getValue1(), true, eventDescription.getValue3())); // mark as sent already
            }
        });
        if (notificationText.length() > 0) {
            emailService.sendSimpleMessage("torsten.loehr@gmail.com", "Benachrichtigung", notificationText.toString());
        }
    }

}
