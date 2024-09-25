Feature: Handle Invalid flight number input
  
  Scenario Outline: Verify system handles invalid flight number input
    Given I open the flight number page
    When I enter "<flightNumber>" as the Invalid flight number
    And I enter "<date>" as the travel date and click Show flight status by Invalid Flight Number
    Then I should see an error message stating "Unfortunately, we could not find any results for your search."

    Examples:
      | flightNumber | date       |
      | INVALID123   | 2024-08-09 |
      | FAKE456      | 2024-08-10 |
