package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

import java.time.Duration;

public class DemoQAWebTableTest extends BaseTest {

    @Test(retryAnalyzer = utils.RetryAnalyzer.class)
    public void addWebTableEntry() {
        driver.get("https://demoqa.com/webtables");

        driver.findElement(By.id("addNewRecordButton")).click();
        driver.findElement(By.id("firstName")).sendKeys("Pramod");
        driver.findElement(By.id("lastName")).sendKeys("Salunkhe");
        driver.findElement(By.id("userEmail")).sendKeys("pramod@example.com");
        driver.findElement(By.id("age")).sendKeys("35");
        driver.findElement(By.id("salary")).sendKeys("50000");
        driver.findElement(By.id("department")).sendKeys("QA");
        driver.findElement(By.id("submit")).click();


        // ✅ Explicit wait for new row to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean rowFound = wait.until(d ->
                d.findElements(By.xpath("//div[@class='rt-td' and text()='Pramod']")).size() > 0
        );

        Assert.assertTrue(rowFound, "❌ Web table entry not added!");
    }
}
