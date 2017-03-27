package org.md2k.datadiagnostic.configurations;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    /*public static void main(String[] args) {

        System.out.println(get("PHONE_BATTERY"));

    }*/

    public static Properties props = null;
    public static String get(String propKey){
        if(props==null) {
            props = new Properties();
            InputStream input = null;

            try {
                System.out.println("Loaded Configurations");
                input = new FileInputStream("config/config.properties");
                // load a properties file
                props.load(input);
                //return prop.getProperty(propKey);

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
        }
        return props.getProperty(propKey);
    }
}
