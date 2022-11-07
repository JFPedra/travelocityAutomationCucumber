package pages;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchPage extends BasePage{

    private final Logger logger = LoggerFactory.getLogger(SearchPage.class);
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void saySomething() {
        logger.warn("Holi, funcioné");
    }

}
