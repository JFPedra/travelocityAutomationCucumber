package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchPage extends BasePage{

    private final By flightSectionButton = By.linkText("Flights");
    private final By packageSectionButton = By.linkText("Packages");
    private final By hotelsSectionButton = By.linkText("Stays");
    private final By cruisesSectionButton = By.linkText("Cruises");
    private final By sectionButtonSelected = By.cssSelector("li[role='presentation'][class='uitk-tab active']");
    private final By flightsAddedButton = By.id("package-pills-flights");
    private final By stayAddedButton = By.id("package-pills-hotels");
    private final By originTextSection = By.cssSelector("button[aria-label='Leaving from']");
    private final By destinationTextSection = By.cssSelector("button[aria-label='Going to']");
    private final By originTextInput = By.cssSelector("input[data-stid$='origin-menu-input']");
    private final By destinationTextInput = By.cssSelector("input[data-stid$='destination-menu-input']");
    private final By travelersButton = By.cssSelector("button[data-testid='travelers-field']");
    private final By departureDateButton = By.id("d1-btn");
    private final By arrivalDateButton = By.id("d2-btn");
    private final By accommodationCheckInDateButton = By.id("d1-partial-btn");
    private final By accommodationCheckOutDateButton = By.id("d2-partial-btn");
    private final By nextMonthButton = By.cssSelector("div[class='uitk-calendar'] button:nth-child(2)");
    private final By doneDatePickerButton = By.cssSelector("button[data-stid='apply-date-picker']");
    private final By amountOfAdults = By.id("adult-input-0");
    private final By decreaseAmountOfAdults = By.cssSelector("svg[aria-label^='Decrease adults']");
    private final By increaseAmountOfAdults = By.xpath("svg[aria-label^='Increase adults']");
    private final By searchButton = By.cssSelector("button[data-testid='submit-button']");
    private final By selectAmountOfTravelersSection= By.cssSelector("button[data-testid='travelers-field']");
    private final By doneTravelersButton = By.cssSelector("button[data-testid='guests-done-button']");
    private final By checkboxHotelForPartOfStay = By.id("package-partial-stay");
    private final By errorMessageCheckIn = By.id("d1-partial-error");
    private final By errorMessageCheckOut = By.id("d2-partial-error");
    private final By cruisesDestinationSelectionBox = By.id("cruise-destination");


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

    public SearchPage clickOnHotelsSectionButton() {
        click(hotelsSectionButton);
        return this;
    }

    public SearchPage clickOnCruisesPage() {
        click(cruisesSectionButton);
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

    public SearchPage selectCruisesDestination(String destination) {
        Select destinationBox = new Select(findElementBy(cruisesDestinationSelectionBox));
        destinationBox.selectByVisibleText(destination);
        return this;
    }

    public String getTravelersTex() {
        String travelersButtonText = getText(travelersButton);
        logger.info("Travelers button says: " + travelersButtonText);
        return travelersButtonText;
    }

    public SearchPage selectCruisesDepartureMonth() {
        LocalDate departureEarliestDate = today.plusMonths(1);
        String departureEarliestDateText = departureEarliestDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        LocalDate departureLatestDate = departureEarliestDate.plusMonths(1);
        String departureLatestDateText = departureLatestDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        logger.info("Selecting earliest depart date: " + departureEarliestDateText);
        click(departureDateButton);
        clickNextMonthWhileDesiredMonthIsNoPresent(departureEarliestDate);
        click(By.cssSelector("button[aria-label='" + departureEarliestDateText + "']"));
        click(doneDatePickerButton);
        logger.info("Selecting latest depart date: " + departureLatestDateText);
        click(arrivalDateButton);
        clickNextMonthWhileDesiredMonthIsNoPresent(departureLatestDate);
        click(By.cssSelector("button[aria-label='" + departureLatestDateText + "']"));
        click(doneDatePickerButton);
        return this;
    }

    public LocalDate selectDepartureDate() {
        LocalDate departureDate = today.plusMonths(2);
        String departureDateText = departureDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        logger.info("Selecting departure day: " + departureDateText);
        click(departureDateButton);
        clickNextMonthWhileDesiredMonthIsNoPresent(departureDate);
        click(By.cssSelector("button[aria-label='" + departureDateText + "']"));
        click(doneDatePickerButton);
        return departureDate;
    }

    public LocalDate selectArrivalDate(int tripDurationInDays) {
        LocalDate arrivalDate = today.plusMonths(2).plusDays(tripDurationInDays);
        String arrivalDateText = arrivalDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        logger.info("Selecting arrival day: " + arrivalDateText);
        click(arrivalDateButton);
        clickNextMonthWhileDesiredMonthIsNoPresent(arrivalDate);
        click(By.cssSelector("button[aria-label='" + arrivalDateText + "']"));
        click(doneDatePickerButton);
        return arrivalDate;
    }

    public void selectAccommodationDates(LocalDate departureDate, LocalDate arrivalDate) {
        String checkInDate = departureDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        String checkOutDate = arrivalDate.format(DateTimeFormatter.ofPattern("LLL d, uuuu"));
        logger.info("Selecting accommodation from " + checkInDate + " to " + checkOutDate);
        click(accommodationCheckInDateButton);
        clickNextMonthWhileDesiredMonthIsNoPresent(departureDate);
        selectDesiredDate(checkInDate);
        click(doneDatePickerButton);
        click(accommodationCheckOutDateButton);
        clickNextMonthWhileDesiredMonthIsNoPresent(arrivalDate);
        selectDesiredDate(checkOutDate);
        click(doneDatePickerButton);
    }

    private void clickNextMonthWhileDesiredMonthIsNoPresent(LocalDate desiredDate) {
        String desiredMonth = desiredDate.format(DateTimeFormatter.ofPattern("LLLL uuuu"));
        while (!isElementPresentWithLocator(By.xpath("//*[text()='" + desiredMonth + "']"), 2)) {
            click(nextMonthButton);
        }
    }

    private void selectDesiredDate(String desiredDate) {
        click(By.cssSelector("button[aria-label='" + desiredDate + "']"));
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

    public boolean areAmountOfTravelersCorrect(int amountOfTravelers) {
        String message = amountOfTravelers > 1? " travelers":" traveler";
        return getTravelersTex().contains(amountOfTravelers + message);
    }

    private int getAmountOfAdultsDisplayed() {
        String amountOfAdultsFound = getAttributeOf(amountOfAdults, "value");
        logger.info("Amount of adults: " +  amountOfAdultsFound );
        return Integer.parseInt(amountOfAdultsFound);
    }

    public SearchPage selectAmountOfAdults(int amountOfAdults) {
        logger.info("Travelers section has aria-expanded as" + findElementBy(selectAmountOfTravelersSection).getAttribute("aria-expanded"));
        click(travelersButton);
        logger.info("Travelers section has aria-expanded as" + findElementBy(selectAmountOfTravelersSection).getAttribute("aria-expanded"));
        waitElementAttributeToBe(selectAmountOfTravelersSection, "aria-expanded", "true");
        while (getAmountOfAdultsDisplayed() < amountOfAdults) {
            click(increaseAmountOfAdults);
        }
        while (getAmountOfAdultsDisplayed() > amountOfAdults) {
            click(decreaseAmountOfAdults);
        }
        click(doneTravelersButton);
        return this;
    }

    public void selectAccommodationsForPartCheckbox() {
        logger.info("Selecting checkbox I only need accommodations for part of my trip");
        clickWithoutWait(checkboxHotelForPartOfStay);
    }

    public String getCheckInErrorMessage() {
        return getText(errorMessageCheckIn);
    }

    public String getCheckOutErrorMessage() {
        return getText(errorMessageCheckOut);
    }
}
