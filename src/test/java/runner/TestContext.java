package runner;

import org.openqa.selenium.WebDriver;

public class TestContext {
    private WebDriver driver;

    public TestContext() {
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
