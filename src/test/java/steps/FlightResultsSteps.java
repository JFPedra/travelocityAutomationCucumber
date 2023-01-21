package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.FlightResultsPage;
import runner.TestContext;

public class FlightResultsSteps extends BaseSteps{
    public FlightResultsSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("validate if each result has flight duration")
    public void validateFlightDuration() {
        FlightResultsPage flightResultsPage = new FlightResultsPage(getDriver());
        Assert.assertTrue(flightResultsPage.areFlightDurationDisplayed(), "Flight duration was not displayed");
    }

    @Then("validate if each result has select button, flight details and baggage fees")
    public void validateEachFlightResult() {
        FlightResultsPage flightResultsPage = new FlightResultsPage(getDriver());
        Assert.assertTrue(flightResultsPage.doFlightsHaveDetails(), "Select button or flight details were missing at one flight");
    }

    @When("the user selects first departure option")
    public void selectFirstDepartureOption() {
        FlightResultsPage flightResultsPage = new FlightResultsPage(getDriver());
        flightResultsPage.selectFirstFlightOption();
    }

    @When("the user selects third returning option")
    public void selectThirdReturningOption() {
        FlightResultsPage flightResultsPage = new FlightResultsPage(getDriver());
        flightResultsPage.selectThirdFlightOption();
    }


    @Then("la pagina se duerme")
    public void ThreadSleep() throws Exception{
        Thread.sleep(40000);
    }
}
