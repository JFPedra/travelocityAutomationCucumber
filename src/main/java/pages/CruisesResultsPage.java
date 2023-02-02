package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This page contains the results when the user is looking for cruises
 */
public class CruisesResultsPage extends BasePage{
    private final By showMoreButton = By.linkText("Show more");
    private final By cruisesResults = By.cssSelector("div[data-stid='search-sailing-card']");
    private final By discountBadge = By.cssSelector("span[class='uitk-badge-base-text']");
    public CruisesResultsPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsPresent(showMoreButton);
    }

    /**
     * Validate if at least one result has discount badge
     * @return true if there is at least one result with discount
     */
    public boolean doesAnyResultHaveDiscount() {
        List<WebElement> cruisesResultsList = findListOfElements(cruisesResults);
        return cruisesResultsList.stream().anyMatch(cruisesResult -> isElementPresentInsideOf(cruisesResult, discountBadge));
    }

    /**
     * Validate if at least one result doesn't have discount badge
     * @return true if there is at least one result without discount
     */
    public boolean doesAnyResultWithoutDiscount() {
        List<WebElement> cruisesResultsList = findListOfElements(cruisesResults);
        return cruisesResultsList.stream().anyMatch(cruisesResult -> !isElementPresentInsideOf(cruisesResult, discountBadge));
    }

    /**
     * In case the cruise result has discount offer, get that discount value
     * @param resultCruise: result to be checked
     * @return discount as integer
     */
    private int getDiscount(WebElement resultCruise) {
        if(isElementPresentInsideOf(resultCruise, discountBadge)) {
            WebElement discountElement = findElementInsideOf(resultCruise, discountBadge);
            String discountText = getText(discountElement).replaceAll("[^0-9]", "");
            return Integer.parseInt(discountText);
        } else {
            return 0;
        }
    }

    /**
     * Get the result with the maximum discount
     * @return result with the maximum discount as WebElement
     */
    private WebElement getResultWithMaximumDiscount() {
        List<WebElement> cruisesResultsList = findListOfElements(cruisesResults);
        return cruisesResultsList.stream().max((cruisesResult1, cruisesResult2) ->
                Integer.compare(getDiscount(cruisesResult1), getDiscount(cruisesResult2))).get();
    }

    /**
     * Select result with maximumdiscount
     */
    public void selectResultWithMaximumDiscount() {
        click(getResultWithMaximumDiscount());
    }
}
