package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class BasePage {
    private final WebDriver driver;
    private final int MAX_TIMEOUT_IN_SECONDS = 40;
    private final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        getDriver().manage().window().maximize();
    }

    protected FluentWait<WebDriver> getWait() {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(MAX_TIMEOUT_IN_SECONDS)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

    protected FluentWait<WebDriver> getWait(int maxTimeoutInSeconds) {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(maxTimeoutInSeconds)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

    protected List<WebElement> findListOfElements(By elementsLocator) {
        logger.info("Looking for elements by: " +  elementsLocator);
        List<WebElement> elementList = driver.findElements(elementsLocator);
        logger.info("There were found " + elementList.size() + " elements");
        return elementList;
    }

    protected WebElement findElementBy(By locator) {
        waitUntilElementIsPresent(locator);
        return driver.findElement(locator);
    }

    protected WebElement findElementInsideOf(WebElement fatherElement, By locatorOfChildElement) {
        return fatherElement.findElement(locatorOfChildElement);
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

    protected void waitUntilElementIsPresent(WebElement element) {
        getWait().until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
    }

    protected boolean waitUntilElementIsClickable(By elementLocator) {
        try {
            getWait().until(ExpectedConditions.elementToBeClickable(elementLocator));
            logger.info("Element located by: " +  elementLocator + " is clickable");
            return true;
        }catch (TimeoutException exception) {
            logger.error("Element located by: " +  elementLocator + " isn't clickable");
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

     protected void waitElementToBeClickable(WebElement element) {
         logger.info("Wait until element: " + element +  " is clickable");
        getWait().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
     }

    protected String getAttributeOf(By elementLocator, String attribute) {
        return findElementBy(elementLocator).getAttribute(attribute);
    }

    protected void waitElementAttributeToBe(By elementLocator, String attribute, String value) {
        getWait().until(ExpectedConditions.attributeToBe(elementLocator, attribute, value));
        logger.info(elementLocator + " has " + attribute + " as " + findElementBy(elementLocator).getAttribute(attribute));
    }

    protected void clickWithoutWait(By elementLocator) {
        logger.info("Clicking on " + elementLocator);
        waitUntilElementIsPresent(elementLocator);
        driver.findElement(elementLocator).click();
        logger.info("Element " +  elementLocator + " was clicked");
    }

    protected void click(By element) {
        logger.info("Clicking on " + element);
        getWait().until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
        logger.info("Element " +  element + " was clicked");
    }

    protected void click(WebElement element) {
        logger.info("Clicking on " + element.getAccessibleName());
        waitUntilElementIsPresent(element);
        waitElementToBeClickable(element);
        element.click();
        logger.info("Element " +  element + " was clicked");
    }

    protected void typeEnter(By elementLocator) {
        driver.findElement(elementLocator).sendKeys(Keys.ENTER);
    }

    protected void sendKeys(By element, String textToSend) {
        logger.info("Sending " + textToSend + " to element " +  element);
        waitUntilElementIsPresent(element);
        driver.findElement(element).sendKeys(textToSend);
    }

    protected String getText(By Locator) {
        logger.info("Getting text from element locator: " + Locator);
        waitUntilElementIsPresent(Locator);
        String elementText = driver.findElement(Locator).getText();
        logger.info("From element locator" + Locator +  " the text found was " + elementText);
        return elementText;
    }

    protected String getText(WebElement element) {
        waitUntilElementIsPresent(element);
        String elementText = element.getText();
        logger.info("Text found was " + elementText);
        return elementText;
    }

    protected boolean isElementDisplayed(WebElement element) {
        logger.info("Checking if element " + element + " is displayed");
        return element.isDisplayed();
    }

    protected boolean isElementDisplayed(By elementLocator) {
        logger.info("Checking if element located by " + elementLocator + " is displayed");
        return findElementBy(elementLocator).isDisplayed();
    }

    protected boolean isElementSelected(By elementLocator) {
        boolean isElementSelected = findElementBy(elementLocator).isSelected();
        logger.info("Element located by " + elementLocator + " was selected: " +  isElementSelected);
        return isElementSelected;
    }
    protected boolean isElementPresentWithLocator(By locatorElement, int maxTimeoutInSeconds) {
        boolean isPresent;
        try {
            getWait(maxTimeoutInSeconds).until(ExpectedConditions.presenceOfElementLocated(locatorElement));
            logger.info("Element located by " + locatorElement + " is present");
            isPresent = true;
        }catch (TimeoutException exception) {
            logger.error("Element located by " + locatorElement + " isn't present");
            isPresent = false;
        }
        return isPresent;
    }

    protected void waitUntilTabsToBe(int windowsAmount) {
        getWait().until(driver -> driver.getWindowHandles().size() == windowsAmount);
    }

    protected boolean isElementPresentInsideOf(WebElement parentElement, By childElementLocator) {
        try {
            parentElement.findElement(childElementLocator);
            return true;
        } catch (NotFoundException exception) {
            return false;
        }
    }

    protected void scrollToElement(WebElement element) {
        new Actions(driver).scrollToElement(element).perform();
    }

    public WebDriver getDriver() {
        return this.driver;
    }


}
