package pages;

import org.openqa.selenium.By;
import utils.Waits;

public class PIMPage extends BasePage {

    private final By pimHeader = By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");

    private final By addEmployeeTab =
            By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space()='Add Employee']");

    private final By employeeListTab =
            By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-item') and normalize-space()='Employee List']");

    private final By activeTopTab =
            By.xpath("//a[contains(@class,'oxd-topbar-body-nav-tab-item') and (@aria-selected='true' or contains(@class,'--active'))]");

    public boolean isAt() {
        Waits.urlContains("/pim");
        String text = Waits.visible(pimHeader).getText().trim();
        return text.equalsIgnoreCase("PIM");
    }

    public String getActiveTopTabText() {
        return Waits.visible(activeTopTab).getText().trim();
    }

    public boolean isOnAddEmployeeTab() {
        return getActiveTopTabText().equalsIgnoreCase("Add Employee");
    }

    public boolean isOnEmployeeListTab() {
        return getActiveTopTabText().equalsIgnoreCase("Employee List");
    }

    // ---------------------------------------------------------------------
    // Navigation
    // ---------------------------------------------------------------------
    public void openAddEmployee() {
        Waits.clickable(addEmployeeTab).click();
        Waits.urlContains("/pim/addEmployee");
    }

    public void openEmployeeList() {
        Waits.clickable(employeeListTab).click();
        Waits.urlContains("/pim/viewEmployeeList");
    }
}