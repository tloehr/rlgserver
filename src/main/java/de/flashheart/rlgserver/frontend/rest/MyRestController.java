package de.flashheart.rlgserver.frontend.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.backend.data.entity.Game;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MyRestController implements HasLogger {

    private static final String tcustlate = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public static final String GREETING = "/greeting";
    public static final String GAMESTATE_CREATE = "/rest/gamestate/create";


    public static final String DUMMY_cust = "/rest/cust/dummy";
    public static final String GET_cust = "/rest/cust/{id}";
    public static final String GET_ALL_cust = "/rest/custs";
    public static final String CREATE_cust = "/rest/cust/create";
    public static final String DELETE_cust = "/rest/cust/delete/{id}";


    private final GameService gameService;
    

    @Autowired
    public MyRestController(GameService gameService) {
        this.gameService = gameService;
//        this.mapper = new ObjectMapper();
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
    @RequestMapping(value = GAMESTATE_CREATE, method = RequestMethod.POST)
    public @ResponseBody
    GameState saveGameState(@RequestBody GameState gameState) {
        getLogger().debug("got: " + gameState);
        try {
            getLogger().info("inbound from: " + gameState.getBombname() + " " + gameState.getUuid());
            gameService.update(gameState);
        } catch (JsonProcessingException e) {
            getLogger().warn(e.getMessage(), e);
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
