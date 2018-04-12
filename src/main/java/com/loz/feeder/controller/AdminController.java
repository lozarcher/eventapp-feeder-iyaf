package com.loz.feeder.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loz.ScheduledTasks;
import com.loz.feeder.dao.feed.facebook.event.Event;
import com.loz.feeder.dao.feed.facebook.event.EventResponse;
import com.loz.feeder.service.FacebookFeedService;
import com.loz.feeder.service.RefreshService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AdminController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    ScheduledTasks scheduledTasks;

    @Autowired
    RefreshService refreshService;

    @RequestMapping("/refresh")
    @ResponseBody
    public String refreshData() {
        //scheduledTasks.refreshEvents();
        //scheduledTasks.refreshTweets();
        scheduledTasks.refreshPosts();
        scheduledTasks.refreshPerformers();
        LOGGER.info("/refresh called - done");
        return "Refresh done";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/secure/updateevents", produces = "text/plain")
    @ResponseBody
    public String updateEvents(@RequestParam("events") String eventsJson) {
        try {
            String output = "";
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
            EventResponse eventResponse = mapper.readValue(eventsJson, EventResponse.class);
            for (Event e : eventResponse.getData()) {
                output += e.getName()+"\n";
            }
            refreshService.storeEventsAndVenues(eventResponse.getData());
            return "Storing " + output;

        } catch (IOException e) {
            return "Error "+e.getLocalizedMessage();
        }


    }

}
