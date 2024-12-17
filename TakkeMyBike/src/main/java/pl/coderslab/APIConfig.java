package pl.coderslab;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class APIConfig {
    private static final String PROPERTIES_FILE = "src/main/resources/key.properties";
    @Getter
    private static String apiKey;

    static {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(input);
            apiKey = properties.getProperty("API_KEY");
            if (apiKey == null) {
                throw new IllegalArgumentException("API_KEY not found in " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load API_KEY from " + PROPERTIES_FILE, e);
        }
    }

}
