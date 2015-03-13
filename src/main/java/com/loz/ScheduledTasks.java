package com.loz;

import com.loz.dao.LastRefreshDao;
import com.loz.service.RefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    LastRefreshDao lastRefreshDao;

    @Autowired
    RefreshService refreshService;

    @Scheduled(cron = "${cron.refresh.events}")
    public void refreshEvents() {
        LOGGER.debug("Refreshing Events");
        refreshService.updateEventsAndVenues();
    }

    @Scheduled(cron = "${cron.refresh.events}")
    public void refreshTraders() {
        LOGGER.debug("Refreshing Traders");
        refreshService.updateTraders();
    }

    @Scheduled(cron = "${cron.refresh.tweets}")
    public void refreshTweets() {
        LOGGER.debug("Refreshing Tweets");
        refreshService.updateTweets();
    }
}
