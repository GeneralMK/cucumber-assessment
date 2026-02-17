package utils;

import driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public final class ActionsUtil {

    private ActionsUtil(){}

    private static Actions actions() {
        return new Actions(DriverManager.getDriver());
    }

    public static void hover(WebElement el) {
        actions().moveToElement(el).perform();
    }

    public static void doubleClick(WebElement el) {
        actions().doubleClick(el).perform();
    }

    public static void rightClick(WebElement el) {
        actions().contextClick(el).perform();
    }

    public static void scrollTo(WebElement el) {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    public static void jsClick(WebElement el) {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].click();", el);
    }

    public static void jsType(WebElement el, String text) {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].value=arguments[1];", el, text);
    }

    public static void highlight(WebElement el) {
        ((JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].style.border='2px solid red';", el);
    }
}