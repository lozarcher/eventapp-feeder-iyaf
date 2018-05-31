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

        Installed intRoku = new Installed();
        intRoku.setConfigUrl("https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-int.json");
        intRoku.setVersionUrl("https://roku-p06.int.channel4.com/properties/releasenumber.xml");
        intRoku.setTitle("Roku INT");
        intRoku.setKey(rokuKey);
        platforms.add(intRoku);

        Installed testRoku = new Installed();
        testRoku.setConfigUrl("https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-test.json");
        testRoku.setVersionUrl("https://roku-p06.test.channel4.com/properties/releasenumber.xml");
        testRoku.setTitle("Roku TEST");
        testRoku.setKey(rokuKey);
        platforms.add(testRoku);

        Installed stageRoku = new Installed();
        stageRoku.setConfigUrl("https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-stage.json");
        stageRoku.setVersionUrl("https://roku-p06.stage.channel4.com/properties/releasenumber.xml");
        stageRoku.setTitle("Roku STAGE");
        stageRoku.setKey(rokuKey);
        platforms.add(stageRoku);

        Installed prodRoku = new Installed();
        prodRoku.setConfigUrl("https://roku-p06.channel4.com/properties/"+rokuConfigVersion+"/all4-roku-properties-prod.json");
        prodRoku.setVersionUrl("https://roku-p06.channel4.com/properties/releasenumber.xml");
        prodRoku.setTitle("Roku PROD");
        prodRoku.setKey(rokuKey);
        platforms.add(prodRoku);

        return platforms;
    }


}
