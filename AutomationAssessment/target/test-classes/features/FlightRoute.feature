Feature: Flight Status Verification
  I want to check the flight status on Eurowings website

  Scenario Outline: Verify flight status by route
    Given I open the flight status page
    When I enter "<departure>" as the departure airport
    And I enter "<destination>" as the destination airport
    And I enter "<date>" as the travel date and click Show flight status
    Then I should see the flight status for "<departure>" to "<destination>" on "<date>" with a status that is one of "<statuses>"

    Examples:
    | departure | destination | date       | statuses 				 					|
    | CGN       | BER         | 2024-08-09 | on time, delayed, arrived  |
    | CGN       | BER         | 2024-08-10 | on time, delayed, arrived  |
