package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import utils.DateUtil;
import utils.FileUtil;

public final class ExtentManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> tlTest = new ThreadLocal<>();

    private ExtentManager(){}

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            String outDir = "target/test-output";
            FileUtil.ensureDir(outDir);

            String reportPath = outDir + "/ExtentReport_" + DateUtil.timestamp() + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Cucumber Selenium Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Framework", "Cucumber + Selenium + TestNG");
            extent.setSystemInfo("Java", System.getProperty("java.version"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extent;
    }

    public static void createTest(String testName) {
        ExtentTest test = getExtent().createTest(testName);
        tlTest.set(test);
    }

    public static ExtentTest getTest() {
        return tlTest.get();
    }

    public static void pass(String msg) {
        if (getTest() != null) getTest().pass(msg);
    }

    public static void fail(String msg) {
        if (getTest() != null) getTest().fail(msg);
    }

    public static void info(String msg) {
        if (getTest() != null) getTest().info(msg);
    }

    public static synchronized void flush() {
        if (extent != null) extent.flush();
        tlTest.remove();
    }
}