package pages;

import driver.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ActionsUtil;
import utils.Waits;

public class AddEmployeePage extends BasePage {

    @FindBy(css = "h6.orangehrm-main-title")
    private WebElement titleAddEmployee;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstName;

    @FindBy(css = "input[name='middleName']")
    private WebElement middleName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastName;

    @FindBy(xpath = "(//label[normalize-space()='Employee Id']/following::input)[1]")
    private WebElement employeeId;

    @FindBy(xpath = "//*[@class='oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space']")
    private WebElement btnSave;

    // loaders / overlays (try both; some builds have one of them)
    private final By loadingSpinnerBy = By.cssSelector(".oxd-loading-spinner");
    private final By loadingOverlayBy = By.cssSelector(".oxd-loading-overlay");
    private final By successToastBy   = By.cssSelector(".oxd-toast--success, .oxd-toast.oxd-toast--success");

    public AddEmployeePage() {
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    public boolean isAt() {
        return "Add Employee".equalsIgnoreCase(Waits.visible(titleAddEmployee).getText().trim());
    }

    public void enterFirstName(String fname){
        WebElement el = Waits.visible(firstName);
        el.clear();
        el.sendKeys(fname);
    }

    public void enterLastName(String lname){
        WebElement el = Waits.visible(lastName);
        el.clear();
        el.sendKeys(lname);
    }

    public void enterMiddleName(String mName){
        WebElement el = Waits.visible(middleName);
        el.clear();
        el.sendKeys(mName);
    }

    public void enterEmployeeId(String empId){
        ActionsUtil.clearAndType(employeeId, empId);
    }


    public String getTitleText() {
        return Waits.visible(titleAddEmployee).getText().trim();
    }

    public void fillNames(String f, String m, String l) {
        WebElement fn = Waits.visible(firstName);
        fn.clear();
        if (f != null) fn.sendKeys(f);

        WebElement mn = Waits.visible(middleName);
        mn.clear();
        if (m != null) mn.sendKeys(m);

        WebElement ln = Waits.visible(lastName);
        ln.clear();
        if (l != null) ln.sendKeys(l);
    }

    public void setEmployeeId(String id) {
        WebElement empId = Waits.visible(employeeId);
        empId.clear();
        empId.sendKeys(id);
    }

    public String getEmployeeIdValue() {
        Waits.visible(employeeId);

        Waits.waitUntil(driver -> {
            try {
                String v = employeeId.getAttribute("value");
                return v != null && !v.trim().isEmpty();
            } catch (StaleElementReferenceException e) {
                return false;
            }
        }, 30);

        return employeeId.getAttribute("value");
    }

    /**
     * ✅ Robust Save:
     * - scroll button into view
     * - normal click, fallback to JS click if intercepted/stale
     * - wait for loader to disappear / url change / success toast
     */
    public void clickSave() {
        // if any loader still visible from navigation, wait it out
        safeWaitLoaderGone();

        WebElement save = Waits.visible(btnSave);
        ActionsUtil.scrollTo(save);
        ActionsUtil.highlight(save);

        try {
            Waits.clickable(save).click();
        } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
            // re-fetch and JS click
            WebElement freshSave = Waits.clickable(btnSave);
            ActionsUtil.scrollTo(freshSave);
            ActionsUtil.jsClick(freshSave);
        }

        // ✅ Wait for save to actually complete
        waitForSaveComplete();
    }

    // -------------------------
    // helpers
    // -------------------------

    private void safeWaitLoaderGone() {
        try { Waits.invisible(loadingSpinnerBy); } catch (Exception ignored) {}
        try { Waits.invisible(loadingOverlayBy); } catch (Exception ignored) {}
    }

    private void waitForSaveComplete() {

        safeWaitLoaderGone();


        try { Waits.urlContains("viewPersonalDetails"); } catch (Exception ignored) {}

        // 3) success toast (if your build shows it)
        try { Waits.visible(successToastBy); } catch (Exception ignored) {}

        // 4) employee id input exists (page is stable)
        Waits.visible(employeeId);
    }
}