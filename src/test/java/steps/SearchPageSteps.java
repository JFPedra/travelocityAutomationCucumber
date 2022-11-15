package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.SearchPage;
import runner.TestContext;

public class SearchPageSteps extends BaseSteps{
    public SearchPageSteps(TestContext testContext) {
        super(testContext);
    }
    @Given("the user opens Travelocity at search flights section")
    public void pageAtFlightSection() {
        SearchPage searchPage = new SearchPage(getDriver());
        if(!searchPage.getSectionSelected().equals("Flights")) {
            searchPage.clickOnFlightSectionButton();
        }
    }

    @Then("the user looks for flights from {word} to {word} to {int} adult(s)")
    public void loooksForFlights(String origin, String destiny, int amountOfPeople) {
        System.out.println(origin);
        System.out.println(destiny);
        System.out.println(amountOfPeople);
    }
}
