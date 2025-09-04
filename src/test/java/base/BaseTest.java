package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

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
}
