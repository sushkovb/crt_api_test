package helpers;

import java.io.IOException;

public class ConfigProp {
    public String getPropertyByKey(String key) throws IOException {
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("application.properties"));
        return System.getProperty(key);
    }
}
