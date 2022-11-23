package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadFromConfig {
    public static String readFromFile(String data){
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            properties.load(fileInputStream);
        }catch (IOException e){
            System.out.println(e);
        }
        return properties.getProperty(data);
    }
}
