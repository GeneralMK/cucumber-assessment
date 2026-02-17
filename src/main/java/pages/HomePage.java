package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Waits;

public class HomePage extends BasePage {

    private final By breadcrumbModule = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    private final By pimLeftMenu = By.xpath("//span[contains(@class,'oxd-main-menu-item--name') and normalize-space()='PIM']");

    public boolean isAt() {
        Waits.urlContains("/dashboard");
        String text = Waits.visible(breadcrumbModule).getText().trim();
        return text.equalsIgnoreCase("Dashboard");
    }

    public void clickPIM() {
        Waits.clickable(pimLeftMenu).click();
    }
}