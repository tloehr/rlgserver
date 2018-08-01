package de.flashheart.rlgserver;

import de.flashheart.rlgserver.backend.data.entity.Customer;
import de.flashheart.rlgserver.backend.data.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final String tcustlate = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public static final String GREETING = "/greeting";
    public static final String DUMMY_cust = "/rest/cust/dummy";
    public static final String GET_cust = "/rest/cust/{id}";
    public static final String GET_ALL_cust = "/rest/custs";
    public static final String CREATE_cust = "/rest/cust/create";
    public static final String DELETE_cust = "/rest/cust/delete/{id}";

    @Autowired
    CustomerRepository customerRepository;

    @RequestMapping(value = GREETING, method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(tcustlate, name));
    }


    @RequestMapping(value = DUMMY_cust, method = RequestMethod.GET)
    public @ResponseBody
    Customer getDummyCustomer() {
        log.info("Start getDummyCustomer");
        Customer cust = new Customer("Dummy","Dummy");
        customerRepository.save(cust);
        log.info("created: id="+cust.getId());
        return cust;
    }

    @RequestMapping(value = GET_cust, method = RequestMethod.GET)
    public @ResponseBody
    Customer getCustomer(@PathVariable("id") long custId) {
        log.info("Start getCustomer. ID=" + custId);
        Optional<Customer> optionalCustomer = customerRepository.findById(custId);
        return optionalCustomer.get();
    }

    @RequestMapping(value = GET_ALL_cust, method = RequestMethod.GET)
    public @ResponseBody
    List<Customer> getAllCustomers() {
        log.info("Start getAllCustomers.");

        ArrayList<Customer> result = new ArrayList();
        for (Customer cust : customerRepository.findAll()){
            result.add(cust);
        }

        return result;
    }

    @RequestMapping(value = CREATE_cust, method = RequestMethod.POST)
    public @ResponseBody
    Customer createCustomer(@RequestBody Customer cust) {
        log.info("Start createCustomer.");
        customerRepository.save(cust);
        return cust;
    }

    @RequestMapping(value = DELETE_cust, method = RequestMethod.DELETE)
    public @ResponseBody
    Customer deleteCustomer(@PathVariable("id") long custId) {
        log.info("Start deleteCustomer.");
        Optional<Customer> optionalCustomer = customerRepository.findById(custId);
        customerRepository.delete(optionalCustomer.get());
        return optionalCustomer.get();
    }
}
