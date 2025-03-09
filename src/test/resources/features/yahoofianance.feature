
Feature: Yahoo Finance Stock Search and Validation
@Tc-001
  Scenario Outline: Verify Stock Search Functionality
    Given the user navigates to yahoo
    When the user enters "<Stock Symbol>" in the search bar
    Then the user verify the tsla header should appear
    Then the user scroll down
    Then the user verifies the stock page header contains "<Stock Symbol>"
    Then the user verifies the stock price is greater than <Expected Price>
    Then the user captures all stock details
  Examples:
    | Stock Symbol | Expected Price |
    | TSLA         | 200            |
    | AAPL         | 150            |
    | AMZN         | 2500           |



