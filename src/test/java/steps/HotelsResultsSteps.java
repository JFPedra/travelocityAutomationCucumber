package steps;

import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.HotelsResultsPage;
import runner.TestContext;

public class HotelsResultsSteps extends BaseSteps{
    public HotelsResultsSteps(TestContext testContext) {
        super(testContext);
    }

    @Then("there is validated that the results page contains discount banner")
    public void discountBannerIsDisplayed() {
        HotelsResultsPage hotelsResultsPage = new HotelsResultsPage(getDriver());
        Assert.assertTrue(hotelsResultsPage.isDiscountOptionVisible(), "Discount option wasn't visible");
    }

    @Then("there is validated that sponsored results appear first")
    public void sponsoredResultsAppearFirst() {
        HotelsResultsPage hotelsResultsPage = new HotelsResultsPage(getDriver());
        Assert.assertTrue(hotelsResultsPage.doesTheSponsoredResultsAppearsFirstly(),
                "Not all sponsored results appear first");
    }
}
