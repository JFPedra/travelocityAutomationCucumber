package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.SearchPage;

public class ExampleStep extends BaseSteps{
    @Given("the user opens the browser")
    public void the_user_opens_the_browser() {
        SearchPage searchPage = new SearchPage(testContext.getDriver());
        searchPage.saySomething();
    }
    @Then("the user says hello")
    public void the_user_says_hello() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
