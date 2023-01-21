package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FlightResultsPage extends BasePage{
    private final By pageContent = By.cssSelector("ul[data-test-id='listings']");
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

    private String originalWindow = getDriver().getWindowHandle();

    public FlightResultsPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsPresent(showMoreButton, 15);
    }

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

    public FlightResultsPage selectFirstFlightOption() {
        List<WebElement> flightResults = findListOfElements(resultButton);
        scrollToTheTop();
        click(flightResults.get(0));
        click(selectButton);
        return new FlightResultsPage(getDriver());
    }
    public FlightDetailsPage selectThirdFlightOption() {
        List<WebElement> flightResults = findListOfElements(resultButton);
        //scrollToTheTop();
        click(flightResults.get(2));
        click(selectButton);
        if(isElementPresentWithLocator(unlockTripSavingsModal, 2)) {
            click(noThanksButton);
        }
        logger.info("Amount of tabs: " +  getDriver().getWindowHandles().size());
        waitUntilTabsToBe(2);
        getDriver().getWindowHandles().forEach( tab ->logger.info("Tabs opened: " + tab));
        return new FlightDetailsPage(getDriver());
    }

    private void scrollToTheTop() {
        scrollToElement(findElementBy(header));
    }
    private void goAndClickOnFlightResult(WebElement flightResult) {
        if(!flightResult.isEnabled())
            scrollToElement(flightResult);
        click(flightResult);
    }
}
