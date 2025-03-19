package com.yahoo.projects.website.yahoo.pages;

import com.yahoo.utils.LogUtils;
import org.openqa.selenium.By;
import com.yahoo.base.WebUI;

public class BookingPage {

    // Locators for Booking Page
    private final By bookingStepIndicator = By.xpath("//span[contains(text(),'Booking')]");
    private final By passengerFirstName = By.xpath("//input[@id='passengers[0].firstName']");
    private final By passengerLastName = By.xpath("//input[@id='passengers[0].lastName']");
    private final By passengerTitle = By.xpath("//button[@data-testid='apzPicker-passengers[0].title_button-container']");
    private final By dateOfBirth = By.xpath("//input[@id='passengers[0].dob']");
    private final By nationalityDropdown = By.xpath("//button[@data-testid='apzPicker-passengers[0].nationality_button-container']");
    private final By contactFirstName = By.xpath("//input[@id='contact.firstName']");
    private final By contactLastName = By.xpath("//input[@id='contact.lastName']");
    private final By contactEmail = By.xpath("//input[@id='contact.email']");
    private final By contactMobileNumber = By.xpath("//input[@id='contact.mobile']");
    private final By continueButton = By.xpath("//button[@data-testid='flightBooking-checkout-button']");

    /**
     * Method to verify Booking Page is Open
     */
    public boolean isBookingPageOpen() {
        WebUI.waitForElementVisible(bookingStepIndicator);
        return WebUI.isElementVisible(bookingStepIndicator);
    }

    /**
     * Fill Passenger Details
     */
    public void fillPassengerDetails(String title, String firstName, String lastName, String dob, String nationality) {
        LogUtils.info("Filling Passenger Details...");

      /*  // Select Title
        WebUI.clickElement(passengerTitle);
        WebUI.selectDropdownByVisibleText(passengerTitle, title);*/

        // Enter First Name
        WebUI.setText(passengerFirstName, firstName);

        // Enter Last Name
        WebUI.setText(passengerLastName, lastName);

        // Enter Date of Birth
        WebUI.setText(dateOfBirth, dob);

        /*// Select Nationality
        WebUI.clickElement(nationalityDropdown);
        WebUI.selectDropdownByVisibleText(nationalityDropdown, nationality);*/

        LogUtils.info("Passenger Details Filled Successfully.");
    }

    /**
     * Fill Contact Details
     */
    public void fillContactDetails(String firstName, String lastName, String email, String mobileNumber) {
        LogUtils.info("Filling Contact Details...");

        // Enter Contact First Name
        WebUI.setText(contactFirstName, firstName);

        // Enter Contact Last Name
        WebUI.setText(contactLastName, lastName);

        // Enter Email
        WebUI.setText(contactEmail, email);

        // Enter Mobile Number
        WebUI.setText(contactMobileNumber, mobileNumber);

        LogUtils.info("Contact Details Filled Successfully.");
    }

    /**
     * Click on Continue Button
     */
    public void clickContinue() {
        LogUtils.info("Clicking Continue button...");
        WebUI.scrollToElementAtBottom(continueButton);
        WebUI.clickElement(continueButton);
        LogUtils.info("✅ Continue button clicked.");
    }

    /**
     * Complete the Booking Process
     */
    public void completeBooking(String passengerTitle, String passengerFirstName, String passengerLastName,
                                String dob, String nationality,
                                String contactFirstName, String contactLastName, String email, String mobileNumber) {
        if (isBookingPageOpen()) {
            fillPassengerDetails(passengerTitle, passengerFirstName, passengerLastName, dob, nationality);
            fillContactDetails(contactFirstName, contactLastName, email, mobileNumber);
            clickContinue();
            LogUtils.info("✅ Booking process completed.");
        } else {
            LogUtils.error("❌ Booking Page is not open.");
        }
    }
}

