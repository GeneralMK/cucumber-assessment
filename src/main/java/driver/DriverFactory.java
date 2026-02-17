package driver;

import config.ConfigLoader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {}

    public static void initDriver() {
        String browser = ConfigLoader.get("browser").toLowerCase();
        boolean headless = ConfigLoader.getBoolean("headless", false);

        WebDriver driver;

        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            default -> {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                // Headless / UI mode
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                } else {
                    options.addArguments("--start-maximized");
                }

                // Helps with some environments + insecure sites
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("--allow-running-insecure-content");

                // (Sometimes needed in newer Chrome/Selenium combos)
                options.addArguments("--remote-allow-origins=*");

                driver = new ChromeDriver(options);
            }
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        DriverManager.setDriver(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DriverManager.getDriver();
        try {
            if (driver != null) driver.quit();
        } finally {
            DriverManager.unload();
        }
    }
}