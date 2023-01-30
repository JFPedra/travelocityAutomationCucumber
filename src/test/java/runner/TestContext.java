package runner;

import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private WebDriver driver;
    private Map<String, Object> dataSharingMap = new HashMap<>();

    public TestContext() {
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void addToDataSharingMap(String key, Object value) {
        dataSharingMap.put(key, value);
    }

    public Object getObjectDataSharingMap(String key) {
        return dataSharingMap.get(key);
    }
}
