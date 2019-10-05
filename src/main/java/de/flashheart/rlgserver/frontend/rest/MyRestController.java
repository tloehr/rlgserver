package de.flashheart.rlgserver.frontend.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.app.misc.NotificationService;
import de.flashheart.rlgserver.backend.data.entity.CoolingDevice;
import de.flashheart.rlgserver.backend.data.entity.Match;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.data.pojo.SensorEvent;
import de.flashheart.rlgserver.backend.service.CoolingDeviceService;
import de.flashheart.rlgserver.backend.service.MatchService;
import de.flashheart.rlgserver.backend.service.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyRestController implements HasLogger {

    private static final String tcustlate = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public static final String GREETING = "/greeting";
    public static final String GAMESTATE_CREATE = "/rest/gamestate/create";
    public static final String GAMESTATE_GET = "/rest/gamestate/get";
    public static final String GAMESTATE_LIST = "/rest/gamestate/list";
    public static final String GAMESTATE_LISTRUNNING = "/rest/gamestate/listrunning";

    public static final String SENSOR_EVENT = "/rest/sensor/event";
    public static final String CREATE_COOLING_DEVICE = "/rest/coolingdevice/create";

    public static final String DUMMY_cust = "/rest/cust/dummy";
    public static final String GET_cust = "/rest/cust/{id}";
    public static final String GET_ALL_cust = "/rest/custs";
    public static final String CREATE_cust = "/rest/cust/create";
    public static final String DELETE_cust = "/rest/cust/delete/{id}";


    private final MatchService matchService;
    private final ReadingService readingService;
    private final CoolingDeviceService coolingDeviceService;
    private final NotificationService notificationService;
    private final ObjectMapper mapper = new ObjectMapper();
//    Random rn = new Random();
//    private HashMap<String, String> alerts = new HashMap<>();

    @Autowired
    public MyRestController(MatchService matchService, ReadingService readingService, CoolingDeviceService coolingDeviceService, NotificationService notificationService) {
        this.matchService = matchService;
//        this.mapper = new ObjectMapper();
        this.readingService = readingService;
        this.coolingDeviceService = coolingDeviceService;
        this.notificationService = notificationService;
    }

    @RequestMapping(value = GREETING, method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(tcustlate, name));
    }


    //    @RequestMapping(value = DUMMY_cust, method = RequestMethod.GET)
//    public @ResponseBody
//    Customer getDummyCustomer() {
//        log.info("Start getDummyCustomer");
//        Customer cust = new Customer("Dummy","Dummy");
//        customerRepository.save(cust);
//        log.info("created: id="+cust.getId());
//        return cust;
//    }
//
//    @RequestMapping(value = GET_cust, method = RequestMethod.GET)
//    public @ResponseBody
//    Customer getCustomer(@PathVariable("id") long custId) {
//        log.info("Start getCustomer. ID=" + custId);
//        Optional<Customer> optionalCustomer = customerRepository.findById(custId);
//        return optionalCustomer.get();
//    }
//
//    @RequestMapping(value = GET_ALL_cust, method = RequestMethod.GET)
//    public @ResponseBody
//    List<Customer> getAllCustomers() {
//        log.info("Start getAllCustomers.");
//
//        ArrayList<Customer> result = new ArrayList();
//        for (Customer cust : customerRepository.findAll()){
//            result.add(cust);
//        }
//
//        return result;
//    }
//


    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    @RequestMapping(value = SENSOR_EVENT, method = RequestMethod.POST)
    public @ResponseBody
    SensorEvent saveSensorEvent(@RequestBody SensorEvent sensorEvent) {
//        getLogger().debug("got: " + sensorEvent);
        readingService.create(sensorEvent);

        coolingDeviceService.findByUuid(sensorEvent.getUuid()).ifPresent(coolingDevice -> {
            int event = NotificationService.NORMAL;
            String message = "";
            if (coolingDevice.getMin().compareTo(sensorEvent.getValue()) > 0) {
                event = NotificationService.TOO_LOW;
                message = "Wert zu niedrig für " + coolingDevice.getMachine() + ". Soll >" + coolingDevice.getMin() + "°C  Ist: " + sensorEvent.getValue() + "°C";
            }
            if (sensorEvent.getValue().compareTo(coolingDevice.getMax()) > 0) {
                event = NotificationService.TOO_HIGH;
                message = "Wert zu hoch für " + coolingDevice.getMachine() + ". Soll <" + coolingDevice.getMax() + "°C  Ist: " + sensorEvent.getValue() + "°C";
            }

            notificationService.addEvent(sensorEvent.getUuid(), event, message);
        });

        return sensorEvent;
    }

    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    @RequestMapping(value = CREATE_COOLING_DEVICE, method = RequestMethod.POST)
    public @ResponseBody
    CoolingDevice createCoolingDeviceIfNecessary(@RequestBody HashMap coolingDeviceDescription) {
        return coolingDeviceService.createOrUpdate(coolingDeviceDescription);
    }


    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    @RequestMapping(value = GAMESTATE_CREATE, method = RequestMethod.POST)
    public @ResponseBody
    GameState saveGameState(@RequestBody GameState gameState) {
        getLogger().debug("got: " + gameState);
        try {
            getLogger().info("inbound from: " + gameState.getBombname() + " " + gameState.getUuid());
            matchService.update(gameState);
        } catch (JsonProcessingException e) {
            getLogger().warn(e.getMessage(), e);
        }

        return gameState;
    }

    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    @RequestMapping(value = GAMESTATE_LIST, method = RequestMethod.GET)
    public @ResponseBody
    List<GameState> getGameStates(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> from, @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> to) {
        ArrayList<GameState> result = new ArrayList<>();

        matchService.findGamesBetween(from.orElse(LocalDateTime.of(1970, 1, 1, 0, 0)), to.orElse(LocalDateTime.of(2500, 12, 31, 23, 59))).forEach(match -> {
            try {
                result.add(mapper.readValue(match.getJson(), GameState.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    // The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    @RequestMapping(value = GAMESTATE_LISTRUNNING, method = RequestMethod.GET)
    public @ResponseBody
    List<GameState> listRunning() {
        ArrayList<GameState> result = new ArrayList<>();

        matchService.findRunningMatches().forEach(match -> {
            try {
                result.add(mapper.readValue(match.getJson(), GameState.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    @RequestMapping(value = GAMESTATE_GET, method = RequestMethod.GET)
    public @ResponseBody
    GameState getCustomer(@RequestParam("id") long id) {
        getLogger().debug("GET the gamestate with id: " + id);
        Optional<Match> optionalGame = matchService.findById(id);
        GameState gameState = null;
        if (optionalGame.isPresent()) {
            try {
                gameState = mapper.readValue(optionalGame.get().getJson(), GameState.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gameState;
    }

//
//    @RequestMapping(value = DELETE_cust, method = RequestMethod.DELETE)
//    public @ResponseBody
//    Customer deleteCustomer(@PathVariable("id") long custId) {
//        log.info("Start deleteCustomer.");
//        Optional<Customer> optionalCustomer = customerRepository.findById(custId);
//        customerRepository.delete(optionalCustomer.get());
//        return optionalCustomer.get();
//    }
}
