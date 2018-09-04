package de.flashheart.rlgserver.backend.jobs;

import de.flashheart.rlgserver.app.misc.HasLogger;
import de.flashheart.rlgserver.backend.service.MatchService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@DisallowConcurrentExecution
@Component
public class CleanupGameJob extends QuartzJobBean implements HasLogger {
    public final static String name = "cleanupgamejob";

//    @Autowired
//	private MatchService gameService;

	// for sending parameters by the JobDetail
//	public void setName(String name) {
//			this.name = name;
//		}

    @Override
   	protected void executeInternal(JobExecutionContext context) {
   		getLogger().debug(String.format("Hello %s!", this.name));
//		gameService.fixBrokenMatches();
   	}

}
