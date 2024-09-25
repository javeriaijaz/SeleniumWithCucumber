Feature: Verify flight status retrieval for past dates

  Scenario Outline: Verify that the user can only retrieve flight status data for past dates
    Given I open the flight status page for past dates
    When I enter "<date>" as the travel date for past dates
    Then I should see an error message for past dates stating "<expectedMessage>"

  Examples:
    | date       | expectedMessage                                 |
    | 2024-08-02 | Unfortunately there are no flights available.  |
    | 2024-08-01 | Unfortunately there are no flights available.  |
