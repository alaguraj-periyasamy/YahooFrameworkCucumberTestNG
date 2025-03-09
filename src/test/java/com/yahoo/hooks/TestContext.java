package com.yahoo.hooks;

import com.yahoo.driver.DriverManager;
import com.yahoo.driver.TargetFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class TestContext {

    private WebDriver driver;

    public TestContext() {
        driver = ThreadGuard.protect(new TargetFactory().createInstance());
        driver.manage().window().maximize();
        DriverManager.setDriver(driver);
    }


    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

}
