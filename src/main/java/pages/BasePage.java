package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * This page contains all methods that may be used in any page of Travelocity web page
 */
public abstract class BasePage {
    private final WebDriver driver;
    private final int MAX_TIMEOUT_IN_SECONDS = 40;
    private final Logger logger = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        getDriver().manage().window().maximize();
    }

    /**
     * Get fluent wait with a predefined max timeout, with a pooling of 250 milliseconds
     * @return fluent wait
     */
    protected FluentWait<WebDriver> getWait() {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(MAX_TIMEOUT_IN_SECONDS)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

    /**
     * Get a fluent wait with a pooling of 250 millseconds
     * @param maxTimeoutInSeconds: max wait time
     * @return the fluent wait
     */
    protected FluentWait<WebDriver> getWait(int maxTimeoutInSeconds) {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(maxTimeoutInSeconds)).
                pollingEvery(Duration.ofMillis(250)).
                ignoring(NotFoundException.class);
    }

    /**
     * Find all elements with the same locator
     * @param elementsLocator: locator of the elements to be looked for
     * @return a list of elements foubd
     */
    protected List<WebElement> findListOfElements(By elementsLocator) {
        logger.info("Looking for elements by: " +  elementsLocator);
        List<WebElement> elementList = driver.findElements(elementsLocator);
        logger.info("There were found " + elementList.size() + " elements");
        return elementList;
    }

    /**
     * Find element using a By locator
     * @param locator: locator to identify the element
     * @return element found as WebElement
     */
    protected WebElement findElementBy(By locator) {
        waitUntilElementIsPresent(locator);
        return driver.findElement(locator);
    }

    /**
     * find and get element inside of a parent element
     * @param parentElement: element that should contain the other element
     * @param locatorOfChildElement: locator of the element ot be looked for
     * @return the child element found as WebElement
     */
    protected WebElement findElementInsideOf(WebElement parentElement, By locatorOfChildElement) {
        return parentElement.findElement(locatorOfChildElement);
    }

    /**
     * Wait until element is present
     * @param elementLocator: locator of the element
     */
    protected void waitUntilElementIsPresent(By elementLocator) {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            logger.info("Element: " +  elementLocator + " was present");
        } catch (TimeoutException exception) {
            logger.error("Element: " + elementLocator + " was not present");
        }
    }

    /**
     * Wait until element is present
     * @param element: element to be validated
     */
    protected void waitUntilElementIsPresent(WebElement element) {
        getWait().until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
    }

    /**
     * Wait until element is clickable
     * @param elementLocator locator of the element to wait
     */
    protected void waitUntilElementIsClickable(By elementLocator) {
        try {
            getWait().until(ExpectedConditions.elementToBeClickable(elementLocator));
            logger.info("Element located by: " +  elementLocator + " is clickable");
        }catch (TimeoutException exception) {
            logger.error("Element located by: " +  elementLocator + " isn't clickable");
        }
    }

    /**
     * Wait until element is present
     * @param elementLocator: locator of the element
     * @param maxTimeoutInSeconds: max time to wait in seconds
     */
    protected void waitUntilElementIsPresent(By elementLocator, int maxTimeoutInSeconds) {
        try {
            getWait(maxTimeoutInSeconds).until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            logger.info("Element: " +  elementLocator + " was present");
        } catch (TimeoutException exception) {
            logger.error("Element: " + elementLocator + " was not present");
        }
    }

    /**
     * Wait until element is clickable
     * @param element: element to validate
     */
     protected void waitElementToBeClickable(WebElement element) {
         logger.info("Wait until element: " + element +  " is clickable");
        getWait().until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
     }

    /**
     *  Wait until browser has a number of tabs open
     * @param windowsAmount: number of tabs that should be open
     */
    protected void waitUntilTabsToBe(int windowsAmount) {
        getWait().until(driver -> driver.getWindowHandles().size() == windowsAmount);
    }

    /**
     * Wait until attribute element has the value provided
     * @param elementLocator: locator of the element
     * @param attribute: attribute to check
     * @param value: desired value
     */
    protected void waitElementAttributeToBe(By elementLocator, String attribute, String value) {
        getWait().until(ExpectedConditions.attributeToBe(elementLocator, attribute, value));
        logger.info(elementLocator + " has " + attribute + " as " + findElementBy(elementLocator).getAttribute(attribute));
    }

    /**
     * Get attribute of the element
     * @param elementLocator: locator of the element
     * @param attribute: attribute to check
     * @return attribute value
     */

    protected String getAttributeOf(By elementLocator, String attribute) {
        return findElementBy(elementLocator).getAttribute(attribute);
    }

    /**
     * Click without check if the element is clickable
     * @param elementLocator: locator of the element
     */
    protected void clickWithoutWait(By elementLocator) {
        logger.info("Clicking on " + elementLocator);
        waitUntilElementIsPresent(elementLocator);
        driver.findElement(elementLocator).click();
        logger.info("Element " +  elementLocator + " was clicked");
    }

    /**
     * Click on an element
     * @param elementLocator: locator of the element
     */
    protected void click(By elementLocator) {
        logger.info("Clicking on " + elementLocator);
        getWait().until(ExpectedConditions.elementToBeClickable(elementLocator));
        driver.findElement(elementLocator).click();
        logger.info("Element " +  elementLocator + " was clicked");
    }

    /**
     * Click on an element
     * @param element element to be clicked
     */
    protected void click(WebElement element) {
        logger.info("Clicking on " + element.getAccessibleName());
        waitUntilElementIsPresent(element);
        waitElementToBeClickable(element);
        element.click();
        logger.info("Element " +  element + " was clicked");
    }

    /**
     * Type enter on an element
     * @param elementLocator: locator of the element
     */
    protected void typeEnter(By elementLocator) {
        driver.findElement(elementLocator).sendKeys(Keys.ENTER);
    }

    /**
     * Send keys to an input element
     * @param element: element that receives the keys
     * @param textToSend: keys to be sent
     */
    protected void sendKeys(By element, String textToSend) {
        logger.info("Sending " + textToSend + " to element " +  element);
        waitUntilElementIsPresent(element);
        driver.findElement(element).sendKeys(textToSend);
    }

    /**
     * Get the element text
     * @param elementLocator: locator of the element that contains text
     * @return text that the element has
     */
    protected String getText(By elementLocator) {
        logger.info("Getting text from element locator: " + elementLocator);
        waitUntilElementIsPresent(elementLocator);
        String elementText = driver.findElement(elementLocator).getText();
        logger.info("From element locator" + elementLocator +  " the text found was " + elementText);
        return elementText;
    }

    /**
     * Get the element text
     * @param element: element that contains text
     * @return text that the element has
     */
    protected String getText(WebElement element) {
        waitUntilElementIsPresent(element);
        String elementText = element.getText();
        logger.info("Text found was " + elementText);
        return elementText;
    }

    /**
     * Validates if the element is displayed
     * @param element: element to be validated
     * @return true if the element is displayed
     */
    protected boolean isElementDisplayed(WebElement element) {
        logger.info("Checking if element " + element + " is displayed");
        return element.isDisplayed();
    }

    /**
     * Validates if the element is displayed
     * @param elementLocator: element locator to be validated
     * @return true if the element is displayed
     */
    protected boolean isElementDisplayed(By elementLocator) {
        logger.info("Checking if element located by " + elementLocator + " is displayed");
        return findElementBy(elementLocator).isDisplayed();
    }

    /**
     * Validates if an element is selected
     * @param elementLocator: element locator
     * @return true if the element was selected
     */
    protected boolean isElementSelected(By elementLocator) {
        boolean isElementSelected = findElementBy(elementLocator).isSelected();
        logger.info("Element located by " + elementLocator + " was selected: " +  isElementSelected);
        return isElementSelected;
    }

    /**
     * Validates if element is present in the DOM
     * @param elementLocator: element locator
     * @param maxTimeoutInSeconds: max timeout to wait
     * @return true if the element was located
     */
    protected boolean isElementPresentWithLocator(By elementLocator, int maxTimeoutInSeconds) {
        boolean isPresent;
        try {
            getWait(maxTimeoutInSeconds).until(ExpectedConditions.presenceOfElementLocated(elementLocator));
            logger.info("Element located by " + elementLocator + " is present");
            isPresent = true;
        }catch (TimeoutException exception) {
            logger.error("Element located by " + elementLocator + " isn't present");
            isPresent = false;
        }
        return isPresent;
    }

    /**
     * Validate if element contains a child element
     * @param parentElement: element that should contain the other element
     * @param childElementLocator: element to be looked for
     * @return true if the child element is inside of parent element
     */
    protected boolean isElementPresentInsideOf(WebElement parentElement, By childElementLocator) {
        try {
            parentElement.findElement(childElementLocator);
            return true;
        } catch (NotFoundException exception) {
            return false;
        }
    }

    /**
     * Scroll to element location
     * @param element: element to be used as location reference
     */
    protected void scrollToElement(WebElement element) {
        new Actions(driver).scrollToElement(element).perform();
    }

    /**
     * Get driver
     * @return WebDriver instanced
     */
    protected WebDriver getDriver() {
        return this.driver;
    }
}
