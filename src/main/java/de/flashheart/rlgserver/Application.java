package de.flashheart.rlgserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.backend.data.entity.Game;
import de.flashheart.rlgserver.backend.data.pojo.GameEvent;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.data.repositories.GameRepository;
import de.flashheart.rlgserver.backend.service.GameService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    GameService gameService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
//            // save a couple of customers
//            repository.save(new Customer("Jack", "Bauer"));
//            repository.save(new Customer("Chloe", "O'Brian"));
//            repository.save(new Customer("Kim", "Bauer"));
//            repository.save(new Customer("David", "Palmer"));
//            repository.save(new Customer("Michelle", "Dessler"));
//
//            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (Customer customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");
//
//            // fetch an individual customer by ID
//            repository.findById(1L)
//                    .ifPresent(customer -> {
//                        log.info("Customer found with findById(1L):");
//                        log.info("--------------------------------");
//                        log.info(customer.toString());
//                        log.info("");
//                    });
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastName('Bauer'):");
//            log.info("--------------------------------------------");
//            repository.findByLastName("Bauer").forEach(bauer -> {
//                log.info(bauer.toString());
//            });
//            // for (Customer bauer : repository.findByLastName("Bauer")) {
//            // 	log.info(bauer.toString());
//            // }
//            log.info("");

            GameState gameState = new GameState();
            gameState.setBombname("OCF Flagge #1292887452");
            gameState.setGametype("farcry");
            gameState.setUuid("7f9fbe9f-ff89-4ae0-9532-e4c5cd82e93d");
            gameState.setMatchid(34l);
            gameState.setTimestamp_game_started(new DateTime().getMillis());
            gameState.setTimestamp_game_paused(new DateTime().plusHours(1).getMillis());
            gameState.setTimestamp_game_ended(new DateTime().plusDays(1).getMillis());
            gameState.setBombfused(true);
            gameState.setRemaining(150000l);
            gameState.setCapturetime(121211212121l);
            gameState.setMaxgametime(892189212l);
            gameState.setGametime(System.currentTimeMillis());
            gameState.setGamestate(GameState.NON_EXISTENT);

            gameState.getGameEvents().add(new GameEvent(new DateTime().getMillis(), "SOMEEVENT", 1212212l, 1212121222111l));
            gameState.getGameEvents().add(new GameEvent(new DateTime().getMillis(), "SOMEEVENT1", 1212212l, 1212121222111l));
            gameState.getGameEvents().add(new GameEvent(new DateTime().getMillis(), "SOMEEVENT2", 1212212l, 1212121222111l));


            log.info(gameState.toString());

//            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(gameState);

            log.info(json);

            GameState gameState1 = mapper.readValue(json, GameState.class);

            log.info(gameState1.toString());

            checkTransaction(gameState);

        };

    }


    void checkTransaction(GameState gameState) {
        try {
            gameService.update(gameState);

            gameState.setTimestamp_game_started(new DateTime().getMillis());
            gameService.update(gameState);

            gameState.setTimestamp_game_started(new DateTime().getMillis());
            gameService.update(gameState);

            gameState.setTimestamp_game_started(new DateTime().getMillis());
            gameService.update(gameState);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
