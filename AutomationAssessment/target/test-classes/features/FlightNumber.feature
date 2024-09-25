Feature: Flight Status Verification by Flight Number
  I want to verify the flight status on Eurowings website using the flight number.

  Scenario Outline: Verify flight status by flight number
    Given I open the flight number status page
    When I enter "<flightNumber>" as the flight number
    And I enter "<date>" as the travel date and click Show flight status by flight number
		Then I should see the flight status by flight number for "<departure>" to "<destination>" on "<date>" with a status that is one of "<statuses>"

    Examples:
      | flightNumber | date         | departure | destination  | statuses                  |
      | EW12         | 2024-08-09   | CGN       | BER          | on time, delayed, arrived |
      | EW6          | 2024-08-10   | CGN       | BER          | on time, delayed, arrived |
