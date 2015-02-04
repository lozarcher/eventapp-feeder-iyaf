package com.loz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FeedController {

    @Autowired
    FacebookAccessToken facebookAccessToken;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        String appId = facebookAccessToken.getToken();
        return "Hello World! "+appId;
    }
}
