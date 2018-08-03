package com.loz.c4.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstalledPlatformsHTML5 {

    @Value("${samsung.key}")
    private String samsungKey;

    @Value("${samsung.config.version}")
    private String samsungConfigVersion;

    public List<Installed>getPlatforms() {
        List<Installed> platforms = new ArrayList<>();

        platforms.add(
                new Installed(
                "Samsung INT ("+samsungConfigVersion+")",
                "https://samsung.int.channel4.com/properties/releasenumber.xml",
                "https://samsung.int.channel4.com/properties/"+samsungConfigVersion+"/all4-samsung-properties-prod.json",
                samsungKey,
                samsungConfigVersion));


        platforms.add(
                new Installed(
                        "Samsung TEST ("+samsungConfigVersion+")",
                        "https://samsung.test.channel4.com/properties/releasenumber.xml",
                        "https://samsung.test.channel4.com/properties/"+samsungConfigVersion+"/all4-samsung-properties-prod.json",
                        samsungKey,
                        samsungConfigVersion));

        platforms.add(
                new Installed(
                        "Samsung STAGE ("+samsungConfigVersion+")",
                        "https://samsung.stage.channel4.com/properties/releasenumber.xml",
                        "https://samsung.stage.channel4.com/properties/"+samsungConfigVersion+"/all4-samsung-properties-prod.json",
                        samsungKey,
                        samsungConfigVersion));

        platforms.add(
                new Installed(
                        "Samsung PROD ("+samsungConfigVersion+")",
                        "https://samsung.channel4.com/properties/releasenumber.xml",
                        "https://samsung.channel4.com/properties/"+samsungConfigVersion+"/all4-samsung-properties-prod.json",
                        samsungKey,
                        samsungConfigVersion));

        return platforms;
    }


}
