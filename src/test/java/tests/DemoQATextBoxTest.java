package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.time.Duration;

public class DemoQATextBoxTest extends BaseTest {

    @Test(retryAnalyzer = utils.RetryAnalyzer.class)
    public void fillTextBoxForm() {
        driver.get("https://demoqa.com/text-box");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("userName")).sendKeys("Pramod");
        driver.findElement(By.id("userEmail")).sendKeys("pramod@example.com");
        driver.findElement(By.id("currentAddress")).sendKeys("Pune, India");
        driver.findElement(By.id("permanentAddress")).sendKeys("Pune, India");

        WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);

        String nameOutput = driver.findElement(By.id("name")).getText();
        Assert.assertTrue(nameOutput.contains("Pramod"), "❌ Name was not submitted properly!");
    }
}
