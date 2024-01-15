package com.agilecrm_automation.common;

import java.io.*;
import java.util.Properties;

public class PropertyHandler {

    Properties properties;
    public PropertyHandler(String fileName) {

        String filepath = System.getProperty("user.dir")+"/src/test/resources/properties/"+fileName;
        File file = new File(filepath);
        FileReader reader = null;
        properties = new Properties();
        try {
            reader = new FileReader(file);
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public String getProperty(String key)  {

        return properties.getProperty(key);

    }
}
