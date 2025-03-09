package com.yahoo.projects.website.yahoo.pages;

import com.yahoo.base.WebUI;
import com.yahoo.utils.LogUtils;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public class YahooFinancePage {
    SoftAssert softAssert = new SoftAssert();

    private final String pageUrl = "https://finance.yahoo.com/";

    private final By searchBar = By.xpath("//input[@id='ybar-sbq']");
    private final By searchBtn = By.xpath("//button[@type='submit']");
    private final By tslaelement=By.xpath("//div[contains(text(), 'TSLA')]");
    private final By autosuggestDropdown = By.xpath("//ul[@role='listbox']/li");
    private final By firstSuggestion = By.xpath("//ul[@role='listbox']/li[1]");
    private final By tslaHeader = By.xpath("//h1[contains(text(), 'Tesla, Inc.')]");
    private final By stockPrice = By.xpath("//span[@data-testid='qsp-price']");
    private final By previousClose = By.xpath("//fin-streamer[@data-field='regularMarketPreviousClose']");
    private final By volume = By.xpath("//fin-streamer[@data-field='regularMarketVolume']");
    private final By marketCap = By.xpath("//fin-streamer[@data-field='marketCap']");

    private final By open = By.xpath("//fin-streamer[@data-field='regularMarketOpen']");
    private final By week52Range = By.xpath("//fin-streamer[@data-field='fiftyTwoWeekRange']");
    private final By peRatio = By.xpath("//fin-streamer[@data-field='trailingPE']");
    private final By avgVolume = By.xpath("//fin-streamer[@data-field='averageVolume']");


    public void openPage() {
        WebUI.openWebsite(pageUrl);
    }

    public boolean isPageLoaded() {
        return WebUI.verifyElementVisible(searchBar);
    }

    public void searchStock(String stockSymbol) {
        WebUI.setText(searchBar, stockSymbol);
        WebUI.clickElement(searchBtn);
        WebUI.waitForElementPresent(firstSuggestion);
    }

    public boolean isDisplayed() {
        return WebUI.isElementVisible(tslaHeader,10);
    }
    public void scrollDown() {
        WebUI.scrollToPosition(0,400);
        WebUI.takeFullPageScreenshot("homescreen");
    }

    public boolean isTSLADisplayed(){
       return WebUI.isElementDisplayed(tslaHeader);
    }

    public void verifyStockPrice(int expectedPrice) {

        double actualPrice = Double.parseDouble(getStockPrice());
        LogUtils.info("✅ Actual Stock Price: " + actualPrice);
        if (actualPrice > expectedPrice) {
            LogUtils.info("✅ Stock price is greater than " + expectedPrice);
        } else {
            LogUtils.warn("⚠️ Stock price is less than or equal to " + expectedPrice);
        }
        softAssert.assertTrue(actualPrice > expectedPrice, "Stock price is NOT greater than " + expectedPrice);
        softAssert.assertAll();
    }

    public String getFirstSuggestionText() {
        return WebUI.getTextElement(firstSuggestion);
    }

    public void selectFirstSuggestion() {
        WebUI.clickElement(firstSuggestion);
        WebUI.waitForPageLoaded();
    }

    public String getStockPrice() {
        return WebUI.getTextElement(stockPrice);
    }

    public Map<String, String> getAllStockDetails() {
        Map<String, String> stockDetails = new HashMap<>();

        stockDetails.put("Previous Close", WebUI.getTextElement(previousClose));
        stockDetails.put("Volume", WebUI.getTextElement(volume));
        stockDetails.put("AvgVolume", WebUI.getTextElement(avgVolume));

        stockDetails.put("Market Cap", WebUI.getTextElement(marketCap));
        stockDetails.put("Open", WebUI.getTextElement(open));
        stockDetails.put("52 Week Range", WebUI.getTextElement(week52Range));
        stockDetails.put("PE Ratio (TTM)", WebUI.getTextElement(peRatio));

        LogUtils.info("✅ Stock Details: " + stockDetails);
        return stockDetails;
    }




}