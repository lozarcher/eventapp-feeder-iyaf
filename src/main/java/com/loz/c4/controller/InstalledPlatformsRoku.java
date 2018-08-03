package com.loz.c4.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstalledPlatformsRoku {

    @Value("${roku.key}")
    private String rokuKey;

    @Value("${roku.config.version}")
    private String rokuConfigVersion;

    public List<Installed>getPlatforms() {
        List<Installed> platforms = new ArrayList<>();

        platforms.add(
                new Installed(
                        "Roku INT ("+rokuConfigVersion+")",
                        "https://roku-p06.int.channel4.com/properties/releasenumber.xml",
                        "https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-int.json",
                        rokuKey,
                        rokuConfigVersion));

        platforms.add(
                new Installed(
                        "Roku TEST ("+rokuConfigVersion+")",
                        "https://roku-p06.test.channel4.com/properties/releasenumber.xml",
                        "https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-test.json",
                        rokuKey,
                        rokuConfigVersion));

        platforms.add(
                new Installed(
                        "Roku STAGE ("+rokuConfigVersion+")",
                        "https://roku-p06.stage.channel4.com/properties/releasenumber.xml",
                        "https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-stage.json",
                        rokuKey,
                        rokuConfigVersion));

        platforms.add(
                new Installed(
                        "Roku PROD ("+rokuConfigVersion+")",
                        "https://roku-p06.channel4.com/properties/releasenumber.xml",
                        "https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-prod.json",
                        rokuKey,
                        rokuConfigVersion));

        return platforms;
    }


}
