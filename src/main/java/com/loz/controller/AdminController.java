package com.loz.controller;

import com.loz.ScheduledTasks;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    ScheduledTasks scheduledTasks;

    @RequestMapping("/refresh")
    @ResponseBody
    public String refreshData() {
        scheduledTasks.refreshEvents();
        scheduledTasks.refreshTweets();
        return "Refresh done";
    }
}
