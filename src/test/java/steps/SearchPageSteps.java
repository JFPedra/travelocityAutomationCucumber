package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.SearchPage;
import runner.TestContext;

public class SearchPageSteps extends BaseSteps{

    private SearchPage searchPage;
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
        searchPage.selectArrivalDate(15).confirmFlightsSearch();
    }

    @When("the user selects dates for a trip of 13 days")
    public void selectDatesForA13daysTrip() {
        searchPage = new SearchPage(getDriver());
        searchPage.selectDepartureDate();
        searchPage.selectArrivalDate(13).confirmFlightsSearch();
    }

    @When("the user enters {string} as destination")
    public void userEnteresDestination(String destination) {
        searchPage = new SearchPage(getDriver());
        searchPage.enterDestination(destination);
    }


    @Then("there is validated that stay added and flights added are selected")
    public void validatesStayAndFlightsAdded() {
        searchPage = new SearchPage(getDriver());
        Assert.assertTrue(searchPage.isFlightAddedButtonSelected(), "Flights added button wasn't selected");
        Assert.assertTrue(searchPage.isStayAddedButtonSelected(), "Stay added button wasn't selected");
    }
}
