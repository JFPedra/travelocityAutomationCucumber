package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchPage extends BasePage{

    private By flightSectionButton = By.linkText("Flights");
    private By sectionButtonSelected = By.cssSelector("li[role='presentation'][class='uitk-tab active']");
    private By originTextSection = By.cssSelector("");
    private By destinationTextSection = By.cssSelector("");

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getSectionSelected() {
        return getText(sectionButtonSelected);
    }
    public SearchPage clickOnFlightSectionButton() {
        click(flightSectionButton);
        return this;
    }

    public SearchPage enterOrigin(String originToEnter) {
        sendKeys(originTextSection, originToEnter);
        return this;
    }
}
