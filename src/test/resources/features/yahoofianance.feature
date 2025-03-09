@Yahoo
Feature: Yahoo Finance Stock Search and Validation

  Background:
    Given the user navigates to yahoo

  @Tc-001
  Scenario Outline: Verify Stock Search Functionality
    When the user enters "<Stock Symbol>" in the search bar
    Then the user verify the tsla header should appear
    Then the user scroll down
    Then the user verifies the stock page header contains "<Stock Symbol>"
    Then the user verifies the stock price is greater than <Expected Price>
    Then the user captures all stock details
    Examples:
      | Stock Symbol | Expected Price |
      | TSLA         | 200            |
      | AAPL         | 200            |
      | MS           | 100            |
      | SNT.WA       | 200            |
      | AMZN         | 200            |

  @Tc-002
  Scenario Outline: Validate Stock Trend (Up/Down)
    When the user enters "<Stock Symbol>" in the search bar
    Then the user verifies the stock trend is "<Trend>"
    Examples:
      | Stock Symbol | Trend |
      | TSLA         | UP    |
      | AAPL         | UP    |
      | MS           | UP    |
      | SNT.WA       | UP    |
      | AMZN         | DOWN  |


  @Tc-003
  Scenario Outline: Search for an Invalid Stock Symbol
    When the user enters "<Stock Symbol>" in the search bar invalid data
    Then the user verifies an error message is displayed

    Examples:
      | Stock Symbol |
      | INVALID123   |
      | 7567%&^%     |
      | NOEXISTSYMBL |

  @Tc-004
  Scenario Outline: Verify if Market is Open or Closed
    When the user enters "<Stock Symbol>" in the search bar
    Then the user verifies the market status
    Examples:
      | Stock Symbol |
      | TSLA         |
      | AAPL         |
      | MS           |
      | SNT.WA       |
      | AMZN         |

  @Tc-005
  Scenario Outline: Validate Historical Stock Data <timePeriod>
    When the user enters "<Stock Symbol>" in the search bar
    Then the user navigates to historical data section
    Then the user verifies "<Time Period>" data is displayed correctly
    Examples:
      | Stock Symbol | Time Period | timePeriod     |
      | TSLA         | 1_D         | One day        |
      | TSLA         | 5_D         | five day       |
      | TSLA         | 3_M         | three months   |
      | TSLA         | 6_M         | six months     |
      | TSLA         | YTD         | year data      |
      | TSLA         | 1_Y         | one year data  |
      | TSLA         | 5_Y         | five year data |
      | TSLA         | MAX         | max data       |
      | AAPL         | 1_D         | One day        |
      | AAPL         | 5_D         | five day       |
      | AAPL         | 3_M         | three months   |
      | AAPL         | 6_M         | six months     |
      | AAPL         | YTD         | year data      |
      | AAPL         | 1_Y         | one year data  |
      | AAPL         | 5_Y         | five year data |
      | AAPL         | MAX         | max data       |
      | AMZN         | 1_D         | One day        |
      | AMZN         | 5_D         | five day       |
      | AMZN         | 3_M         | three months   |
      | AMZN         | 6_M         | six months     |
      | AMZN         | YTD         | year data      |
      | AMZN         | 1_Y         | one year data  |
      | AMZN         | 5_Y         | five year data |
      | AMZN         | MAX         | max data       |
