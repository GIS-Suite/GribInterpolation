package com.github.gissuite.gribinterpolation.res;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Resources {
    private static final Logger logger = LoggerFactory.getLogger(Resources.class);
    private static Resources instance = null;
    private static Properties properties = null;
    private Resources() {}

    public static Resources init() {
        if (instance == null || properties == null) {
            instance = new Resources();
            getProperties();
        }
        return instance;
    }

    private static void getProperties() {
        properties = new Properties();
        final String path = "src/main/resources/appsettings.config";
        try (FileInputStream stream = new FileInputStream(path)) {
            properties.load(stream);
        } catch (IOException exception) {
            logger.error("Unable to get application config.", exception);
        }
    }
}
