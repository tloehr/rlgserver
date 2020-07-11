package de.flashheart.rlgserver.backend.jobs;

import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.app.misc.NotificationService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class AlertJob extends QuartzJobBean implements HasLogger {
    public final static String name = "alertjob";

    @Autowired
    private NotificationService notificationService;


    @Override
    protected void executeInternal(JobExecutionContext context) {
        getLogger().debug("checking for alert conditions");
        notificationService.checkForNotifications();
    }

}
