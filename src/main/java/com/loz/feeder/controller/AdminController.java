package com.loz.feeder.controller;

import com.loz.ScheduledTasks;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    ScheduledTasks scheduledTasks;

    @RequestMapping("/refresh")
    @ResponseBody
    public String refreshData() {
        scheduledTasks.refreshEvents();
        scheduledTasks.refreshTweets();
        scheduledTasks.refreshPosts();
        scheduledTasks.refreshPerformers();
        LOGGER.info("/refresh called - done");
        return "Refresh done";
    }
}
