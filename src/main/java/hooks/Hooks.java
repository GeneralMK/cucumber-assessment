package hooks;

import config.ConfigLoader;
import driver.DriverFactory;
import driver.DriverManager;
import utils.ScreenshotUtil;
import io.cucumber.java.*;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import reporting.ExtentManager;

public class Hooks {
    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        log.info("Starting scenario: {}", scenario.getName());
        DriverFactory.initDriver();

    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] shot = ScreenshotUtil.takeScreenshotBytes();

            // Cucumber attach
            scenario.attach(shot, "image/png", "Failure Screenshot");

            // Allure attach
            Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(shot));
        }

        DriverFactory.quitDriver();
        log.info("Finished scenario: {} | status={}", scenario.getName(), scenario.getStatus());
    }
}