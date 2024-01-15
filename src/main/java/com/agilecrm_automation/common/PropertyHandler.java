package com.agilecrm_automation.common;

import java.io.*;
import java.util.Properties;

public class PropertyHandler {

    String path;
    PropertyHandler(String path){
        this.path = path;
    }

    public String getProperty(String key) throws IOException {
        String filepath = System.getProperty("user.dir")+"/src/test/resources/properties/"+path;
        File file = new File(filepath);
        FileReader reader = new FileReader(file);

        Properties properties = new Properties();
        properties.load(reader);

       return properties.getProperty(key);



    }
}
