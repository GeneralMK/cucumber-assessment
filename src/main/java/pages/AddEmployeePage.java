package pages;

import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Waits;

public class AddEmployeePage extends BasePage {

    @FindBy(css = "h6.orangehrm-main-title")
    private WebElement titleAddEmployee;

    @FindBy(css = "input[name='firstName']")
    private WebElement firstName;

    @FindBy(css = "input[name='middleName']")
    private WebElement middleName;

    @FindBy(css = "input[name='lastName']")
    private WebElement lastName;

    @FindBy(xpath = "(//label[normalize-space()='Employee Id']/following::input)[1]")
    private WebElement employeeId;

    @FindBy(css = "button[type='submit']")
    private WebElement btnSave;

    public AddEmployeePage() {
        WebDriver driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public boolean isAt() {
        return "Add Employee".equalsIgnoreCase(Waits.visible(titleAddEmployee).getText().trim());
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
        return Waits.visible(employeeId).getAttribute("value");
    }

    public void clickSave() {
        Waits.clickable(btnSave).click();
    }
}