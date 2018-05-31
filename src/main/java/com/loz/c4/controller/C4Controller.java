package com.loz.c4.controller;

import com.loz.c4.exception.C4ConfigException;
import com.loz.c4.service.C4ConfigService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class C4Controller {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(C4Controller.class);

    @Autowired
    C4ConfigService c4ConfigService;

    @RequestMapping("/configs")
    @ResponseBody
    public String getConfigs() {
        LOGGER.info("/configs called - done");
        try {
            return c4ConfigService.generatePropertiesPage().toString();
        } catch (C4ConfigException e) {
            return e.getLocalizedMessage();
        }
    }


}
