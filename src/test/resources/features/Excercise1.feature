#language:en
Feature: First exercise of Web Automation Testing Exam

  Scenario: Just an test Escenario
    Given the user opens Travelocity at search flights section
    When the user looks for flights from LAS to LAX to 1 adult
    And the user selects departure day in two months
    And the user selects arrival day in two months and fifteen days
    Then the sorting options are validated