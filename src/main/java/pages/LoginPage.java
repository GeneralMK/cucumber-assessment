package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Waits;

public class LoginPage extends BasePage {

    @FindBy(name = "username")
    private WebElement username;

    @FindBy(name = "password")
    private WebElement password;

    // button text: "Login"
    private final By loginBtn = By.cssSelector("button[type='submit']");

    private final By requiredMessages = By.cssSelector("span.oxd-input-field-error-message");
    private final By invalidCreds = By.cssSelector("p.oxd-alert-content-text");

    public boolean isAt() {
        Waits.urlContains("/auth/login");
        Waits.visible(By.name("username"));
        return true;
    }

    public void clearUsername() {
        Waits.visible(By.name("username")).clear();
    }

    public void typeUsername(String value) {
        Waits.visible(By.name("username")).sendKeys(value);
    }

    public void clearPassword() {
        Waits.visible(By.name("password")).clear();
    }

    public void typePassword(String value) {
        Waits.visible(By.name("password")).sendKeys(value);
    }

    public void clickLogin() {
        Waits.clickable(loginBtn).click();
    }

    public void login(String user, String pass) {
        typeUsername(user);
        typePassword(pass);
        clickLogin();
    }

    // NOTE:
    // If both fields show "Required", OrangeHRM renders 2 spans.
    // We'll fetch by index safely in steps.
    public String getAnyRequiredMessage() {
        return Waits.visible(requiredMessages).getText().trim();
    }

    public String getInvalidCredentialsMessage() {
        return Waits.visible(invalidCreds).getText().trim();
    }
}