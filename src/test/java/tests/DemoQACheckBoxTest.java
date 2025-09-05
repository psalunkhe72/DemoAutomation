package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.RetryAnalyzer;

public class DemoQACheckBoxTest extends BaseTest {

    @Test(retryAnalyzer = utils.RetryAnalyzer.class)
    public void selectCheckBox() {
        driver.get("https://demoqa.com/checkbox");

        WebElement expandBtn = driver.findElement(By.cssSelector("button[title='Toggle']"));
        expandBtn.click();

        WebElement checkBox = driver.findElement(By.cssSelector("span.rct-checkbox"));
        checkBox.click();

        WebElement result = driver.findElement(By.id("result"));
        Assert.assertTrue(result.getText().contains("home"), "❌ Checkbox not selected properly!");
    }
}
