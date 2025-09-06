package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ExtentManager;

import java.io.File;

public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Detect environment: default = local
        String env = System.getProperty("env", "local");
        System.out.println("Running tests in environment: " + env);

        if (env.equalsIgnoreCase("jenkins")) {
            // ✅ Jenkins/Linux-safe options
            options.addArguments("--headless=new");       // modern headless mode
            options.addArguments("--no-sandbox");         // required on Jenkins
            options.addArguments("--disable-dev-shm-usage"); // prevents crashes
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        } else {
            // ✅ Local (with browser UI)
            options.addArguments("--start-maximized");
        }

        driver = new ChromeDriver(options);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected static ExtentReports extent;

    @BeforeSuite
    public void setupExtentReport() {
        // 1. Create reports folder if it doesn't exist
        File reportDir = new File("reports");
        if (!reportDir.exists()) {
            reportDir.mkdir();
        }

        // 2. Initialize ExtentSparkReporter
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("reports/ExtentReport.html");

        // 3. Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        if (ExtentManager.getInstance() != null) {
            ExtentManager.getInstance().flush();
            System.out.println("✅ Extent Report flushed to target/ExtentReport.html");
        }
    }
}







