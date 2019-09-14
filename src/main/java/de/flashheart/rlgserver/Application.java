package de.flashheart.rlgserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.backend.data.pojo.GameState;
import de.flashheart.rlgserver.backend.service.CoolingDeviceService;
import de.flashheart.rlgserver.backend.service.DBUserService;
import de.flashheart.rlgserver.backend.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EnableCaching
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    MatchService matchService;

    @Autowired
    DBUserService dbUserService;

    @Autowired
    CoolingDeviceService coolingDeviceService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {

//            // Running FC Game - Hot
//            GameState gs1 = createGame("{\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"FUSED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":165,\"timestamp\":0,\"timestamp_game_started\":1534588618394,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":-1,\"bombfused\":true,\"remaining\":119516,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":216780,\"gameEvents\":[{\"pit\":1534588618391,\"event\":\"START_GAME\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588618394,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588693328,\"event\":\"FUSED\",\"gametime\":74909,\"remaining\":180000},{\"pit\":1534588714984,\"event\":\"DEFUSED\",\"gametime\":96544,\"remaining\":1403456},{\"pit\":1534588774734,\"event\":\"FUSED\",\"gametime\":156296,\"remaining\":180000}]}");
//            gs1.setMatchid(111l);
//            gs1.setZoneid(ZoneId.systemDefault().getId());
//            gs1.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//            gs1.setColor("white");
//            gs1.setRemaining();
//            matchService.update(gs1);
//
//            // Finished FC Game - Taken
//            gs1 = createGame(" {\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"EXPLODED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":165,\"timestamp\":0,\"timestamp_game_started\":1534588618394,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":1534588954728,\"bombfused\":true,\"remaining\":1163666,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":336334,\"gameEvents\":[{\"pit\":1534588618391,\"event\":\"START_GAME\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588618394,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":1500000},{\"pit\":1534588693328,\"event\":\"FUSED\",\"gametime\":74909,\"remaining\":180000},{\"pit\":1534588714984,\"event\":\"DEFUSED\",\"gametime\":96544,\"remaining\":1403456},{\"pit\":1534588774734,\"event\":\"FUSED\",\"gametime\":156296,\"remaining\":180000},{\"pit\":1534588954728,\"event\":\"EXPLODED\",\"gametime\":336334,\"remaining\":1163666}]}");
//            gs1.setMatchid(112l);
//            gs1.setZoneid(ZoneId.systemDefault().getId());
//            gs1.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//            gs1.setColor("white");
//
//            matchService.update(gs1);
//
//            // Running FC Game - Cold
//            gs1 = createGame("{\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"DEFUSED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":167,\"timestamp\":0,\"timestamp_game_started\":1534589092302,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":-1,\"bombfused\":false,\"remaining\":8015,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":291985,\"gameEvents\":[{\"pit\":1534589092302,\"event\":\"START_GAME\",\"gametime\":336334,\"remaining\":-36334},{\"pit\":1534589092303,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":300000}]}");
//            gs1.setMatchid(113l);
//            gs1.setZoneid(ZoneId.systemDefault().getId());
//            gs1.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//            gs1.setColor("white");
//            matchService.update(gs1);
//
//
//            // Finished FC Game - Taken
//            gs1 = createGame("{\"bombname\":\"MissionBox iMac\",\"gametype\":\"farcry\",\"state\":\"DEFENDED\",\"uuid\":\"87df601b-3f99-4763-8bfa-9619b01ae77e\",\"matchid\":167,\"timestamp\":0,\"timestamp_game_started\":1534589092302,\"timestamp_game_paused\":-1,\"timestamp_game_ended\":1534589392304,\"bombfused\":false,\"remaining\":-1,\"capturetime\":180000,\"maxgametime\":0,\"gametime\":300001,\"gameEvents\":[{\"pit\":1534589092302,\"event\":\"START_GAME\",\"gametime\":336334,\"remaining\":-36334},{\"pit\":1534589092303,\"event\":\"DEFUSED\",\"gametime\":0,\"remaining\":300000},{\"pit\":1534589392304,\"event\":\"DEFENDED\",\"gametime\":300001,\"remaining\":-1}]}");
//            gs1.setMatchid(114l);
//            gs1.setZoneid(ZoneId.systemDefault().getId());
//            gs1.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//            gs1.setColor("white");
//            matchService.update(gs1);
//
//
//            String t = passwordEncoder.encode("test1234");
//            LoggerFactory.getLogger(getClass()).debug(t);
//            LoggerFactory.getLogger(getClass()).debug(Boolean.toString(passwordEncoder.matches("test1234", t)));
//


                   // Nur zur einmaligen Initialisierung der Datenbank
            dbUserService.createUser("2me@flashheart.de", "torsten", "test1234", "admin");
//            coolingDeviceService.create("Truhe 2", "28-01143bb495aa", new BigDecimal(25).negate(), new BigDecimal(14).negate());
//            coolingDeviceService.create("Truhe 1", "28-01143b9fa4aa", new BigDecimal(25).negate(), new BigDecimal(14).negate());
//


        };

    }


    GameState createGame(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, GameState.class);
    }

}
