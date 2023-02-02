
# Travelocity - Test Automation

This automation framework uses java, selenium, cucumber, TestNG, and maven in order to test some features of the Travelocity web page. It is just a simple exercise to practice automation skills.

Given the web page detects that it is being used by an automation tool, not all features works normally and some captcha appears suddenly making the Test cases failed



## Running Tests

To run tests, run the following commands

```bash
  mvn clean test
  mvn clean test -Dcucumber.filter.tags="@ThirdExercise"
```


## Requirements
- Java 8 or greater
- Apache Maven 3.8.1  or greater