package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.PackagesResultsPage;
import runner.TestContext;

import java.util.List;

public class PackageResultsSteps extends BaseSteps{
    public PackageResultsSteps(TestContext testContext) {
        super(testContext);
    }

    @When("the user selects the first result with review greater than {float}")
    public void selectFirstResultWithReviewGreaterThan(float minimumReview) {
        PackagesResultsPage packagesResultsPage = new PackagesResultsPage(testContext.getDriver());
        WebElement resultFound = packagesResultsPage.findFirstResultGraterThan(minimumReview);
        packagesResultsPage.selectResult(resultFound);
    }

    @Then("there is validated that results summary and sidebar map are displayed")
    public void validatesResultsSummaryAndSidebarMap() {
        PackagesResultsPage packagesResultsPage = new PackagesResultsPage(testContext.getDriver());
        Assert.assertTrue(packagesResultsPage.isSidebarMapDisplayed());
        Assert.assertTrue(packagesResultsPage.isHeaderSummaryDisplayed());

    }

    @Then("there is validated that each result has price displayed")
    public void validateEachResult() {
        PackagesResultsPage packagesResultsPage = new PackagesResultsPage(testContext.getDriver());
        List<WebElement> packagesResults = packagesResultsPage.getPackageResults();
        int resultNumber = 1;
        for (WebElement result: packagesResults) {
            Assert.assertTrue(packagesResultsPage.isPriceDisplayedForResult(result, resultNumber),
                    "Price wasn't present at result " + resultNumber);
            resultNumber++;
        }
    }
}
