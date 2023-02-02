package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This page represents the web page that contains the flight results, and contains methods to validate the results and
 * select the desired flight result
 */
public class FlightResultsPage extends BasePage{
    private final By showMoreButton = By.cssSelector("button[data-test-id='show-more-button']");
    private final By resultContainer = By.cssSelector("li[data-test-id='offer-listing']");
    private final By flightDuration = By.cssSelector("div[data-test-id='journey-duration']");
    private final By selectButton = By.cssSelector("button[stid='select-button']");
    private final By resultButton = By.cssSelector("button[data-stid^='FLIGHTS_DETAILS_AND_FARES']");
    private final By flightFaresAndDetails = By.cssSelector("div[data-stid='flights-details-and-fares']");
    private final By closeFlightDetails = By.cssSelector("button[class*='uitk-toolbar-button-v2-icon-only']");
    private final By unlockTripSavingsModal = By.cssSelector("div[data-test-id='SellHotelForcedChoice']");
    private final By noThanksButton = By.cssSelector("a[data-test-id='forcedChoiceNoThanks']");
    private final By header = By.tagName("header");
    private final Logger logger = LoggerFactory.getLogger(FlightResultsPage.class);

    public FlightResultsPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsPresent(showMoreButton, 15);
    }

    /**
     * Validates if the flight duration is displayed in each result
     * @return true if all results have flight duration
     */
    public boolean areFlightDurationDisplayed() {
        boolean isFlightDurationDisplayed;
        int index = 0;
        List<WebElement> flightResults = findListOfElements(resultContainer);
        do {
            WebElement flightResult = flightResults.get(index);
            isFlightDurationDisplayed = isElementDisplayed(findElementInsideOf(flightResult, flightDuration));
            logger.info("Is flight duration displayed at position " +  index + "? " + isFlightDurationDisplayed);
            if(!isFlightDurationDisplayed)
                return false;
            index ++;
        } while (flightResults.size() > index);
        return true;
    }

    /**
     * Validates if all flight results have flight details and select button
     * @return true if all results have flight details and select button
     */
    public boolean doFlightsHaveDetails() {
        boolean doesFlightHaveSelectButton;
        boolean doesFlightHaveDetails;
        List<WebElement> flightResults = findListOfElements(resultButton);
        int index = 0;
        do {
            goAndClickOnFlightResult(flightResults.get(index));
            waitUntilElementIsPresent(flightFaresAndDetails);
            waitUntilElementIsClickable(selectButton);
            doesFlightHaveSelectButton = isElementDisplayed(findElementBy(selectButton));
            doesFlightHaveDetails = isElementDisplayed(findElementBy(flightFaresAndDetails));
            index++;
            logger.info("Does flight " + index + " has details? " + doesFlightHaveDetails);
            logger.info("Does flight " + index + " has select button? " + doesFlightHaveSelectButton);
            click(closeFlightDetails);
            if(!doesFlightHaveDetails)
                return false;
            if(!doesFlightHaveSelectButton)
                return false;
        }while (flightResults.size() > index);
        return true;
    }

    /**
     * Select first flight result
     * @return Flight results Page
     */
    public FlightResultsPage selectFirstFlightOption() {
        List<WebElement> flightResults = findListOfElements(resultButton);
        scrollToTheTop();
        click(flightResults.get(0));
        click(selectButton);
        return new FlightResultsPage(getDriver());
    }

    /**
     * Select third flight result
     * @return Flight results Page
     */
    public FlightDetailsPage selectThirdFlightOption() {
        List<WebElement> flightResults = findListOfElements(resultButton);
        scrollToTheTop();
        click(flightResults.get(2));
        click(selectButton);
        if(isElementPresentWithLocator(unlockTripSavingsModal, 2)) {
            click(noThanksButton);
        }
        waitUntilTabsToBe(2);
        return new FlightDetailsPage(getDriver());
    }

    /**
     * Scroll to the top of the page
     */
    private void scrollToTheTop() {
        scrollToElement(findElementBy(header));
    }

    /**
     * Scroll to a flight result and click it
     * @param flightResult flight result to be clicked
     */
    private void goAndClickOnFlightResult(WebElement flightResult) {
        if(!flightResult.isEnabled())
            scrollToElement(flightResult);
        click(flightResult);
    }
}
