package de.flashheart.rlgserver.app.config;

import de.flashheart.rlgserver.backend.jobs.AlertJob;
import de.flashheart.rlgserver.backend.jobs.CleanupGameJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail cleanupGameJobDetail() {
        return JobBuilder.newJob(CleanupGameJob.class)
                .withIdentity("cleanupgamejob1")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger cleanupGameJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(60)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(cleanupGameJobDetail())
                .withIdentity("cleanupgametrigger1")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail alertJobDetail() {
        return JobBuilder.newJob(AlertJob.class)
                .withIdentity("alertjob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger alertJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(10)
                .repeatForever();

        return TriggerBuilder
                .newTrigger()
                .forJob(alertJobDetail())
                .withIdentity("alertjobtrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}