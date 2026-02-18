package utils;

import driver.DriverManager;
import org.openqa.selenium.*;
import java.util.function.Function;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class Waits {

    private Waits() {}

    private static WebDriverWait waitDriver() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(60));
    }

    public static WebElement visible(By by) {
        return waitDriver().until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static WebElement clickable(By by) {
        return waitDriver().until(ExpectedConditions.elementToBeClickable(by));
    }

    public static WebElement visible(WebElement el) {
        return waitDriver().until(ExpectedConditions.visibilityOf(el));
    }

    public static void waitUntil(Function<WebDriver, Boolean> condition, int seconds) {
        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds))
                .until(condition);
    }

    public static WebElement clickable(WebElement el) {
        return waitDriver().until(ExpectedConditions.elementToBeClickable(el));
    }

    public static void urlContains(String part) {
        waitDriver().until(ExpectedConditions.urlContains(part));
    }

    public static void invisible(By by) {
        waitDriver().until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void jsClick(WebElement el) {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", el);
    }

    public static void jsSetValue(WebElement el, String value) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                el, value
        );
    }
}