package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TextBoxPage {
    WebDriver driver;

    @FindBy(id = "userName")
    WebElement fullNameField;

    @FindBy(id = "userEmail")
    WebElement emailField;

    @FindBy(id = "submit")
    WebElement submitBtn;

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterFullName(String name) {
        fullNameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }




    public void submitForm() {
        // Hide ads iframe
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "var ads = document.querySelectorAll('iframe[id^=\"google_ads_iframe\"]');" +
                            "ads.forEach(a => a.style.display='none');"
            );
        } catch (Exception e) { }

        // Scroll button into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", submitBtn);

        // Wait until clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn));

        // Click
        submitBtn.click();
    }



}
