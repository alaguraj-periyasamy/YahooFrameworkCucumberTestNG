package com.yahoo.projects.website.yahoo.stepdefinitions;

import com.yahoo.base.WebUI;
import com.yahoo.projects.website.yahoo.pages.YahooFinancePage;
import com.yahoo.utils.LogUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class YahooSteps {

    private final YahooFinancePage yahooFinancePage = new YahooFinancePage();
    private String stockPrice;

    @Given("the user navigates to yahoo")
    public void userNavigatesToYahoo() {
        LogUtils.info("Navigating to Yahoo Finance...");
        yahooFinancePage.openPage();
        Assert.assertTrue(yahooFinancePage.isPageLoaded(), "Yahoo Finance page did not load successfully!");
    }

    @When("the user enters {string} in the search bar")
    public void userEntersStockSymbol(String stockSymbol) {
        LogUtils.info("Searching for stock: " + stockSymbol);
        yahooFinancePage.searchStock(stockSymbol);
    }

    @Then("the user verify the tsla header should appear")
    public void verifyTSLAAppears() {
        Assert.assertTrue(yahooFinancePage.isDisplayed(), "TSLA did not appear!");
    }

    @Then("the user scroll down")
    public void user_scrolls_down() {
        yahooFinancePage.scrollDown();
    }

    @Then("the user verifies the stock page header contains {string}")
    public void verify_stock_page_header(String stockSymbol) {
        Assert.assertTrue(yahooFinancePage.isTSLADisplayed(), "Stock page did not load correctly!");
    }

    @Then("the user verifies the stock price is greater than {int}")
    public void verify_stock_price(int expectedPrice) {
        yahooFinancePage.verifyStockPrice(expectedPrice);
    }

    @Then("the user captures all stock details")
    public void captureAllStockDetails() {
        Map<String, String> stockDetails = yahooFinancePage.getAllStockDetails();

        for (Map.Entry<String, String> entry : stockDetails.entrySet()) {
            LogUtils.info(entry.getKey() + ": " + entry.getValue());
            Assert.assertNotNull("❌ " + entry.getKey() + " is missing!", entry.getValue());
        }
    }

    @Then("the user verifies the stock trend is {string}")
    public void theUserVerifiesTheStockTrend(String expectedTrend) {
        YahooFinancePage yahooFinancePage = new YahooFinancePage();
        yahooFinancePage.verifyStockTrend(expectedTrend);
    }

    @When("the user enters {string} in the search bar invalid data")
    public void userEntersinvalidStockSymbol(String stockSymbol) {
        LogUtils.info("Searching for stock: " + stockSymbol);
        yahooFinancePage.invalidsearchStock(stockSymbol);
    }
    @Then("the user verifies an error message is displayed")
    public void verifyErrorMessageDisplayed() {

        Assert.assertTrue(yahooFinancePage.isErrorMessageDisplayed(), "❌ Error message is NOT displayed!");
        LogUtils.info("✅ Error message displayed: " + yahooFinancePage.getErrorMessage());
    }


    @Then("the user verifies the market status")
    public void verifyMarketStatus() {

        String marketStatus = yahooFinancePage.getMarketStatus();
        LogUtils.info("✅ Market Status: " + marketStatus);

        Assert.assertNotNull(marketStatus, "❌ Market Status is not found!");
    }

    @When("the user navigates to historical data section")
    public void user_navigates_to_historical_data() {
        yahooFinancePage.navigateToHistoricalData();
    }

    @Then("the user verifies {string} data is displayed correctly")
    public void user_verifies_data_is_displayed_correctly(String timePeriod) {
        yahooFinancePage.selectTimePeriod(timePeriod);
        yahooFinancePage.printHistoricalData();
    }

}
