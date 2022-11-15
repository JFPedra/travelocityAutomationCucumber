package steps;

import org.openqa.selenium.WebDriver;
import runner.TestContext;

public class BaseSteps {

 protected TestContext testContext;

 protected void setDriver(WebDriver driver) {
  testContext.setDriver(driver);
 }

 protected WebDriver getDriver() {
  return testContext.getDriver();
 }

 public BaseSteps(TestContext testContext) {
  this.testContext = testContext;
 }
}
