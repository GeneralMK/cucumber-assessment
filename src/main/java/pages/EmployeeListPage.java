package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Waits;

import java.util.List;

public class EmployeeListPage extends BasePage {

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeIdInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchBtn;

    @FindBy(css = ".oxd-table-body .oxd-table-card")
    private List<WebElement> rows;

    public void searchByEmployeeId(String employeeId) {
        Waits.visible(By.xpath("//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input"));
        employeeIdInput.clear();
        employeeIdInput.sendKeys(employeeId);

        Waits.clickable(By.cssSelector("button[type='submit']")).click();
        Waits.visible(By.cssSelector(".oxd-table-body"));
    }

    public boolean isRecordFoundForEmployeeId(String employeeId) {
        return rows.stream().anyMatch(r -> r.getText().contains(employeeId));
    }
}