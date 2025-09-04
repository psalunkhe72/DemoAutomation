package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DemoQATest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");           // ✅ headless for Jenkins
        options.addArguments("--no-sandbox");             // ✅ required in Jenkins/Linux
        options.addArguments("--disable-dev-shm-usage");  // ✅ prevents crashes
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");  // ✅ simulate full screen

        driver = new ChromeDriver(options);
        driver.get("https://demoqa.com/text-box");
    }

    @Test
    public void fillTextBoxForm() {
        // ✅ Fill form fields
        driver.findElement(By.id("userName")).sendKeys("Pramod");
        driver.findElement(By.id("userEmail")).sendKeys("pramod@example.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Pune, India");
        driver.findElement(By.id("permanentAddress")).sendKeys("Pune, India");

        // ✅ Fix for intercepted click issue
        WebElement submitBtn = driver.findElement(By.id("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        // ✅ Verify output
        String nameOutput = driver.findElement(By.id("name")).getText();
        Assert.assertTrue(nameOutput.contains("Pramod"), "Name was not submitted properly!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
