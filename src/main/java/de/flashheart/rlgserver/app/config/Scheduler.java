package de.flashheart.rlgserver.app.config;

import de.flashheart.rlgserver.backend.data.entity.IncomingMessage;
import de.flashheart.rlgserver.backend.service.IncomingMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
public class Scheduler {
    @Autowired
    IncomingMessageService incomingMessageService;

    @Scheduled(fixedRate = 10000)
    public void scheduleFixedRateTask() {

        List<String> services = new ArrayList<>();
        services.add("28-01143b9d24aa");
        services.add("28-02159245cb05");
        services.add("28-020292455938");

        List<IncomingMessage> list = incomingMessageService.check_value_out_of_range("temp.celsius", BigDecimal.valueOf(-1d), BigDecimal.valueOf(3d), services);
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }


}
