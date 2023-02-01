package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CruisesResultsPage;
import runner.TestContext;

public class CruisesResultsSteps extends BaseSteps {
    public CruisesResultsSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("there is validated that there are results with and without discounts")
    public void validateResultsWithAndWithoutDiscount() {
        CruisesResultsPage cruisesResultsPage = new CruisesResultsPage(getDriver());
        Assert.assertTrue(cruisesResultsPage.doesAnyResultHaveDiscount(), "There is not any result with discount");
        Assert.assertTrue(cruisesResultsPage.doesAnyResultWithoutDiscount(), "All results have discount");
    }
    @When("the result with the best discount is selected")
    public void selectResultWithBestDiscount() {
        CruisesResultsPage cruisesResultsPage = new CruisesResultsPage(getDriver());
        cruisesResultsPage.selectResultWithMaximumDiscount();
    }
}
