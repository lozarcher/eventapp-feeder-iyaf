package com.loz.c4.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstalledPlatform {

    @Value("${samsung.key}")
    private String samsungKey;

    public List<Installed>getPlatforms() {
        List<Installed> platforms = new ArrayList<>();

        Installed intSamsung = new Installed();
        intSamsung.setConfigUrl("https://samsung.int.channel4.com/properties/6.16/all4-samsung-properties-prod.json");
        intSamsung.setVersionUrl("https://samsung.int.channel4.com/properties/releasenumber.xml");
        intSamsung.setTitle("Samsing INT");
        intSamsung.setKey(samsungKey);
        platforms.add(intSamsung);

        Installed testSamsung = new Installed();
        testSamsung.setConfigUrl("https://samsung.test.channel4.com/properties/6.16/all4-samsung-properties-prod.json");
        testSamsung.setVersionUrl("https://samsung.test.channel4.com/properties/releasenumber.xml");
        testSamsung.setTitle("Samsing TEST");
        testSamsung.setKey(samsungKey);
        platforms.add(testSamsung);

        Installed stageSamsung = new Installed();
        stageSamsung.setConfigUrl("https://samsung.stage.channel4.com/properties/6.16/all4-samsung-properties-prod.json");
        stageSamsung.setVersionUrl("https://samsung.stage.channel4.com/properties/releasenumber.xml");
        stageSamsung.setTitle("Samsing STAGE");
        stageSamsung.setKey(samsungKey);
        platforms.add(stageSamsung);

        Installed prodSamsung = new Installed();
        prodSamsung.setConfigUrl("https://samsung.channel4.com/properties/6.16/all4-samsung-properties-prod.json");
        prodSamsung.setVersionUrl("https://samsung.channel4.com/properties/releasenumber.xml");
        prodSamsung.setTitle("Samsing PROD");
        prodSamsung.setKey(samsungKey);
        platforms.add(prodSamsung);

        return platforms;
    }


}
