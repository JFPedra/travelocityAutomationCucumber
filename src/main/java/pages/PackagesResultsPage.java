package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PackagesResultsPage extends BasePage {
    private final By showMoreButton = By.cssSelector("button[data-test-id='show-more-button']");
    private final By headerResultsSummary = By.cssSelector("section[data-stid='results-header']");
    private final By sidebarMap = By.cssSelector("section[data-stid='desktop-sidebar']");
    private final By packagesResults = By.cssSelector("div[data-stid='lodging-card-responsive']");
    private final By resultTitle = By.cssSelector("h2[class='uitk-heading uitk-heading-5 overflow-wrap']");
    private final By resultPrice = By.cssSelector("div[data-stid='price-lockup-wrapper']");
    private final By hotelReview = By.cssSelector("span[class='uitk-text uitk-type-300 uitk-type-bold uitk-text-default-theme']");
    private final Logger logger = LoggerFactory.getLogger(PackagesResultsPage.class);

    public PackagesResultsPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getPackageResults() {
        return findListOfElements(packagesResults);
    }

    public boolean isSidebarMapDisplayed() {
        waitUntilElementIsPresent(showMoreButton, 15);
        return isElementDisplayed(sidebarMap);
    }

    public boolean isHeaderSummaryDisplayed() {
        return isElementDisplayed(headerResultsSummary);
    }

    public boolean isPriceDisplayedForResult(WebElement resultContainer, int resultContainerNumber) {
        boolean isPricePresent = isElementPresentInsideOf(resultContainer, resultPrice);
        logger.info("Was price inside of result number " + resultContainerNumber + "? " + isPricePresent);
        return isPricePresent;
    }

    public float getHotelReview(WebElement result) {
        String review = getText(findElementInsideOf(result, hotelReview));
        String hotelName = getText(findElementInsideOf(result, resultTitle));
        logger.info("Review found at " + hotelName + ": " + review);
        return Float.parseFloat(review);
    }

    public WebElement findFirstResultGraterThan(float minimumReview) {
        WebElement resultWithTheBestReview = getPackageResults().stream().filter(result -> getHotelReview(result) >= minimumReview)
                .findFirst()
                .orElseThrow(NotFoundException::new);
        logger.info("The first hotel found with review greater than " + minimumReview + " was " +
                getText(findElementInsideOf(resultWithTheBestReview, resultTitle)));
        return resultWithTheBestReview;
    }

    public void selectResult(WebElement resultToSelect) {
        logger.info("Selecting: " +  getText(findElementInsideOf(resultToSelect, resultTitle)));
        click(resultToSelect);
    }
}