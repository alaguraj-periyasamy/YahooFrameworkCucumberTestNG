package com.yahoo.projects.website.yahoo.pages;

import org.openqa.selenium.By;
import com.yahoo.base.WebUI;

public class ConfirmationPage {

    // Locators
    private By firstNameText = By.xpath("//div[@data-testid='flightBookView-confirm_passengerFirstname_1-label']");
    private By lastNameText = By.xpath("//div[@data-testid='flightBookView-confirm_passengerLastname_1-label']");
    private By emailText = By.xpath("//div[@data-testid='flightBookView-confirm_contactEmail-label']");
    private By mobileText = By.xpath("//div[@data-testid='flightBookView-confirm_contactNumber-label']");
    private By confirmButton = By.xpath("//button[@data-testid='flightBookView-confirm-button']");

    // Methods
    public String getFirstName() {
        return WebUI.getTextElement(firstNameText);
    }

    public String getLastName() {
        return WebUI.getTextElement(lastNameText);
    }

    public String getEmail() {
        return WebUI.getTextElement(emailText);
    }

    public String getMobileNumber() {
        return WebUI.getTextElement(mobileText);
    }

    public void clickConfirm() {
        WebUI.clickElement(confirmButton);
    }
}
