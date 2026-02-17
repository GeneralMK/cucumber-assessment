package base;

import driver.DriverManager;
import utils.Waits;
import org.openqa.selenium.By;

public abstract class Base {

    protected void click(By by) {
        Waits.clickable(by).click();
    }

    protected void type(By by, String text) {
        Waits.visible(by).clear();
        Waits.visible(by).sendKeys(text);
    }

    protected String getText(By by) {
        return Waits.visible(by).getText();
    }

    protected String currentUrl() {
        return DriverManager.getDriver().getCurrentUrl();
    }
}