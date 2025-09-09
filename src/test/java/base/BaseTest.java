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





    @BeforeTest(alwaysRun = true)
    @Parameters({"browser"})
    public void setup(@Optional("chrome") String browser) {
        String env = System.getProperty("env", "local");
        System.out.println("Running tests in environment: " + env + " | Browser: " + browser);

        try {
            if (env.equalsIgnoreCase("grid")) {
                // Selenium Grid URL
                URL gridUrl = new URL("http://localhost:4444/wd/hub");

                if (browser.equalsIgnoreCase("firefox")) {
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless=new");
                    firefoxOptions.addArguments("--no-sandbox");
                    firefoxOptions.addArguments("--disable-dev-shm-usage");
                    firefoxOptions.addArguments("--disable-gpu");
                    firefoxOptions.addArguments("--window-size=1920,1080");

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
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.setCapability("se:recordVideo", false); // optional for stability

                    // Retry loop for Grid node
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
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
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

    @BeforeSuite(alwaysRun = true)
    public void setupExtentReport() {
        extent = ExtentManager.getInstance();
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("✅ Extent Report flushed to target/ExtentReport.html");
        }
    }
}