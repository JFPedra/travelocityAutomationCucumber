package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchPage extends BasePage{

    private final By flightSectionButton = By.linkText("Flights");
    private final By packageSectionButton = By.linkText("Packages");
    private final By sectionButtonSelected = By.cssSelector("li[role='presentation'][class='uitk-tab active']");
    private final By flightsAddedButton = By.id("package-pills-flights");
    private final By stayAddedButton = By.id("package-pills-hotels");
    private final By originTextSection = By.cssSelector("div[data-testid='location-field-leg1-origin-container']");
    private final By destinationTextSection = By.cssSelector("div[data-testid='location-field-leg1-destination-container'");
    private final By originTextInput = By.id("location-field-leg1-origin");
    private final By destinationTextInput = By.id("location-field-leg1-destination");
    private final By travelersButton = By.id("adaptive-menu");
    private final By departureDateButton = By.id("d1-btn");
    private final By arrivalDateButton = By.id("d2-btn");
    private final By nextMonthButton = By.cssSelector("div[class='uitk-calendar'] button:nth-child(2)");
    private final By doneDatePickerButton = By.cssSelector("button[data-stid='apply-date-picker']");
    private final By searchButton = By.cssSelector("button[data-testid='submit-button']");


    private final LocalDate today = LocalDate.now();

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

    public SearchPage clickOnPackagesSectionButton() {
        click(packageSectionButton);
        return this;
    }

    public SearchPage enterOrigin(String originToEnter) {
        click(originTextSection);
        sendKeys(originTextInput, originToEnter);
        typeEnter(originTextInput);
        return this;
    }

    public SearchPage enterDestination(String destinyToEnter) {
        click(destinationTextSection);
        sendKeys(destinationTextInput, destinyToEnter);
        typeEnter(destinationTextInput);
        return this;
    }

    public boolean isAmountOfTravelersCorrect(int amountOfTravelers) {
        String travelersButtonText = getText(travelersButton);
        logger.info("Travelers button says: " + travelersButton);
        return travelersButtonText.contains(String.valueOf(amountOfTravelers));
    }

    public SearchPage selectDepartureDate() {
        LocalDate departureDate = today.plusMonths(2);
        String departureDateText = departureDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        logger.info("Selecting departure day: " + departureDateText);
        click(departureDateButton);
        String departureMonth = departureDate.format(DateTimeFormatter.ofPattern("LLLL uuuu"));
        while (!isElementPresentWithLocator(By.xpath("//*[text()='" + departureMonth + "']"), 2)) {
            click(nextMonthButton);
        }
        click(By.cssSelector("button[aria-label='" + departureDateText + "']"));
        click(doneDatePickerButton);
        return this;
    }

    public SearchPage selectArrivalDate() {
        LocalDate arrivalDate = today.plusMonths(2).plusDays(15);
        String arrivalDateText = arrivalDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        logger.info("Selecting arrival day: " + arrivalDateText);
        click(arrivalDateButton);
        String departureMonth = arrivalDate.format(DateTimeFormatter.ofPattern("LLLL uuuu"));
        while (!isElementPresentWithLocator(By.xpath("//*[text()='" + departureMonth + "']"), 2)) {
            click(nextMonthButton);
        }
        click(By.cssSelector("button[aria-label='" + arrivalDateText + "']"));
        click(doneDatePickerButton);
        return this;
    }

    public void confirmFlightsSearch() {
        click(searchButton);
    }

    public boolean isFlightAddedButtonSelected() {
        return isElementSelected(flightsAddedButton);
    }

    public boolean isStayAddedButtonSelected() {
        return isElementSelected(stayAddedButton);
    }


}
