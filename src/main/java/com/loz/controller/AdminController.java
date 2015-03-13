package com.loz.controller;

import com.loz.ScheduledTasks;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
        scheduledTasks.refreshTraders();
        LOGGER.info("/refresh called - done");
        return "Refresh done";
    }

}
