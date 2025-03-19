@FlightInsurance
Feature: Flight Booking and Travel Insurance Validation

  Background:
    Given the user opens the airline portal

  @Tc-001
  Scenario Outline: Verify Insurance Selection in Flight Booking <Airline>
    When the user searches for a flight from "<From City>" to "<To City>" departing on "<Departure Date>" and returning on "<Return Date>"
    And the user selects a flight
    And  the user enter a booking page details
    Then the user clicks on the continue button
    And the user gets all details and clicks the confirm button
    And the user retrieves all insurance details
    Examples:
      | From City | To City | Departure Date | Return Date | Airline |
      | KUL       | JKTA    | 2025-06-01     | 2025-06-10  | airpaz  |
      | KUL       | BKK     | 2025-07-05     | 2025-07-12  | airpaz  |
      | KUL       | SINA    | 2025-08-15     | 2025-08-25  | airpaz  |

  @Tc-002
  Scenario: Verify Insurance Price Calculation
    When the user selects a flight
    And the user adds travel insurance "Standard Plan"
    Then the user verifies the selected insurance details

  @Tc-003
  Scenario: Verify Insurance Cancellation and Refund Process
    When the user searches for a flight from "JFK" to "LAX" departing on "2025-07-15" and returning on "2025-07-20"
    And the user selects a flight
    And the user adds travel insurance "Basic Plan"
    And the user completes the booking
    When the user cancels the insurance
    Then the user verifies that the refund is processed

  @Tc-004
  Scenario: Verify Insurance Not Available for Certain Flights
    When the user searches for a flight from "SIN" to "NYC" departing on "2025-08-05" and returning on "2025-08-15"
    Then the user verifies that insurance is not available for the selected flight

  @Tc-005
  Scenario: Verify Insurance for Different Age Groups
    When the user enters customer age "25"
    Then the user verifies the selected insurance details
    When the user enters customer age "65"
    Then the user verifies the selected insurance details

  @Tc-006
  Scenario: Verify Insurance Plan Availability and Special Offers
    When the user searches for a flight from "LON" to "DXB" departing on "2025-09-10" and returning on "2025-09-20"
    Then the user verifies the insurance plan availability for special offers

  @Tc-007
  Scenario: Verify Insurance Linked to the Correct Flight
    When the user searches for a flight from "SYD" to "BKK" departing on "2025-10-15" and returning on "2025-10-25"
    Then the user verifies the selected insurance details

  @Tc-008
  Scenario: Verify Refund Process After Cancellation
    When the user searches for a flight from "DEL" to "SFO" departing on "2025-11-05" and returning on "2025-11-15"
    And the user selects a flight
    And the user adds travel insurance "Gold Plan"
    And the user completes the booking
    When the user cancels the insurance
    Then the user verifies that the refund is processed

  @Tc-009
  Scenario: Verify Insurance Coverage Options
    When the user searches for a flight from "CDG" to "YYZ" departing on "2025-12-01" and returning on "2025-12-10"
    Then the user verifies the correct insurance coverage options
