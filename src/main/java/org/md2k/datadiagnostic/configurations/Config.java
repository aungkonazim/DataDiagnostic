package org.md2k.datadiagnostic.configurations;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static void main(String[] args) {

        System.out.println(get("PHONE_BATTERY"));

    }

    public static String get(String propKey){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config/config.properties");
            // load a properties file
            prop.load(input);

            return prop.getProperty(propKey);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
