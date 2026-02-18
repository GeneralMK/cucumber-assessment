package pages;

import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ActionsUtil;
import utils.Waits;

import java.util.List;
import java.util.Optional;

public class EmployeeListPage extends BasePage {

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeIdInput;

    private final By searchBtnBy  = By.cssSelector("button[type='submit']");
    private final By tableBodyBy  = By.cssSelector(".oxd-table-body");
    private final By rowCardsBy   = By.cssSelector(".oxd-table-body .oxd-table-card");


    private final By noRecordsBy  = By.xpath("//*[contains(normalize-space(),'No Records Found')]");

    public void searchByEmployeeId(String employeeId) {
        WebElement input = Waits.visible(employeeIdInput);
        input.clear();
        input.sendKeys(employeeId);

        String beforeSig = tableSignature();

        Waits.clickable(searchBtnBy).click();
        Waits.visible(tableBodyBy);


        Waits.waitUntil(d -> {
            try {
                boolean hasRows = !d.findElements(rowCardsBy).isEmpty();
                boolean noRecords = !d.findElements(noRecordsBy).isEmpty();
                boolean changed = !tableSignature().equals(beforeSig);
                return hasRows || noRecords || changed;
            } catch (Exception e) {
                return false;
            }
        }, 30);
    }

    public boolean isRecordFoundForEmployeeId(String employeeId) {
        WebElement tableBody = Waits.visible(tableBodyBy);

        List<WebElement> currentRows = DriverManager.getDriver().findElements(rowCardsBy);

        Optional<WebElement> match = currentRows.stream()
                .filter(r -> r.getText().contains(employeeId))
                .findFirst();

        if (match.isPresent()) {
            WebElement row = match.get();

            // table body is the scroll container
            ActionsUtil.scrollInContainer(tableBody, row);
            ActionsUtil.highlight(row);

            return true;
        }
        return false;
    }

    private String tableSignature() {
        try {
            List<WebElement> rows = DriverManager.getDriver().findElements(rowCardsBy);
            String first = rows.isEmpty() ? "" : rows.get(0).getText();
            return rows.size() + "::" + first;
        } catch (Exception e) {
            return "NA";
        }
    }
}