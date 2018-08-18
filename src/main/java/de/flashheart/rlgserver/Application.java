package de.flashheart.rlgserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.app.misc.Tools;
import de.flashheart.rlgserver.backend.data.pojo.GameEvent;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.time.LocalDateTime;

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


            // Creating 3 Testmatches for FarCry

            // a finished one


            // Running FC Game - Hot
            GameState gs1 = createGame("{\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"FUSED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":165,\"timestamp\":0,\"timestamp_game_started\":1534588618394,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":-1,\"bombfused\":true,\"remaining\":119516,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":216780,\"gameEvents\":[{\"pit\":1534588618391,\"event\":\"START_GAME\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588618394,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588693328,\"event\":\"FUSED\",\"gametime\":74909,\"remaining\":180000},{\"pit\":1534588714984,\"event\":\"DEFUSED\",\"gametime\":96544,\"remaining\":1403456},{\"pit\":1534588774734,\"event\":\"FUSED\",\"gametime\":156296,\"remaining\":180000}]}");
            gs1.setMatchid(111l);
            gameService.update(gs1);

            // Finished FC Game - Taken
            gs1 = createGame(" {\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"EXPLODED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":165,\"timestamp\":0,\"timestamp_game_started\":1534588618394,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":1534588954728,\"bombfused\":true,\"remaining\":1163666,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":336334,\"gameEvents\":[{\"pit\":1534588618391,\"event\":\"START_GAME\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588618394,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588693328,\"event\":\"FUSED\",\"gametime\":74909,\"remaining\":180000},{\"pit\":1534588714984,\"event\":\"DEFUSED\",\"gametime\":96544,\"remaining\":1403456},{\"pit\":1534588774734,\"event\":\"FUSED\",\"gametime\":156296,\"remaining\":180000},{\"pit\":1534588954728,\"event\":\"EXPLODED\",\"gametime\":336334,\"remaining\":1163666}]}");
            gs1.setMatchid(112l);
            gameService.update(gs1);

            // Running FC Game - Cold
            gs1 = createGame("{\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"DEFUSED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":167,\"timestamp\":0,\"timestamp_game_started\":1534589092302,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":-1,\"bombfused\":false,\"remaining\":8015,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":291985,\"gameEvents\":[{\"pit\":1534589092302,\"event\":\"START_GAME\",\"gametime\":336334,\"remaining\":-36334},{\"pit\":1534589092303,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":300000}]}");
            gs1.setMatchid(113l);
            gameService.update(gs1);

            // Finished FC Game - Taken
            gs1 = createGame("{\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"DEFENDED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":167,\"timestamp\":0,\"timestamp_game_started\":1534589092302,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":1534589392304,\"bombfused\":false,\"remaining\":-1,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":300001,\"gameEvents\":[{\"pit\":1534589092302,\"event\":\"START_GAME\",\"gametime\":336334,\"remaining\":-36334},{\"pit\":1534589092303,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":300000},{\"pit\":1534589392304,\"event\":\"DEFENDED\",\"gametime\":300001,\"remaining\":-1}]}");
            gs1.setMatchid(114l);
            gameService.update(gs1);


//

//            ObjectMapper mapper = new ObjectMapper();
//
//            String json = mapper.writeValueAsString(gameState);
//
//            log.info(json);
//
//            GameState gameState1 = mapper.readValue(json, GameState.class);
//
//            log.info(gameState1.toString());


        };

    }


    void checkTransaction(GameState gameState) {
        try {
            gameService.update(gameState);

            gameState.setTimestamp_game_started(System.currentTimeMillis());
            gameService.update(gameState);

            gameState.setTimestamp_game_started(System.currentTimeMillis());
            gameService.update(gameState);

            gameState.setTimestamp_game_started(System.currentTimeMillis());
            gameService.update(gameState);


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    GameState createGame(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, GameState.class);
    }

    GameState createFinishedFarcryGame() {
        GameState gameState = new GameState("VirtualMissionBox #1", GameState.TYPE_FARCRY, "7f9fbe9f-ff89-4ae0-9532-e4c5cd82e93d", 111l);
        gameState.setState("finished");

        LocalDateTime ldt = LocalDateTime.now().minusDays(1l);

        gameState.setTimestamp_game_started(Tools.toLocalMillis(ldt));
        gameState.setTimestamp_game_ended(Tools.toLocalMillis(ldt.plusMinutes(25l)));

        gameState.setBombfused(true);
        gameState.setRemaining(0l);
        gameState.setCapturetime(180000l);
        gameState.setMaxgametime(25 * 60 * 1000);
        gameState.setGametime(180000l);

        gameState.getGameEvents().add(new GameEvent(System.currentTimeMillis(), "SOMEEVENT", 1212212l, 1212121222111l));
        gameState.getGameEvents().add(new GameEvent(System.currentTimeMillis(), "SOMEEVENT1", 1212212l, 1212121222111l));
        gameState.getGameEvents().add(new GameEvent(System.currentTimeMillis(), "SOMEEVENT2", 1212212l, 1212121222111l));
        return gameState;
    }
}
