package com.yahoo.projects.website.yahoo.stepdefinitions;

import com.yahoo.projects.website.yahoo.pages.BookingPage;
import com.yahoo.projects.website.yahoo.pages.ConfirmationPage;
import com.yahoo.projects.website.yahoo.pages.FlightBookingPage;
import com.yahoo.projects.website.yahoo.pages.InsurancePage;
import com.yahoo.utils.LogUtils;
import io.cucumber.java.en.*;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class FlightBookingSteps {

    FlightBookingPage flightBookingPage = new FlightBookingPage();
    BookingPage bookingPage = new BookingPage();
    ConfirmationPage confirmationPage = new ConfirmationPage();
    InsurancePage insurancePage = new InsurancePage();


    SoftAssert softAssert = new SoftAssert();

    @Given("the user opens the airline portal")
    public void openAirlinePortal() {
        LogUtils.info("Opening the Airline Portal...");
        flightBookingPage.openAirlinePortal();
    }

    @When("the user searches for a flight from {string} to {string} departing on {string} and returning on {string}")
    public void searchFlight(String from, String to, String departDate, String returnDate) {
        LogUtils.info("🔍 Searching for flight: From " + from + " to " + to + " Depart: " + departDate + " Return: " + returnDate);

        flightBookingPage.selectFromCity(from);
        flightBookingPage.selectToCity(to);
        flightBookingPage.selectDepartureDate(departDate);
        flightBookingPage.selectReturnDate(returnDate);
        flightBookingPage.clickSearchButton();
        flightBookingPage.extractAndLogAllFlightDetails();

        LogUtils.info("✅ Flight search initiated successfully!");
    }


    @And("the user selects a flight")
    public void selectFlight() throws InterruptedException {
        LogUtils.info("Selecting a flight...");
        flightBookingPage.selectFlight();
    }
    @And("the user enter a booking page details")
    public void enterBookingPageDetails() {
        LogUtils.info("🔹 Verifying Booking Page...");

        LogUtils.info("🔹 Filling Passenger Details...");
        bookingPage.fillPassengerDetails("Mr", "Alaguraj", "Periyasamy", "08/03/2007", "Malaysia");

        // Fill Contact Details
        LogUtils.info("🔹 Filling Contact Details...");
        bookingPage.fillContactDetails("Alaguraj", "Periyasamy", "testerworld301@gmail.com", "1124355716");

        LogUtils.info("✅ Booking Page details entered successfully!");
    }

    @Then("the user clicks on the continue button")
    public void clickContinueButton() {
        LogUtils.info("🔹 Clicking Continue Button...");
        bookingPage.clickContinue();
        LogUtils.info("✅ User successfully clicked Continue.");
    }



    @And("the user gets all details and clicks the confirm button")
    public void theUserGetsAllDetailsAndClicksTheConfirmButton() {
        String firstName = confirmationPage.getFirstName();
        String lastName = confirmationPage.getLastName();
        String email = confirmationPage.getEmail();
        String mobile = confirmationPage.getMobileNumber();

        LogUtils.info("🔹 Booking Details:");
        LogUtils.info("   ✅ First Name: " + firstName);
        LogUtils.info("   ✅ Last Name: " + lastName);
        LogUtils.info("   ✅ Email: " + email);
        LogUtils.info("   ✅ Mobile Number: " + mobile);

        Assert.assertEquals("Alaguraj", firstName);
        Assert.assertEquals("Periyasamy", lastName);
        Assert.assertEquals("testerworld301@gmail.com", email);
        Assert.assertEquals("+60 11 2435 5716", mobile);

        LogUtils.info("🔹 Clicking the Confirm Button...");
        confirmationPage.clickConfirm();
        LogUtils.info("✅ Booking Confirmed Successfully.");
        LogUtils.info("✅ Booking has been successfully completed.");

    }
    @And("the user retrieves all insurance details")
    public void theUserRetrievesAllInsuranceDetails() {

        String title = insurancePage.getInsuranceTitle();
        String description = insurancePage.getInsuranceDescription();
        String termsLink = insurancePage.getInsuranceTermsLink();
        String insuranceCost = insurancePage.getInsurancePrice();
        String totalCost = insurancePage.getTotalPrice();

        // Log Insurance Data
        LogUtils.info("🔹 Insurance Details:");
        LogUtils.info("   ✅ Title: " + title);
        LogUtils.info("   ✅ Description: " + description);
        LogUtils.info("   ✅ Terms & Conditions Link: " + termsLink);
        LogUtils.info("   ✅ Insurance Price: " + insuranceCost);
        LogUtils.info("   ✅ Total Price (After Insurance): " + totalCost);
        LogUtils.info("🔹 Selecting Insurance...");
        insurancePage.selectInsurance();
        LogUtils.info("✅ Insurance Selected Successfully.");
        LogUtils.info("🔹 Declining Insurance...");
        insurancePage.declineInsurance();
        LogUtils.info("✅ Insurance Declined Successfully.");
    }





    @And("the user adds travel insurance {string}")
    public void addTravelInsurance(String insuranceType) {
        LogUtils.info("Adding insurance: " + insuranceType);
        flightBookingPage.addTravelInsurance(insuranceType);
    }

    @Then("the user verifies the selected insurance details")
    public void verifyInsuranceDetails() {
        LogUtils.info("Verifying insurance details...");
        flightBookingPage.verifyInsuranceDetails();
    }

    @And("the user completes the booking")
    public void completeBooking() {
        LogUtils.info("Completing flight booking...");
        flightBookingPage.completeBooking();
    }

    @Then("the user verifies the booking email contains insurance details")
    public void verifyBookingEmail() {
        LogUtils.info("Verifying booking confirmation email...");
        flightBookingPage.verifyBookingEmail();
    }

    @When("the user cancels the insurance")
    public void cancelInsurance() {
        LogUtils.info("Cancelling insurance...");
        flightBookingPage.cancelInsurance();
    }

    @Then("the user verifies that the refund is processed")
    public void verifyRefundProcessed() {
        LogUtils.info("Verifying refund processing...");
        softAssert.assertTrue(flightBookingPage.verifyRefundProcessed(), "❌ Refund not processed correctly!");
        softAssert.assertAll();
    }

    @Then("the user verifies that insurance is not available for the selected flight")
    public void verifyInsuranceNotAvailable() {
        LogUtils.info("Verifying insurance availability...");
        softAssert.assertFalse(flightBookingPage.isInsuranceAvailable(), "❌ Insurance option should NOT be available!");
        softAssert.assertAll();
    }

    @When("the user enters customer age {string}")
    public void enterCustomerAge(String age) {
        LogUtils.info("Entering customer age: " + age);
        softAssert.assertTrue(flightBookingPage.isInsuranceAvailableForAgeGroup(age), "❌ Insurance should be available for this age group!");
        softAssert.assertAll();
    }

    @Then("the user verifies the insurance plan availability for special offers")
    public void verifyInsuranceForSpecialOffers() {
        LogUtils.info("Verifying insurance options for special offers...");
        softAssert.assertTrue(flightBookingPage.verifySpecialOfferDiscount(), "❌ Special offer discount is missing!");
        softAssert.assertAll();
    }

    @Then("the user verifies the correct insurance coverage options")
    public void verifyInsuranceCoverageOptions() {
        LogUtils.info("Verifying insurance coverage options...");
        flightBookingPage.getInsuranceCoverageOptions();
    }
}
