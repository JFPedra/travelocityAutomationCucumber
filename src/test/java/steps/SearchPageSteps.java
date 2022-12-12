package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import pages.SearchPage;
import runner.TestContext;

public class SearchPageSteps extends BaseSteps{

    private SearchPage searchPage;
    public SearchPageSteps(TestContext testContext) {
        super(testContext);
    }
    @Given("the user opens Travelocity at search flights section")
    public void pageAtFlightSection() {
        searchPage = new SearchPage(getDriver());
        if(!searchPage.getSectionSelected().equals("Flights")) {
            searchPage.clickOnFlightSectionButton();
        }
        setDriver(searchPage.getDriver());
    }

    @When("the user looks for flights from {word} to {word} to {int} adult(s)")
    public void lookForFlights(String origin, String destination, int amountOfPeople) {
        searchPage = new SearchPage(getDriver());
        searchPage.enterOrigin(origin);
        searchPage.enterDestination(destination);
        if(!searchPage.isAmountOfTravelersCorrect(amountOfPeople)) {
            /*TODO implement logic to select amount of people*/
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
        searchPage.selectArrivalDate().confirmFlightsSearch();
    }
}
