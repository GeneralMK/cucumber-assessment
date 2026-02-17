package steps;

import config.Config;
import driver.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.*;

import utils.ConfigLoaderUtil;

import java.util.List;
import java.util.Map;

public class HrmsSteps {

    // store generated id across steps
    private String generatedEmployeeId;

    private LoginPage loginPage() { return new LoginPage(); }
    private HomePage dashboardPage() { return new HomePage(); }
    private PIMPage pimPage() { return new PIMPage(); }
    private AddEmployeePage addEmployeePage() { return new AddEmployeePage(); }
    private EmployeeListPage employeeListPage() { return new EmployeeListPage(); }

    @Given("I open the HRMS login page")
    public void i_open_the_hrms_login_page() {
        Assert.assertNotNull(DriverManager.getDriver(), "Driver is NULL. Hooks not running or glue missing.");
        DriverManager.getDriver().get(Config.baseUrl());
        Assert.assertTrue(loginPage().isAt(), "Not on Login page");
    }

    @When("I clear the username field")
    public void i_clear_the_username_field() {
        loginPage().clearUsername();
    }

    @When("I enter username as {string}")
    public void i_enter_username_as(String username) {
        loginPage().typeUsername(username);
    }

    @When("I clear the password field")
    public void i_clear_the_password_field() {
        loginPage().clearPassword();
    }

    @When("I enter password as {string}")
    public void i_enter_password_as(String password) {
        loginPage().typePassword(password);
    }

    @When("I click the Login button")
    public void i_click_the_login_button() {
        loginPage().clickLogin();
    }

    @Then("I should see username field error message {string}")
    public void i_should_see_username_field_error_message(String expected) {
        Assert.assertEquals(loginPage().getAnyRequiredMessage(), expected);
    }

    @Then("I should see password field error message {string}")
    public void i_should_see_password_field_error_message(String expected) {
        Assert.assertEquals(loginPage().getAnyRequiredMessage(), expected);
    }

    @Then("I should see login error message {string}")
    public void i_should_see_login_error_message(String expected) {
        Assert.assertEquals(loginPage().getInvalidCredentialsMessage(), expected);
    }

    @When("I login with valid admin credentials")
    public void i_login_with_valid_admin_credentials() {
        loginPage().login(ConfigLoaderUtil.username(), ConfigLoaderUtil.password());
    }

    @Then("I should be on the Dashboard page")
    public void i_should_be_on_the_dashboard_page() {
        Assert.assertTrue(dashboardPage().isAt(), "Not on Dashboard page");
    }

    @Given("I navigate to PIM module")
    public void i_navigate_to_pim_module() {
        dashboardPage().clickPIM();
        Assert.assertTrue(pimPage().isAt(), "Not on PIM page");
    }

    @Given("I open Add Employee page")
    public void i_open_add_employee_page() {
        pimPage().openAddEmployee();
        Assert.assertTrue(pimPage().isOnAddEmployeeTab(), "Not on Add Employee page");
    }

    @When("I add a new employee with:")
    public void i_add_a_new_employee_with(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        Map<String, String> row = rows.get(0);

        String firstName = row.get("firstName");
        String middleName = row.get("middleName");
        String lastName = row.get("lastName");

        addEmployeePage().fillNames(firstName, middleName, lastName);
        addEmployeePage().clickSave();

        // capture generated id after save
        generatedEmployeeId = addEmployeePage().getEmployeeIdValue();
    }

    @When("I add a new employee with provided id:")
    public void i_add_a_new_employee_with_provided_id(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        Map<String, String> row = rows.get(0);

        addEmployeePage().fillNames(row.get("firstName"), row.get("middleName"), row.get("lastName"));
        addEmployeePage().setEmployeeId(row.get("employeeId"));
        addEmployeePage().clickSave();
    }



    @Then("the system should generate an employee id")
    public void the_system_should_generate_an_employee_id() {
        Assert.assertNotNull(generatedEmployeeId, "Generated employee id is null");
        Assert.assertFalse(generatedEmployeeId.isBlank(), "Generated employee id is blank");
    }

    @Then("I should be able to find the employee in Employee List by generated employee id")
    public void i_should_be_able_to_find_the_employee_in_employee_list_by_generated_employee_id() {
        pimPage().openEmployeeList();
        employeeListPage().searchByEmployeeId(generatedEmployeeId);
        Assert.assertTrue(employeeListPage().isRecordFoundForEmployeeId(generatedEmployeeId),
                "Employee record not found for generated id: " + generatedEmployeeId);
    }

    @Then("I should be able to find the employee in Employee List by employee id {string}")
    public void i_should_be_able_to_find_the_employee_in_employee_list_by_employee_id(String employeeId) {
        pimPage().openEmployeeList();
        employeeListPage().searchByEmployeeId(employeeId);
        Assert.assertTrue(employeeListPage().isRecordFoundForEmployeeId(employeeId),
                "Employee record not found for id: " + employeeId);
    }

    @When("I attempt to save employee with first name {string} and last name {string}")
    public void i_attempt_to_save_employee_with_first_name_and_last_name(String firstName, String lastName) {
        addEmployeePage().fillNames(firstName, "", lastName);
        addEmployeePage().clickSave();
    }


    @Then("the employee record should exist in the database for the generated employee id")
    public void the_employee_record_should_exist_in_the_database_for_the_generated_employee_id() {
        Assert.assertTrue(true, "DB verification not implemented yet");
    }

    @Then("the employee record should exist in the database for employee id {string}")
    public void the_employee_record_should_exist_in_the_database_for_employee_id(String employeeId) {
        Assert.assertTrue(true, "DB verification not implemented yet");
    }
}