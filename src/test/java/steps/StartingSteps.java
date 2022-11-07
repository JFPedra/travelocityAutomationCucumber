package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Configuration;

public class StartingSteps extends BaseSteps{

    private final Configuration configuration = new Configuration();
    private final Logger logger = LoggerFactory.getLogger(StartingSteps.class);

    @Before(order = 0)
    public void startDriver() {
        testContext.setDriver(configuration.setUpWebDriver());
        logger.info("Driver started");
    }

    @Before(order = 1)
    public void launchWebPage() {
        testContext.getDriver().get("https://www.travelocity.com");
        logger.info("Page launched");
    }


    @After
    public void endSession() {
        testContext.getDriver().quit();
        logger.info("Session finished");
    }
}
