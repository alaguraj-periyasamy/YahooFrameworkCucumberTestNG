package com.yahoo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
        features = "src/test/resources/features/insurance.feature",
        glue = {
                "com.yahoo.projects.website.yahoo.stepdefinitions",
                "com.yahoo.hooks"
        },
        plugin = {
                "com.yahoo.hooks.CucumberListener",
                "pretty",
                "html:target/cucumber-reports/TestRunnerYahoo.html",
                "json:target/cucumber-reports/TestRunnerYahoo.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true,
        tags = "@Tc-001"
)
public class TestRunnerInsurance extends AbstractTestNGCucumberTests{

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
