package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelsResultsPage extends BasePage{
    private final By joiningDiscountSection = By.cssSelector("div[data-stid='lodging-message-card-message-result']");
    private final By hotelsResults = By.cssSelector("div[data-stid='lodging-card-responsive']");
    private final By sponsoredResultMark = By.xpath("//[text()='Ad']");
    private final By showMoreButton = By.cssSelector("button[data-stid='show-more-results']");
    private final Logger logger = LoggerFactory.getLogger(HotelsResultsPage.class);
    public HotelsResultsPage(WebDriver driver) {
        super(driver);
        waitUntilElementIsPresent(showMoreButton);
    }

    public boolean isDiscountOptionVisible() {
        return isElementDisplayed(joiningDiscountSection);
    }

    private boolean isSponsoredMarkPresentInsideOf(WebElement parentElement, int resultNumber) {
        boolean isMarkPresent = isElementPresentInsideOf(parentElement, sponsoredResultMark);
        resultNumber++;
        logger.info("Does result number " + resultNumber + " has de sponsor mark? " + isMarkPresent);
        return isMarkPresent;
    }

    public boolean doesTheSponsoredResultsAppearsFirstly() {
        List<WebElement> hotelsList = getResultsList();
        boolean doesPreviousResultHaveSponsorMark = isSponsoredMarkPresentInsideOf(hotelsList.get(0), 0);
        Optional<Boolean> shouldResultHaveSponsorMarK = shouldResultHaveSponsorMarK(doesPreviousResultHaveSponsorMark);
        List<Optional<Boolean>> doesSponsoredResultsAppearFirst = new ArrayList<>();
        int resultNumber = 1;
        do {
            doesPreviousResultHaveSponsorMark = isSponsoredMarkPresentInsideOf(hotelsList.get(resultNumber), resultNumber);
            resultNumber++;
            logger.info("Should hotel " +  resultNumber + " have sponsor badge? " + shouldResultHaveSponsorMarK.orElse(false));
            if(!shouldResultHaveSponsorMarK.isPresent()) {
                doesSponsoredResultsAppearFirst.add(Optional.empty());
            } else if(!shouldResultHaveSponsorMarK.get()){
                logger.error("Hotel " +  resultNumber + " has not proper order");
                doesSponsoredResultsAppearFirst.add(Optional.of(!doesPreviousResultHaveSponsorMark));
            }
            shouldResultHaveSponsorMarK = shouldResultHaveSponsorMarK(doesPreviousResultHaveSponsorMark);
        }while (resultNumber < hotelsList.size());
        logger.info("Should result have sponsor badge?" +  doesSponsoredResultsAppearFirst);
        return !doesSponsoredResultsAppearFirst.contains(false);
    }

    private List<WebElement> getResultsList() {
        List<WebElement> resultsList = findListOfElements(hotelsResults);
        logger.info("Amount of elements found: " + resultsList.size());
        return resultsList;
    }

    private Optional<Boolean> shouldResultHaveSponsorMarK(boolean doesPreviousResultHasSponsorMark) {
        if(doesPreviousResultHasSponsorMark) {
            return Optional.empty();
        } else {
            return Optional.of(false);
        }
    }
}