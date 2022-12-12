package steps;

import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.FlightResultsPage;
import runner.TestContext;

public class FlightResultsSteps extends BaseSteps{
    public FlightResultsSteps(TestContext testContext) {
        super(testContext);
    }

    /**
     * El cuadro que permite ordenar los resultados no aparece en la versión de travelocity que abre el freamwork
     */
    @Then("the sorting options are validated")
    public void validateSortingOptions() {
        FlightResultsPage flightResultsPage = new FlightResultsPage(getDriver());
        Assert.assertTrue(flightResultsPage.areSortingBoxValuesCorrect());
    }

    @Then("validate if each result has select button, flight duration, fligh details and baggege fees")
    public void validateEachFlightResult() {

    }


}
