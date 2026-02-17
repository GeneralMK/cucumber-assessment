package utils;

import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class Waits {

    private Waits() {}

    private static WebDriverWait waitDriver() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(60));
    }

    // --- By ---
    public static WebElement visible(By by) {
        return waitDriver().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static WebElement clickable(By by) {
        return waitDriver().until(ExpectedConditions.elementToBeClickable(by));
    }

    // --- WebElement ---
    public static WebElement visible(WebElement el) {
        return waitDriver().until(ExpectedConditions.visibilityOf(el));
    }

    public static WebElement clickable(WebElement el) {
        return waitDriver().until(ExpectedConditions.elementToBeClickable(el));
    }

    public static void urlContains(String part) {
        waitDriver().until(ExpectedConditions.urlContains(part));
    }
}