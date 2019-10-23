package de.flashheart.rlgserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.flashheart.rlgserver.app.misc.EmailServiceImpl;
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
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EnableCaching
public class Application extends SpringBootServletInitializer {
    // https://stackoverflow.com/questions/54226981/vaadin-spring-projekt-expects-no-arg-constructor-only-on-tomcat-not-local
    // Finally, we initialize the Servlet context required by Tomcat by implementing the SpringBootServletInitializer interface.
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    MatchService matchService;

    @Autowired
    DBUserService dbUserService;

    @Autowired
    CoolingDeviceService coolingDeviceService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public EmailServiceImpl emailService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            dbUserService.createUserIfNecessary("2me@flashheart.de", "torsten", "test1234", "admin");

//            log.debug(coolingDeviceService.lastSeen("dahjdhaskj").toString());
//            log.debug(coolingDeviceService.lastSeen("28-01143b87deaa").toString());

        };

    }


    GameState createGame(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, GameState.class);
    }

}
