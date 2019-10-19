package de.flashheart.rlgserver.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import de.flashheart.rlgserver.backend.data.entity.Reading;
import de.flashheart.rlgserver.backend.service.CoolingDeviceService;
import de.flashheart.rlgserver.backend.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {
    @Autowired
    public MainView(ReadingService readingService, CoolingDeviceService coolingDeviceService) {


        coolingDeviceService.findAll().forEach(coolingDevice -> {
            Button button = new Button(coolingDevice.getMachine(), buttonClickEvent -> {
                Optional<Reading> reading = readingService.lastReading4(coolingDevice);
                reading.ifPresent(reading1 -> Notification.show("Letzter Wert: " + reading1.getPit() + " " + reading1.getTemperature() + "°C"));
            });
            add(button);
        });

    }

}