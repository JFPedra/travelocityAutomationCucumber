#language:en
Feature: Exploring travelocity webpage

  @FirstExercise
  Scenario: Begin the process of booking a flight till the complete credit card information page.
    Given the user opens Travelocity at flights section
    When the user looks for flights from LAS to LAX to 1 adult
    And the user selects departure day in two months
    And the user selects arrival day in two months and fifteen days
    And the user confirms search parameters
    Then validate if each result has flight duration
    Then validate if each result has select button, flight details and baggage fees
    When the user selects first departure option
    And the user selects third returning option

  @SecondExercise
  Scenario: Begin the process of booking a flight with hotel and car.
    Given the user opens Travelocity at packages section
    Then there is validated that stay added and flights added are selected
    When the user looks for flights from LAS to LAX to 1 adult
    And the user selects dates for a trip of 13 days
    And the user confirms search parameters
    Then there is validated that results summary and sidebar map are displayed
    And there is validated that each result has price displayed
    When the user selects the first result with review greater than 8.5
    And la pagina se duerme

  @ThirdExercise
  Scenario: Verify that search by hotel name works properly.
    Given the user opens Travelocity at hotels section
    When the user enters "Montevideo, Uruguay" as destination
    And the user selects dates for a trip of 13 days
    And the user confirms search parameters
    Then there is validated that the results page contains discount banner
    And there is validated that sponsored results appear first
    And la pagina se duerme

    @FourthExercise
    Scenario: Verify that the errr message displayed when looking for a hotel in incorrect dates is correct
      Given the user opens Travelocity at packages section
      When the user looks for flights from LAS to LAX to 1 adult
      And the user selects dates for a trip of 13 days
      And the user selects checkbox I only need accommodations for part of my trip
      And the user selects dates that are not included in the period of the flight
      And the user confirms search parameters
      Then there are validated error messages for wrong accommodation dates
      And la pagina se duerme