package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.ExtentManager;
import utils.RetryAnalyzer;

public class DemoQARadioButtonTest extends BaseTest {



    @Test(retryAnalyzer = utils.RetryAnalyzer.class)



    public void selectRadioButton() {
        driver.get("https://demoqa.com/radio-button");

        driver.findElement(By.xpath("//label[text()='Yes']")).click();
        String result = driver.findElement(By.className("text-success")).getText();

        Assert.assertEquals(result, "Yes", "❌ Radio button selection failed!");
    }
}
