package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.SearchPage;
import runner.TestContext;

import java.time.LocalDate;

public class SearchPageSteps extends BaseSteps{

    private SearchPage searchPage;
    public final String arrivalDateKey = "arrivalDate";
    private final String departureDateKey = "departureDate";
    public SearchPageSteps(TestContext testContext) {
        super(testContext);
    }
    @Given("the user opens Travelocity at {word} section")
    public void pageAtSection(String section) {
        searchPage = new SearchPage(getDriver());
        switch (section) {
            case "flights":
                if(!searchPage.getSectionSelected().equals("Flights"))
                    searchPage.clickOnFlightSectionButton();
                break;
            case "packages":
                if(!searchPage.getSectionSelected().equals("Packages"))
                    searchPage.clickOnPackagesSectionButton();
                break;
            case "hotels":
                if(!searchPage.getSectionSelected().equals("Stays"))
                    searchPage.clickOnHotelsSectionButton();
                break;
            default:
                throw new IllegalArgumentException();
        }
        setDriver(searchPage.getDriver());
    }

    @When("the user looks for flights from {word} to {word} to {int} adult(s)")
    public void lookForFlights(String origin, String destination, int amountOfPeople) {
        searchPage = new SearchPage(getDriver());
        searchPage.enterOrigin(origin);
        searchPage.enterDestination(destination);
        if(!searchPage.areAmountOfTravelersCorrect(amountOfPeople)) {
            searchPage.selectAmountOfAdults(amountOfPeople);
        }
    }

    @When("the user selects departure day in two months")
    public void selectDepartureInTwoMonths() {
        searchPage = new SearchPage(getDriver());
        searchPage.selectDepartureDate();
    }

    @When("the user selects arrival day in two months and fifteen days")
    public void selectArrivalInTwoMonthsAndFifteenDays() {
        searchPage = new SearchPage(getDriver());
        searchPage.selectArrivalDate(15);
    }

    @When("the user selects dates for a trip of 13 days")
    public void selectDatesForA13daysTrip() {
        searchPage = new SearchPage(getDriver());
        testContext.addToDataSharingMap(departureDateKey, searchPage.selectDepartureDate());
        testContext.addToDataSharingMap(arrivalDateKey, searchPage.selectArrivalDate(13));
    }

    @When("the user selects checkbox I only need accommodations for part of my trip")
    public void selectCheckboxAccommodationsForPart() {
        searchPage = new SearchPage(getDriver());
        searchPage.selectAccommodationsForPartCheckbox();
    }
    @When("the user enters {string} as destination")
    public void userEnteresDestination(String destination) {
        searchPage = new SearchPage(getDriver());
        searchPage.enterDestination(destination);
    }

    @When("the user selects dates that are not included in the period of the flight")
    public void selectDatesNotIncludedInThePeriodOfTheFlight() {
        searchPage = new SearchPage(getDriver());
        LocalDate checkInDate = (LocalDate) testContext.getObjectDataSharingMap(departureDateKey);
        LocalDate checkOutDate = (LocalDate) testContext.getObjectDataSharingMap(arrivalDateKey);
        searchPage.selectAccommodationDates(checkInDate.plusDays(14), checkOutDate.plusDays(12));
    }
    @Then("there is validated that stay added and flights added are selected")
    public void validatesStayAndFlightsAdded() {
        searchPage = new SearchPage(getDriver());
        Assert.assertTrue(searchPage.isFlightAddedButtonSelected(), "Flights added button wasn't selected");
        Assert.assertTrue(searchPage.isStayAddedButtonSelected(), "Stay added button wasn't selected");
    }

    @When("the user confirms search parameters")
    public void confirmFlightParameters() {
        searchPage = new SearchPage(getDriver());
        searchPage.confirmFlightsSearch();
    }

    @Then("there are validated error messages for wrong accommodation dates")
    public void validateCheckInAndCheckOutErrorMessages() {
        searchPage = new SearchPage(getDriver());
        Assert.assertEquals(searchPage.getCheckInErrorMessage(),
                "Your check-in date must fall within your departing and returning dates",
                "Check In error message was wrong");
        Assert.assertEquals(searchPage.getCheckOutErrorMessage(),
                "Your check-out date must fall within your departing and returning dates",
                "Check Out error message was wrong");
    }
}
