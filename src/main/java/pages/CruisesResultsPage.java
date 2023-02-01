package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CruisesResultsPage extends BasePage{
    private final By showMoreButton = By.linkText("Show more");
    private final By cruisesResults = By.cssSelector("div[data-stid='search-sailing-card']");
    private final By discountBadge = By.cssSelector("span[class='uitk-badge-base-text']");
    public CruisesResultsPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsPresent(showMoreButton);
    }

    public boolean doesAnyResultHaveDiscount() {
        List<WebElement> cruisesResultsList = findListOfElements(cruisesResults);
        return cruisesResultsList.stream().anyMatch(cruisesResult -> isElementPresentInsideOf(cruisesResult, discountBadge));
    }

    public boolean doesAnyResultWithoutDiscount() {
        List<WebElement> cruisesResultsList = findListOfElements(cruisesResults);
        return cruisesResultsList.stream().anyMatch(cruisesResult -> !isElementPresentInsideOf(cruisesResult, discountBadge));
    }

    private int getDiscount(WebElement resultCruise) {
        if(isElementPresentInsideOf(resultCruise, discountBadge)) {
            WebElement discountElement = findElementInsideOf(resultCruise, discountBadge);
            String discountText = getText(discountElement).replaceAll("[^0-9]", "");
            return Integer.parseInt(discountText);
        } else {
            return 0;
        }
    }

    private WebElement getResultWithMaximumDiscount() {
        List<WebElement> cruisesResultsList = findListOfElements(cruisesResults);
        WebElement resultWithMaximumDiscount = cruisesResultsList.stream().max((cruisesResult1, cruisesResult2) ->
                Integer.compare(getDiscount(cruisesResult1), getDiscount(cruisesResult2))).get();
        return resultWithMaximumDiscount;
    }

    public void selectResultWithMaximumDiscount() {
        click(getResultWithMaximumDiscount());
    }
}
