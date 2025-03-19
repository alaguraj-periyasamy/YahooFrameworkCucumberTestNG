package com.yahoo.projects.website.yahoo.pages;

import org.openqa.selenium.By;
import com.yahoo.base.WebUI;
public class InsurancePage {

    // Locators for Insurance Page
    private By insuranceSection = By.xpath("//div[@id='addOns-protection-info']");
    private By insuranceTitle = By.xpath("//div[contains(text(),'Flight Protection')]");
    private By insuranceDescription = By.xpath("//div[contains(text(),'Travel Protection')]");
    private By insuranceTermsLink = By.xpath("//a[contains(text(),'View terms')]");
    private By insuranceOptionYes = By.xpath("//input[@type='radio' and @value='true']");
    private By insuranceOptionNo = By.xpath("//input[@type='radio' and @value='false']");
    private By insurancePrice = By.xpath("//div[contains(text(),'Comprehensive Travel Protection')]//following-sibling::div");
    private By totalPrice = By.xpath("//div[@data-testid='flightPricePanel-priceTotal-container']//p[contains(@class,'font-bold')]");

    // Methods to extract details
    public String getInsuranceTitle() {
        return WebUI.getTextElement(insuranceTitle);
    }

    public String getInsuranceDescription() {
        return WebUI.getTextElement(insuranceDescription);
    }

    public String getInsuranceTermsLink() {
        return WebUI.getAttributeElement(insuranceTermsLink, "href");
    }

    public String getInsurancePrice() {
        return WebUI.getTextElement(insurancePrice);
    }

    public String getTotalPrice() {
        return WebUI.getTextElement(totalPrice);
    }

    public boolean isInsuranceSectionDisplayed() {
        return WebUI.isElementDisplayed(insuranceSection);
    }

    public void selectInsurance() {
        WebUI.clickElement(insuranceOptionYes);
    }

    public void declineInsurance() {
        WebUI.clickElement(insuranceOptionNo);
    }
}
