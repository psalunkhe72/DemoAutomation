package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();




    // Folder-based report path for Jenkins
    private static final String REPORT_FOLDER = System.getProperty("user.dir") + "/target/extent-report";

    public static ExtentReports getInstance() {
        if (extent == null) {
            // Ensure folder exists
            new File(REPORT_FOLDER).mkdirs();

            String reportPath = REPORT_FOLDER + "/index.html"; // Main index page
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("DemoQA Automation Results");
            reporter.config().setDocumentTitle("Automation Test Report");

            // ✅ Force offline mode
            reporter.config().setOfflineMode(true);

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Environment", System.getProperty("env", "local"));
            extent.setSystemInfo("Tester", "Pramod");
        }
        return extent;
    }

    // Create test and store in ThreadLocal for parallel runs
    public static ExtentTest createTest(String name) {
        ExtentTest t = getInstance().createTest(name);
        test.set(t);
        return t;
    }

    // Get test for current thread
    public static ExtentTest getTest() {
        return test.get();
    }

    // Flush report at the end
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
