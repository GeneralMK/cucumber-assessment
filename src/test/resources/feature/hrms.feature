@hrms
Feature: HRMS Portal - Login validation and Employee management

  # ---------------------------------------------------------------------------
  # USER STORY 1: Login Validation for HRMs Portal
  # ---------------------------------------------------------------------------

  @login @negative
  Scenario: Login with empty username should show "Required"
    Given I open the HRMS login page
    When I clear the username field
    And I enter password as "anyPassword"
    And I click the Login button
    Then I should see username field error message "Required"

  @login @negative
  Scenario: Login with empty password should show "Required"
    Given I open the HRMS login page
    When I enter username as "anyUser"
    And I clear the password field
    And I click the Login button
    Then I should see password field error message "Required"

  @login @negative
  Scenario Outline: Login with incorrect credentials should show "Invalid credentials"
    Given I open the HRMS login page
    When I enter username as "<username>"
    And I enter password as "<password>"
    And I click the Login button
    Then I should see login error message "Invalid credentials"

    Examples:
      | username    | password     |
      | wrong_user  | wrong_pass   |


  @login @positive
  Scenario: Login with valid credentials should land on Dashboard
    Given I open the HRMS login page
    When I login with valid admin credentials
    Then I should be on the Dashboard page


  # ---------------------------------------------------------------------------
  # USER STORY 2: Add Employee to HRMS (with/without Employee ID)
  # ---------------------------------------------------------------------------

  @employee @positive
  Scenario: Add employee without providing Employee ID (system auto-generates ID)
    Given I open the HRMS login page
    When I login with valid admin credentials
    And I navigate to PIM module
    And I open Add Employee page
    And I add a new employee with:
      | firstName | middleName | lastName |
      | John      |            | Smith    |

    And the system should generate an employee id
    And I should be able to find the employee in Employee List by generated employee id
    And the employee record should exist in the database for the generated employee id

  @employee @positive
  Scenario: Add employee by providing a unique Employee ID
    Given I open the HRMS login page
    When I login with valid admin credentials
    And I navigate to PIM module
    And I open Add Employee page
    And I add a new employee with provided id:
      | firstName | middleName | lastName | employeeId |
      | Masixole  | Masi       | Kondile  | 900001     |

    And I should be able to find the employee in Employee List by employee id "900001"
    And the employee record should exist in the database for employee id "900001"

  @employee @negative
  Scenario Outline: Add employee should show validation messages for missing required fields
    Given I open the HRMS login page
    When I login with valid admin credentials
    And I navigate to PIM module
    And I open Add Employee page
    And I attempt to save employee with first name "<firstName>" and last name "<lastName>"


    Examples:
      | firstName | lastName | expectedMessage |
      |          | Smith    | Required        |
      | John     |          | Required        |