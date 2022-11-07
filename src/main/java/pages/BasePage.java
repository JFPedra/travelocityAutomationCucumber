package pages;

import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import utils.Configuration;

import java.time.Duration;

public class BasePage {
    private WebDriver driver;
    private final int MAX_TIMEOUT_IN_SECONDS = new Configuration().getMaxTimeout();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected FluentWait<WebDriver> getWait() {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(MAX_TIMEOUT_IN_SECONDS)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

}
