package io.gupshup.wpp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configurator {
    private Logger logger = LogManager.getLogger(Configurator.class);
    private static Configurator config;
    private static Properties properties;
    
    private Configurator() {

        logger.info("Initializing the  PropertyLoader ..... ");
        ClassLoader classLoader = Configurator.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("config.properties");
        properties=new Properties();

            try {
                properties.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }

        
        logger.info("PropertyLoader Initialized..");
    }
    
    public static String getPropertyValue(String key) {
        if (config == null)
        {
            config = new Configurator();
        }
        return properties.getProperty(key);
    }
    
    public static String getString(String key){
        return getPropertyValue(key);
    }
    
    public static long getLong(String key){
        return Long.parseLong(getString(key));
    }
    
}