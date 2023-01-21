#language:en
Feature: Exploring travelocity webpage

  @FirstExcercise
  Scenario: Begin the process of booking a flight till the complete credit card information page.
    Given the user opens Travelocity at flights section
    When the user looks for flights from LAS to LAX to 1 adult
    And the user selects departure day in two months
    And the user selects arrival day in two months and fifteen days
    Then validate if each result has flight duration
    Then validate if each result has select button, flight details and baggage fees
    When the user selects first departure option
    And the user selects third returning option

  @SecondExcercise
  Scenario: Begin the process of booking a flight with hotel and car.
    Given the user opens Travelocity at package section
    When the user looks for flights from LAS to LAX to 1 adult
    And the user selects departure day in two months
    And the user selects arrival day in two months and fifteen days
    Then validate if each result has flight duration
    Then validate if each result has select button, flight details and baggage fees
    When the user selects first departure option
    And the user selects third returning optio