package com.yahoo.projects.website.yahoo.pages;

import com.yahoo.base.WebUI;
import com.yahoo.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YahooFinancePage {
    SoftAssert softAssert = new SoftAssert();

    private final String pageUrl = "https://finance.yahoo.com/";

    private final By searchBar = By.xpath("//input[@id='ybar-sbq']");
    private final By searchBtn = By.xpath("//button[@type='submit']");
    private final By tslaelement = By.xpath("//div[contains(text(), 'TSLA')]");
    private final By autosuggestDropdown = By.xpath("//ul[@role='listbox']/li");
    private final By firstSuggestion = By.xpath("//ul[@role='listbox']/li[1]");
    private final By tslaHeader = By.xpath("//section[contains(@class, 'container')]//h1");
    private final By stockPrice = By.xpath("//span[@data-testid='qsp-price']");
    private final By previousClose = By.xpath("//fin-streamer[@data-field='regularMarketPreviousClose']");
    private final By volume = By.xpath("//fin-streamer[@data-field='regularMarketVolume']");
    private final By marketCap = By.xpath("//fin-streamer[@data-field='marketCap']");

    private final By open = By.xpath("//fin-streamer[@data-field='regularMarketOpen']");
    private final By week52Range = By.xpath("//fin-streamer[@data-field='fiftyTwoWeekRange']");
    private final By peRatio = By.xpath("//fin-streamer[@data-field='trailingPE']");
    private final By avgVolume = By.xpath("//fin-streamer[@data-field='averageVolume']");
    private final By stockTrend = By.xpath("//span[@data-testid='qsp-price-change']");

    private final By errorMessage = By.xpath("//p[contains(text(),'No results for')]");

    private final By marketCloseTimeLocator = By.xpath("(//div[@slot='marketTimeNotice']|//span[contains(text(),'After hours:')])[1]");
    private final By afterHoursTimeLocator = By.xpath("(//div[@slot='marketTimeNotice']|//span[contains(text(),'After hours:')])[2]");
    private final By historicalData = By.xpath("//a[contains(@category, 'history') and text()]");
    private final By calenderBtn = By.xpath("(//button[contains(@class, 'menuBtn')]/span)[2]");
    private final By timePeriodButtons = By.xpath("//button[@value]");
    private final By historicaltanleheaderdata = By.xpath("//table[contains(@class, 'table yf-1jecxey')]/thead/tr");
    private final By historicaltableallrowdata = By.xpath("//table[contains(@class, 'table yf-1jecxey')]/tbody/tr");


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

    public void invalidsearchStock(String stockSymbol) {
        WebUI.setText(searchBar, stockSymbol);
        WebUI.clickElement(searchBtn);

    }

    public boolean isDisplayed() {
        return WebUI.isElementVisible(tslaHeader, 10);
    }

    public void scrollDown() {
        WebUI.scrollToPosition(0, 400);
        WebUI.takeFullPageScreenshot("homescreen");
    }

    public boolean isTSLADisplayed() {
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

    public void verifyStockTrend(String expectedTrend) {
        String trendValue = WebUI.getTextElement(stockTrend).trim();
        String actualTrend = trendValue.contains("-") ? "Down" : "Up";  // If the value is negative, it's "Down", else "Up"

        LogUtils.info("✅ Expected Trend: " + expectedTrend);
        LogUtils.info("✅ Actual Trend: " + actualTrend);

        softAssert.assertEquals(actualTrend, expectedTrend, "Stock trend does NOT match!");
    }

    public boolean isErrorMessageDisplayed() {
        return WebUI.isElementDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return WebUI.getTextElement(errorMessage);
    }

    public String getMarketStatus() {
        // Get elements as Strings
        String marketCloseElements = WebUI.getTextElement(marketCloseTimeLocator);
        String afterHoursElements = WebUI.getTextElement(afterHoursTimeLocator);

        LogUtils.info("✅ Market close status Status: " + marketCloseElements + " Market Open Status : " + afterHoursElements);

        // Extract Time Text
        String marketCloseText = (marketCloseElements != null) ? marketCloseElements : "";
        String afterHoursText = (afterHoursElements != null) ? afterHoursElements : "";

        // Extract Time from Text
        String marketCloseTime = extractTime(marketCloseText);
        String afterHoursTime = extractTime(afterHoursText);

        // Get Current Time
        LocalTime currentTime = LocalTime.now();

        // Convert Extracted Time to LocalTime
        LocalTime closeTime = convertToTime(marketCloseTime);
        LocalTime afterTime = convertToTime(afterHoursTime);

        // Validate Parsed Time (Ensure it's not MIN)
        if (closeTime.equals(LocalTime.MIN) || afterTime.equals(LocalTime.MIN)) {
            LogUtils.info("❌ Invalid Market Close or After Hours Time. Market status cannot be determined.");
            return "Market status UNKNOWN";
        }

        // Determine Market Status
        if (currentTime.isBefore(closeTime)) {
            return "Market is OPEN";
        } else if (currentTime.isAfter(closeTime) && currentTime.isBefore(afterTime)) {
            return "Market is in AFTER HOURS trading";
        } else {
            return "Market is CLOSED";
        }
    }


    private String extractTime(String text) {
        Pattern pattern = Pattern.compile("(\\d{1,2}:\\d{2}:\\d{2} (AM|PM))");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : "";
    }


    private LocalTime convertToTime(String time) {
        try {
            if (time == null || time.isEmpty()) {
                LogUtils.info("❌ Invalid Time String: " + time);
                return LocalTime.MIN;  // Default to a minimum valid time
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH);
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            LogUtils.info("❌ Error Parsing Time: " + time);
            return LocalTime.MIN;  // Return a default value instead of null
        }
    }

    public void navigateToHistoricalData() {
        WebUI.clickElement(historicalData);
        WebUI.waitForPageLoaded();
        WebUI.scrollToPosition(0, 300);

    }

    public void selectTimePeriod(String timePeriod) {
        WebUI.clickElement(calenderBtn);
        By timePeriodBtn = By.xpath("//button[@value='" + timePeriod + "']");
        WebUI.clickElement(timePeriodBtn);
        WebUI.waitForPageLoaded();
        WebUI.scrollToPosition(0, 100);
    }

    public void printHistoricalData() {
        LogUtils.info("Fetching Historical Stock Data...");

        List<String> headers = WebUI.getListElementsText(historicaltanleheaderdata);
        LogUtils.info("Table Headers: " + headers);
        List<String> rowData = WebUI.getListElementsText(historicaltableallrowdata);
        LogUtils.info("Historical Data: ");
        for (String row : rowData) {
            LogUtils.info(row);
        }
    }

}