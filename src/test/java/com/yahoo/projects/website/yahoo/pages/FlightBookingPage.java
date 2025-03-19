package com.yahoo.projects.website.yahoo.pages;

import com.yahoo.base.WebUI;
import com.yahoo.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.Map;

public class FlightBookingPage {

    private final String pageUrl = "https://www.airpaz.com/en";  // Update with actual portal URL
    private final SoftAssert softAssert = new SoftAssert();

    private final By fromCitybtn = By.xpath("//input[@placeholder='Origin']");

    private final By fromCityInput=By.xpath("//input[@data-testid='flightAirportPicker-origin-input']");
    private  final By selectfromCity =By.xpath("(//div[@data-testid='autocomplete-perRow'])[1]");
    private final By toCitybtn = By.xpath("//input[@placeholder='Destination']");
    private final By toCityInput=By.xpath("//input[@placeholder='Destination']");
    private final By selecttoCity = By.xpath("(//div[@data-testid='autocomplete-perRow'])[1]");

    private final By departureDate = By.xpath("//div[@data-testid='flightSearchForm-departure-datePicker']");
    private final By returnDate = By.xpath("//div[@data-testid='flightSearchForm-return-datePicker']");
    private final By caldenderdonebtn=By.xpath("//button[@data-testid='done-calendar-button']");
    private final By searchButton = By.xpath("//button[@data-testid='flightSearchForm-searchFlight-button']\n");

    // ✈️ **Flight Selection**
    private final By flightOptions = By.xpath("//button[@data-testid='flight-select-button']");
    private final By selectFlightButton = By.xpath("(//button[@data-testid='flight-select-button'])[1]");
    private final By selectBookButton = By.xpath("(//button[@data-testid='flightFareCard-selectfare-button'])[1]");
    private final By checkoutButton = By.xpath("//button[@data-testid='flightConfirm-checkout-button']");

    private final By checkoutflightdata=By.xpath("(//div[contains(@class, 'flex items-center text-base font-bold')])[1]");


    // 🛡️ **Insurance Options**
    private final By insuranceDropdown = By.id("insurance-options");
    private final By insurancePlans = By.xpath("//div[@class='insurance-plan']");
    private final By insurancePrice = By.id("insurance-price");

    // 🛒 **Checkout & Booking**
    private final By paymentButton = By.id("confirmPayment");
    private final By bookingConfirmation = By.id("booking-confirmation");
    private final By emailReceipt = By.xpath("//div[@class='email-receipt']");

    private final By manageBookings = By.id("manageBookings");
    private final By cancelInsuranceButton = By.id("cancelInsurance");
    private final By refundConfirmation = By.id("refundConfirmation");

    // 🏷️ **Special Offers**
    private final By specialOfferBanner = By.xpath("//div[@class='special-offer']");
    private final By discountPrice = By.id("discounted-insurance-price");

    // ✅ **Methods for Booking a Flight with Insurance**

    public void openAirlinePortal() {
        LogUtils.info("Opening Airline Portal: " + pageUrl);
        WebUI.openWebsite(pageUrl);
    }

    public void selectFromCity(String from) {
        LogUtils.info("Selecting From City: " + from);
        WebUI.clickElement(fromCitybtn);
        WebUI.setText(fromCityInput, from);
        WebUI.waitForElementPresent(selectfromCity);
        WebUI.clickElement(selectfromCity);
    }

    public void selectToCity(String to) {
        LogUtils.info("Selecting To City: " + to);
        WebUI.clickElement(toCitybtn);
        WebUI.setText(toCityInput, to);
        WebUI.waitForElementPresent(selecttoCity);
        WebUI.clickElement(selecttoCity);
    }

    public void selectDepartureDate(String departureDate) {
        LogUtils.info("Selecting Departure Date: " + departureDate);
        WebUI.clickElement(By.xpath("//div[@data-testid='flightSearchForm-departure-datePicker']"));  // Open calendar
      //  selectDateFromCalendar(departureDate);
    }

    public void selectReturnDate(String returnDate) {
        LogUtils.info("Selecting Return Date: " + returnDate);
        WebUI.clickElement(By.xpath("//div[@data-testid='flightSearchForm-return-datePicker']"));  // Open calendar
      //  selectDateFromCalendar(returnDate);
        WebUI.clickElement(caldenderdonebtn);
    }


    private void selectDateFromCalendar(String date) {
        String[] dateParts = date.split("-");
        String year = dateParts[0];
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        String expectedMonthYear = getMonthName(month) + " " + year;
        LogUtils.info("Navigating to Date: " + date);

        // Wait for the calendar to be visible
        WebUI.waitForElementVisible(By.xpath("//div[contains(@class, 'relative flex')]"));

        // Ensure the correct year is displayed
        while (!WebUI.isElementVisible(By.xpath("//div[contains(text(),'" + expectedMonthYear + "')]"))) {
            WebUI.clickElement(By.xpath("//button[@aria-label='Next Month']"));
            WebUI.sleep(1);  // Allow UI update
        }

        // Select the Correct Day
        By dayLocator = By.xpath("//div[@aria-label='" + date + "']//button[contains(@data-testid, 'sharedCalendar-day_" + day + "-inner')]");

        if (WebUI.isElementVisible(dayLocator)) {
            WebUI.clickElement(dayLocator);
            LogUtils.info("✅ Successfully selected date: " + date);
        } else {
            LogUtils.error("❌ Failed to select date: " + date);
            throw new AssertionError("Date selection failed: " + date);
        }
    }


    private String getMonthName(int monthNumber) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[monthNumber - 1];  // Adjust index (0-based)
    }


    public void clickSearchButton() {
        LogUtils.info("Clicking Search Button...");
        WebUI.clickElement(searchButton);
        WebUI.waitForElementVisible(flightOptions);
        LogUtils.info("✅ Flight search results displayed successfully.");
    }
    public void extractAndLogAllFlightDetails() {
        LogUtils.info("🔍 Extracting All Flight Search Results...");

        try {
            // Get all flight result cards dynamically
            List<WebElement> flightCards = WebUI.getElements(By.xpath("//div[contains(@data-testid,'flightResultCard-detail')]"));

            for (int i = 0; i < flightCards.size(); i++) {
                LogUtils.info("🛫 **Flight #" + (i + 1) + " Details:**");

                // ✅ Airline Information
                String airlineName = WebUI.getTextElement(By.xpath("(//div[@data-testid='flightResultCard-airlineName-info']//span)[" + (i + 1) + "]"));
                String cabinClass = WebUI.getTextElement(By.xpath("(//div[@data-testid='flightResultCard-airlineName-info']/div)[1]"));

                // ✅ Departure Information
                String departTime = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[1]/p)[" + (i + 1) + "]"));
                String departAirport = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[1]/div/span[1])[" + (i + 1) + "]"));
                String departTerminal = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[1]/div/span[2])[" + (i + 1) + "]"));

                // ✅ Flight Duration
                String flightDuration = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[2]/div[1])[" + (i + 1) + "]"));
                String flightType = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[2]/div[3])[" + (i + 1) + "]"));

                // ✅ Arrival Information
            //    String arrivalTime = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[3]/p)[" + (i + 1) + "]"));
                String arrivalAirport = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[3]/div/span[1])[" + (i + 1) + "]"));
                String arrivalTerminal = WebUI.getTextElement(By.xpath("(//div[contains(@class,'gap-x-25')]/div[3]/div/span[2])[" + (i + 1) + "]"));

                // ✅ Fare Details
                String farePrice = WebUI.getTextElement(By.xpath("(//div[contains(@class,'text-end')]//span[contains(@class,'font-bold')])[" + (i + 1) + "]"));

                // ✅ Baggage Information
                String baggageInfo = WebUI.getTextElement(By.xpath("(//div[contains(@class,'text-info-dark')])[last()]"));

                // ✅ Log extracted flight details
                LogUtils.info("✈️ **Airline:** " + airlineName + " | Cabin: " + cabinClass);
                LogUtils.info("📍 **Departure:** " + departTime + " | " + departAirport + " " + departTerminal);
                LogUtils.info("⏳ **Duration:** " + flightDuration + " | 🔄 " + flightType);
              //  LogUtils.info("📍 **Arrival:** " + arrivalTime + " | " + arrivalAirport + " " + arrivalTerminal);
                LogUtils.info("💰 **Fare Price:** " + farePrice);
                LogUtils.info("🛄 **Baggage:** " + baggageInfo);
                LogUtils.info("🎟 **Select Flight Button Available:** " + WebUI.isElementVisible(By.xpath("(//button[@data-testid='flight-select-button'])[" + (i + 1) + "]")));

                LogUtils.info("-------------------------------------------------------------");
            }

        } catch (Exception e) {
            LogUtils.error("❌ Error Extracting Flight Search Results: " + e.getMessage());
        }
    }

    public void selectFlight() throws InterruptedException {
        LogUtils.info("Selecting a flight...");
        Thread.sleep(8000);
        WebUI.scrollToPosition(0,200);
        WebUI.waitForElementVisible(selectFlightButton);
        WebUI.clickElement(selectFlightButton);
        Thread.sleep(6000);
        WebUI.scrollToElementAtBottom(selectBookButton);
        WebUI.waitForElementVisible(selectBookButton);
        WebUI.clickElement(selectBookButton);
        Thread.sleep(8000);
        WebUI.waitForElementVisible(selectFlightButton);
        WebUI.clickElement(selectFlightButton);
        Thread.sleep(8000);
        WebUI.waitForElementVisible(selectBookButton);
        WebUI.clickElement(selectBookButton);
        Thread.sleep(8000);
        WebUI.scrollToPosition(400,0);
        WebUI.waitForElementVisible(checkoutButton);
        WebUI.getTextElement(checkoutflightdata);
        extractAndLogFlightDetails();
        extractLogFlightDetails();
        WebUI.clickElement(checkoutButton);
        LogUtils.info("Flight selected successfully.");
    }

    public void extractAndLogFlightDetails() {
        LogUtils.info("🔍 Extracting Flight Details...");

        try {
            String airlineName = WebUI.getTextElement(By.xpath("//p[@data-testid='flightDetail-airline_name-label']"));
            String flightNumber = WebUI.getTextElement(By.xpath("//p[@data-testid='flightDetail-airline_code-label']"));
            String cabinClass = WebUI.getTextElement(By.xpath("//p[@data-testid='flightDetail-airline_cabin-label']"));

            String departTime = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_time-label']"));
            String departDate = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_date-label']"));
            String departCity = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_city-label']"));
            String departAirport = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_airport-label']"));
            String departTerminal = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_terminal-label']"));

            String flightDuration = WebUI.getTextElement(By.xpath("//div[@data-testid='flightTimeline-depart_flight_duration-label']"));

            String arrivalTime = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_time-label']"));
            String arrivalDate = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_date-label']"));
            String arrivalCity = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_city-label']"));
            String arrivalAirport = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_airport-label']"));
            String arrivalTerminal = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_terminal-label']"));

            String cabinBaggage = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_freeCabinBag-label']"));
            String reschedulable = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_rescheduleable-label']"));
            String refundable = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_norefundable-label']"));
            String ticketIssueTime = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_issuedtime-label']"));

            LogUtils.info("✈️ **Flight Details Extracted Successfully**:");
            LogUtils.info("🛫 Airline: " + airlineName + " | Flight Number: " + flightNumber + " | Class: " + cabinClass);
            LogUtils.info("📍 Departure: " + departTime + " on " + departDate + " | " + departCity + " (" + departAirport + ") " + departTerminal);
            LogUtils.info("⏳ Duration: " + flightDuration);
            LogUtils.info("📍 Arrival: " + arrivalTime + " on " + arrivalDate + " | " + arrivalCity + " (" + arrivalAirport + ") " + arrivalTerminal);
            LogUtils.info("🛄 Baggage: " + cabinBaggage);
            LogUtils.info("🔄 Reschedulable: " + reschedulable + " | 💰 Refund Policy: " + refundable);
            LogUtils.info("🎫 Ticket Issue Time: " + ticketIssueTime);

        } catch (Exception e) {
            LogUtils.error("❌ Error Extracting Flight Details: " + e.getMessage());
        }
    }
    public void extractLogFlightDetails() {
        LogUtils.info("🔍 Extracting Flight Details...");

        try {
            String airlineName = WebUI.getTextElement(By.xpath("//p[@data-testid='flightDetail-airline_name-label']"));
            String flightNumber = WebUI.getTextElement(By.xpath("//p[@data-testid='flightDetail-airline_code-label']"));
            String cabinClass = WebUI.getTextElement(By.xpath("//p[@data-testid='flightDetail-airline_cabin-label']"));

            String departTime = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_time-label']"));
            String departDate = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_date-label']"));
            String departCity = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_city-label']"));
            String departAirport = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_airport-label']"));
            String departTerminal = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-depart_terminal-label']"));

            String flightDuration = WebUI.getTextElement(By.xpath("//div[@data-testid='flightTimeline-depart_flight_duration-label']"));

            String arrivalTime = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_time-label']"));
            String arrivalDate = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_date-label']"));
            String arrivalCity = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_city-label']"));
            String arrivalAirport = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_airport-label']"));
            String arrivalTerminal = WebUI.getTextElement(By.xpath("//p[@data-testid='flightTimeline-arrival_terminal-label']"));

            String arrivalDayOffset = "";
            if (WebUI.isElementVisible(By.xpath("//div[@data-testid='flightTimeline-arrival_time_dayoffset-label']"))) {
                arrivalDayOffset = WebUI.getTextElement(By.xpath("//div[@data-testid='flightTimeline-arrival_time_dayoffset-label']"));
            }

            String cabinBaggage = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_freeCabinBag-label']"));
            String reschedulable = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_rescheduleable-label']"));
            String refundable = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_norefundable-label']"));
            String ticketIssueTime = WebUI.getTextElement(By.xpath("//div[@data-testid='flightDetail-facility_issuedtime-label']"));

            LogUtils.info("✈️ **Flight Details Extracted Successfully**:");
            LogUtils.info("🛫 Airline: " + airlineName + " | Flight Number: " + flightNumber + " | Class: " + cabinClass);
            LogUtils.info("📍 Departure: " + departTime + " on " + departDate + " | " + departCity + " (" + departAirport + ") " + departTerminal);
            LogUtils.info("⏳ Duration: " + flightDuration);
            LogUtils.info("📍 Arrival: " + arrivalTime + " " + arrivalDayOffset + " on " + arrivalDate + " | " + arrivalCity + " (" + arrivalAirport + ") " + arrivalTerminal);
            LogUtils.info("🛄 Baggage: " + cabinBaggage);
            LogUtils.info("🔄 Reschedulable: " + reschedulable + " | 💰 Refund Policy: " + refundable);
            LogUtils.info("🎫 Ticket Issue Time: " + ticketIssueTime);

        } catch (Exception e) {
            LogUtils.error("❌ Error Extracting Flight Details: " + e.getMessage());
        }
    }


    public void addTravelInsurance(String planType) {
        LogUtils.info("Adding Travel Insurance Plan: " + planType);
        WebUI.selectDropdownByVisibleText(insuranceDropdown, planType);
        WebUI.waitForElementVisible(insurancePrice);
        LogUtils.info("Insurance plan added successfully.");
    }

    public void verifyInsuranceDetails() {
        String selectedPlan = WebUI.getSelectedDropdownText(insuranceDropdown);
        String price = WebUI.getTextElement(insurancePrice);
        LogUtils.info("Selected Plan: " + selectedPlan + " | Price: " + price);
        softAssert.assertNotNull(selectedPlan, "❌ Insurance plan is not selected!");
        softAssert.assertFalse(price.isEmpty(), "❌ Insurance price is missing!");
        softAssert.assertAll();
    }

    public void completeBooking() {
        LogUtils.info("Completing Flight Booking...");
        WebUI.clickElement(checkoutButton);
        WebUI.clickElement(paymentButton);
        WebUI.waitForElementVisible(bookingConfirmation);
        LogUtils.info("Booking completed successfully.");
    }

    public void verifyBookingEmail() {
        LogUtils.info("Verifying booking email receipt...");
        WebUI.waitForElementVisible(emailReceipt);
        LogUtils.info("✅ Email Receipt Found: " + WebUI.getTextElement(emailReceipt));
    }

    public void cancelInsurance() {
        LogUtils.info("Navigating to Manage Bookings...");
        WebUI.clickElement(manageBookings);
        LogUtils.info("Cancelling Insurance...");
        WebUI.clickElement(cancelInsuranceButton);
        WebUI.waitForElementVisible(refundConfirmation);
        LogUtils.info("Insurance cancellation successful.");
    }

    public boolean verifyRefundProcessed() {
        boolean refundStatus = WebUI.isElementVisible(refundConfirmation);
        softAssert.assertTrue(refundStatus, "❌ Refund not processed!");
        softAssert.assertAll();
        LogUtils.info("✅ Refund processed successfully.");
        return refundStatus;
    }

    public boolean verifyInsurancePriceChange(String expectedPrice) {
        String actualPrice = WebUI.getTextElement(insurancePrice);
        LogUtils.info("Verifying Insurance Price: Expected = " + expectedPrice + " | Actual = " + actualPrice);
        softAssert.assertEquals(actualPrice, expectedPrice, "❌ Insurance price does not match!");
        softAssert.assertAll();
        return actualPrice.equals(expectedPrice);
    }

    public boolean isInsuranceAvailable() {
        boolean available = WebUI.isElementVisible(insuranceDropdown);
        LogUtils.info("Insurance Availability: " + available);
        return available;
    }

    public boolean isInsuranceAvailableForAgeGroup(String age) {
        LogUtils.info("Checking insurance availability for age: " + age);
        WebUI.setText(By.id("customer-age"), age);
        WebUI.waitForElementVisible(insuranceDropdown);
        boolean available = WebUI.isElementVisible(insuranceDropdown);
        LogUtils.info("Insurance availability for age " + age + ": " + available);
        return available;
    }

    public boolean verifySpecialOfferDiscount() {
        boolean offerVisible = WebUI.isElementVisible(specialOfferBanner);
        boolean discountVisible = WebUI.isElementVisible(discountPrice);
        softAssert.assertTrue(offerVisible, "❌ Special offer banner not found!");
        softAssert.assertTrue(discountVisible, "❌ Discounted price not applied!");
        softAssert.assertAll();
        LogUtils.info("✅ Special offer and discount verified.");
        return offerVisible && discountVisible;
    }

    public Map<String, String> getInsuranceCoverageOptions() {
        LogUtils.info("Fetching insurance coverage options...");
        List<String> coverageOptions = WebUI.getListElementsText(insurancePlans);
        Map<String, String> optionsMap = Map.of("CoverageOptions", String.join(", ", coverageOptions));
        LogUtils.info("✅ Insurance Coverage Options: " + optionsMap);
        return optionsMap;
    }
}
