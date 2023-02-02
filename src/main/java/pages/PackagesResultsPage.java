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

    /**
     * Get all package results on a list
     * @return return packages as WebElements List
     */
    public List<WebElement> getPackageResults() {
        return findListOfElements(packagesResults);
    }

    /**
     * Validates that side bas is displayed
     * @return true if side bar is displayed
     */
    public boolean isSidebarMapDisplayed() {
        waitUntilElementIsPresent(showMoreButton, 15);
        return isElementDisplayed(sidebarMap);
    }

    /**
     * Validates if header is displayed
     * @return true if header is displayed
     */
    public boolean isHeaderSummaryDisplayed() {
        return isElementDisplayed(headerResultsSummary);
    }

    /**
     * Validates if the price is displayed
     * @param resultContainer: result container where the price should be displayed
     * @param resultContainerNumber: number of the result container
     * @return true if the price is displayed
     */
    public boolean isPriceDisplayedForResult(WebElement resultContainer, int resultContainerNumber) {
        boolean isPricePresent = isElementPresentInsideOf(resultContainer, resultPrice);
        logger.info("Was price inside of result number " + resultContainerNumber + "? " + isPricePresent);
        return isPricePresent;
    }

    /**
     * Get review of one result
     * @param result result to be checked
     * @return true if the review is displayed
     */
    public float getHotelReview(WebElement result) {
        String review = getText(findElementInsideOf(result, hotelReview));
        String hotelName = getText(findElementInsideOf(result, resultTitle));
        logger.info("Review found at " + hotelName + ": " + review);
        return Float.parseFloat(review);
    }

    /**
     * Find first result with a review grater than a review entered as param
     * @param minimumReview: minimum review to compare
     * @return first result with review grater than review entered as param
     */
    public WebElement findFirstResultGraterThan(float minimumReview) {
        WebElement resultWithTheBestReview = getPackageResults().stream().filter(result -> getHotelReview(result) >= minimumReview)
                .findFirst()
                .orElseThrow(NotFoundException::new);
        logger.info("The first hotel found with review greater than " + minimumReview + " was " +
                getText(findElementInsideOf(resultWithTheBestReview, resultTitle)));
        return resultWithTheBestReview;
    }

    /**
     * Select a result
     * @param resultToSelect: result to be selected
     */
    public void selectResult(WebElement resultToSelect) {
        logger.info("Selecting: " +  getText(findElementInsideOf(resultToSelect, resultTitle)));
        click(resultToSelect);
    }
}