package com.century;

import com.century.db.PgDbService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    private static Properties readAppProperties() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("app.properties");
        Properties properties = new Properties ();
        properties.load(inputStream);
        return properties;
    }

    public static void main(String args[]) throws IOException {
        Properties appProperties = readAppProperties();
        PgDbService pgDbService = new PgDbService(appProperties.getProperty("url"),
                                                  appProperties.getProperty("user"),
                                                  appProperties.getProperty("pass"));

    }
}
