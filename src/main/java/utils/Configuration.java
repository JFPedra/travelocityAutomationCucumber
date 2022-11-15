package utils;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    private Properties properties = new Properties();
    private final Logger logger = getLogger(lookup().lookupClass());

    public Configuration() {
        setProperties();
    }

    public void setProperties() {
        logger.info("Setting properties");
        try {
            File propertiesFile = new File("src/test/resources/config.properties");
            FileReader fileReader = new FileReader(propertiesFile);
            properties.load(fileReader);
            logger.info("Properties set");
        } catch (IOException exception) {
            logger.error("Properties weren't set");
            logger.trace(exception.getMessage());
        }
    }


    public WebDriver setUpWebDriver() {
        String browser = getBrowser().toLowerCase().trim();
        switch (browser) {
            case "chrome":
                logger.info("Chrome browser selected");
                return WebDriverManager.chromedriver().create();
            case "firefox":
                logger.info("Firefox browser selected");
                return WebDriverManager.firefoxdriver().create();
            case "safari":
                logger.info("Safari browser selected");
                return WebDriverManager.safaridriver().create();
            case "edge":
                logger.info("Edge driver selected");
                return WebDriverManager.edgedriver().create();
            default:
                logger.error("Not accepted browser was entered: " + browser);
                throw new WebDriverException();
        }
    }

    private String getBrowser() {
        return properties.getProperty("browser");
    }

    public int getMaxTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

}
