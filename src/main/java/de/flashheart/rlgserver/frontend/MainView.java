package de.flashheart.rlgserver.frontend;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import de.flashheart.rlgserver.backend.data.entity.Reading;
import de.flashheart.rlgserver.backend.data.pojo.CurrentSituation;
import de.flashheart.rlgserver.backend.data.repositories.ReadingRepository;
import de.flashheart.rlgserver.backend.service.CoolingDeviceService;
import de.flashheart.rlgserver.backend.service.ReadingService;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
@PWA(name = "My Application", shortName = "My App")
public class MainView extends AppLayout {

    /**
     * Creates a new MainView.
     */
    public MainView(CoolingDeviceService coolingDeviceService, ReadingService readingService, ReadingRepository readingRepository) {


        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), img);
        Tabs tabs = new Tabs(new Tab("Home"), new Tab("About"));
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);

        HorizontalLayout layout = new HorizontalLayout();
        setContent(layout);
        layout.setWidthFull();
        layout.getStyle().set("border", "1px solid #9E9E9E");

        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setHeightFull();

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        layout.add(leftLayout);
        layout.setFlexGrow(0.5, leftLayout);

        Grid<CurrentSituation> currentSituationGrid = new Grid(CurrentSituation.class);
        currentSituationGrid.setItems(coolingDeviceService.getCurrentSituation());
        leftLayout.add(currentSituationGrid);
        leftLayout.add(buttonsLayout);

        Grid<Reading> readingGrid = new Grid<>(Reading.class);
        CallbackDataProvider<Reading, Void> provider = DataProvider.fromCallbacks(query -> readingService.findAnyMatching(Optional.empty(), new Page, query -> re);
        readingGrid.setDataProvider(provider);
        

        


        // You can initialise any data required for the connected UI components here.
        coolingDeviceService.findAll().forEach(coolingDevice -> {
            Button button = new Button(coolingDevice.getMachine(), buttonClickEvent -> {
                Optional<Reading> reading = readingService.lastReading4(coolingDevice);
                reading.ifPresent(reading1 -> Notification.show("Letzter Wert: " + reading1.getPit() + " " + reading1.getTemperature() + "°C"));
            });
            buttonsLayout.add(button);
        });
    }



}
