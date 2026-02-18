package utils;

import driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import static com.sun.jna.Platform.isMac;

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
        js().executeScript(
                "arguments[0].scrollIntoView({behavior:'instant', block:'center', inline:'center'});",
                el
        );
    }

    public static void scrollToTopOfElement(WebElement el, int offsetPx) {
        js().executeScript(
                "arguments[0].scrollIntoView(true); window.scrollBy(0, -arguments[1]);",
                el, offsetPx
        );
    }

    public static void clearAndType(WebElement el, String text) {
        WebElement e = Waits.visible(el);
        scrollTo(e);

        try {
            e.click();
            Keys modifier = isMac() ? Keys.COMMAND : Keys.CONTROL;

            e.sendKeys(Keys.chord(modifier, "a"));
            e.sendKeys(Keys.DELETE);

            // extra safety for stubborn inputs
            e.sendKeys(Keys.BACK_SPACE);

            // verify cleared
            if (!e.getAttribute("value").isEmpty()) {
                throw new RuntimeException("Field not cleared");
            }

            e.sendKeys(text);
        } catch (Exception ex) {
            // fallback: JS + dispatch events
            jsType(e, "");
            jsType(e, text);
        }
    }
    public static void scrollInContainer(WebElement container, WebElement target) {
        js().executeScript(
                "const c = arguments[0]; const t = arguments[1];" +
                        "const cRect = c.getBoundingClientRect();" +
                        "const tRect = t.getBoundingClientRect();" +
                        "c.scrollTop += (tRect.top - cRect.top) - (cRect.height / 2);",
                container, target
        );
    }

    public static void scrollHorizontally(WebElement container, int pixels) {
        js().executeScript("arguments[0].scrollLeft = arguments[0].scrollLeft + arguments[1];", container, pixels);
    }

    public static void wheelScroll(int deltaY) {
        actions().scrollByAmount(0, deltaY).perform();
    }

    public static void jsClick(WebElement el) {
        js().executeScript("arguments[0].click();", el);
    }

    public static void jsType(WebElement el, String text) {
        js().executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));" +
                        "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));",
                el, text
        );
    }

    public static void highlight(WebElement el) {
        js().executeScript("arguments[0].style.border='2px solid red';", el);
    }

    private static JavascriptExecutor js() {
        return (JavascriptExecutor) DriverManager.getDriver();
    }
}