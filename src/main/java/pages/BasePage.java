package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    private WebDriver driver;
    private final int MAX_TIMEOUT_IN_SECONDS = 20;
    private final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected FluentWait<WebDriver> getWait() {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(MAX_TIMEOUT_IN_SECONDS)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

    protected FluentWait<WebDriver> getWait(int maxTimeoutInSeconds) {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(MAX_TIMEOUT_IN_SECONDS)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

    protected boolean waitUntilElementIsPresent(By element) {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(element));
            logger.info("Element: " +  element + " was present");
            return true;
        } catch (TimeoutException exception) {
            logger.error("Element: " + element + " was not present");
            return false;
        }
    }

    protected boolean waitUntilElementIsPresent(By element, int maxTimeoutInSeconds) {
        try {
            getWait(maxTimeoutInSeconds).until(ExpectedConditions.presenceOfElementLocated(element));
            logger.info("Element: " +  element + " was present");
            return true;
        } catch (TimeoutException exception) {
            logger.error("Element: " + element + " was not present");
            return false;
        }
    }

    protected boolean waitElementToBeClickable(By element) {
        try {
            getWait().until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element: " +  element + " was clickable");
            return true;
        }catch (TimeoutException exception) {
            logger.error("Element: " + element + " was not clickable");
            return false;
        }
    }

    protected boolean waitElementToBeClickable(By element, int maxTimeoutInSeconds) {
        try {
            getWait(maxTimeoutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element: " +  element + " was clickable");
            return true;
        }catch (TimeoutException exception) {
            logger.error("Element: " + element + " was not clickable");
            return false;
        }
    }

    protected void click(By element) {
        logger.info("Clicking on " + element);
        getWait().until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
        logger.info("Element " +  element + " was clicked");
    }

    protected void click(By element, int timeoutInSeconds) {
        logger.info("Clicking on " + element);
        getWait(timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
        logger.info("Element " +  element + " was clicked");
    }

    protected void sendKeys(By element, String textToSend) {
        logger.info("Sending " + textToSend + " to element " +  element);
        driver.findElement(element).sendKeys(textToSend);
    }

    protected String getText(By element) {
        logger.info("Getting text from: " + element);
        waitUntilElementIsPresent(element);
        String elementText = driver.findElement(element).getText();
        logger.info("From " + element +  " the text found was " + elementText);
        return elementText;
    }

}
