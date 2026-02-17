# SyntaxHRM Automation Framework Project

A robust **BDD-style UI automation framework** for the HRM (SyntaxHRM ) web application, built using **Selenium WebDriver, Cucumber, TestNG, and Java**.

The framework is designed with **maintainability, stability, and scalability** in mind, following industry best practices such as the **Page Object Model (POM)**, explicit waits, and scenario-level browser lifecycle management.

---

## 🔧 Tech Stack

* **Java 17**
* **Selenium WebDriver 4.x**
* **Cucumber (BDD)**
* **TestNG**
* **Maven**
* **WebDriverManager**
* **Chrome (Chromium-based browser)**

---

## 📁 Project Structure

```
cucumberProject
│
├── src/test/java
│   ├── config            # Configuration readers & loaders
│   ├── driver            # Driver lifecycle & thread-safe driver manager
│   ├── hooks             # Cucumber hooks (Before / After)
│   ├── pages             # Page Object Model (Login, PIM, AddEmployee, etc.)
│   ├── steps             # Step Definitions
│   ├── utils             # Waits, helpers, utilities
│   └── runner            # TestNG Cucumber runner
│
├── src/test/resources
│   ├── feature           # Cucumber feature files
│   └── config.properties # Environment configuration
│
├── target
│   ├── cucumber-report.html
│   └── logs
│
└── pom.xml
```

---

## 🚀 Key Design Decisions

### 1️⃣ Page Object Model (POM)

* Each screen (Login, Dashboard, PIM, Add Employee, Employee List) has its own Page class
* Page classes expose **business actions**, not raw Selenium calls
* Improves readability and reduces duplication

### 2️⃣ Explicit Wait Strategy (No `Thread.sleep`)

* Centralized `Waits` utility
* Uses `WebDriverWait` + `ExpectedConditions`
* Improves stability across environments

### 3️⃣ Scenario-Level Driver Lifecycle

* Browser **starts before each scenario**
* Browser **quits after each scenario**
* Prevents session reuse bugs and flaky failures

### 4️⃣ Reliable Page Validation

* Pages are validated using **visible UI signals** (e.g. headings like `Add Employee`)
* Avoids unreliable checks like `aria-selected` or transient CSS classes

---

## 🧪 Implemented Test Coverage

### 🔐 Login Validation

* Empty username → Required
* Empty password → Required
* Invalid credentials → Error message
* Valid login → Dashboard

### 👥 Employee Management (PIM)

* Add employee without Employee ID (system-generated)
* Add employee with provided Employee ID
* Validation for missing required fields
* Search employee by ID in Employee List

---

## 🧩 Feature Example

```gherkin
Scenario: Add employee without providing Employee ID
  Given I open the HRMS login page
  When I login with valid admin credentials
  And I navigate to PIM module
  And I open Add Employee page
  And I add a new employee with:
    | firstName | middleName | lastName |
    | John      |            | Smith    |
  Then I should see employee saved successfully
  And the system should generate an employee id
```

---

## 🧠 Page Validation Strategy (Important)

Instead of validating tabs via active CSS classes, the framework validates **page state using visible headings**.

Example:

```java
@FindBy(css = "h6.orangehrm-main-title")
private WebElement titleAddEmployee;

public boolean isAt() {
    return Waits.visible(titleAddEmployee)
                .getText()
                .equalsIgnoreCase("Add Employee");
}
```

This approach is:

* More stable
* Less UI-framework dependent
* Resistant to DOM structure changes

---

## ⚙️ Configuration

### `config.properties`

```properties
base.url=http://54.198.61.50/web/index.php/auth/login
username=Admin
password=the passowrd
browser=chrome
headless=false
```

---

## ▶️ How to Run Tests

### From IDE

* Right-click `TestRunner.java`
* Select **Run**

### From Command Line

```bash
mvn clean test
```

---

## 📊 Reports

After execution, reports are generated at:

```
target/cucumber-report.html
```

Includes:

* Scenario status
* Step execution details
* Failure screenshots (on error)

---

## 🛠️ Known Warnings (Safe to Ignore)

```
WARNING: Unable to find CDP implementation matching Chrome 145
```

This is a **non-blocking Selenium DevTools warning** and does not affect test execution.

---

## 🔮 Future Enhancements

* JDBC-based DB validation for employee records
* Parallel execution with TestNG
* Environment switching (QA / UAT / PROD)
* Allure reporting integration
* Dockerized execution

---

## 👨‍💻 Author

**Masixole Kondile**
 SDET | Automation Engineer

---

