package de.flashheart.rlgserver.frontend;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import de.flashheart.rlgserver.backend.data.entity.Reading;
import de.flashheart.rlgserver.backend.service.CoolingDeviceService;
import de.flashheart.rlgserver.backend.service.ReadingService;

import java.util.Optional;

/**
 * A Designer generated component for the main-view template.
 * <p>
 * Designer will add and remove fields with @Id mappings but does not overwrite or otherwise change this file.
 */
@Tag("main-view")
@JsModule("./main-view.js")
@Route
public class MainView extends PolymerTemplate<MainView.MainViewModel> {

    @Id("sideview")
    private VerticalLayout sideview;
    @Id("mainview")
    private VerticalLayout mainview;

    /**
     * Creates a new MainView.
     */
    public MainView(CoolingDeviceService coolingDeviceService, ReadingService readingService) {
        // You can initialise any data required for the connected UI components here.
        coolingDeviceService.findAll().forEach(coolingDevice -> {
            Button button = new Button(coolingDevice.getMachine(), buttonClickEvent -> {
                Optional<Reading> reading = readingService.lastReading4(coolingDevice);
                reading.ifPresent(reading1 -> Notification.show("Letzter Wert: " + reading1.getPit() + " " + reading1.getTemperature() + "°C"));
            });
            sideview.add(button);
        });
    }

    /**
     * This model binds properties between MainView and main-view
     */
    public interface MainViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
