package base;

import com.aventstack.extentreports.ExtentReports;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import utils.ExtentManager;

import java.net.URL;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;

    @BeforeSuite(alwaysRun = true)
    public void setupExtentReport() {
        extent = ExtentManager.getInstance();
    }

    @BeforeTest(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser) {
        String env = System.getProperty("env", "local");
        System.out.println("Running tests in environment: " + env + " | Browser: " + browser);

        try {
            if (env.equalsIgnoreCase("grid")) {
                URL gridUrl = new URL("http://localhost:4444/wd/hub");

                if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");

                    // Retry loop for Grid node
                    int retries = 3;
                    while (retries > 0) {
                        try {
                            driver = new RemoteWebDriver(gridUrl, firefoxOptions);
                            System.out.println("✅ Connected to Grid Firefox node!");
                            break;
                        } catch (Exception e) {
                            System.out.println("⚠ Firefox Grid node not ready, retrying in 5s...");
                            Thread.sleep(5000);
                            retries--;
                        }
                    }
                    if (driver == null) {
                        throw new RuntimeException("❌ Failed to connect to Selenium Grid Firefox node after retries");
                    }

                } else { // Chrome
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");
                    chromeOptions.setCapability("se:recordVideo", false);

                    int retries = 3;
                    while (retries > 0) {
                        try {
                            driver = new RemoteWebDriver(gridUrl, chromeOptions);
                            System.out.println("✅ Connected to Grid Chrome node!");
                            break;
                        } catch (Exception e) {
                            System.out.println("⚠ Chrome Grid node not ready, retrying in 5s...");
                            Thread.sleep(5000);
                            retries--;
                        }
                    }
                    if (driver == null) {
                        throw new RuntimeException("❌ Failed to connect to Selenium Grid Chrome node after retries");
                    }
                }

            } else {
                // Local execution
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");

                if (env.equalsIgnoreCase("jenkins")) {
                    chromeOptions.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                driver = new ChromeDriver(chromeOptions);
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to initialize WebDriver for env=" + env + " and browser=" + browser, e);
        }
    }

    @AfterTest(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() throws InterruptedException {
        if (extent != null) {
            extent.flush();
            System.out.println("✅ Extent Report flushed to target/ExtentReport.html");
        }
    }
}
