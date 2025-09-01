package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;

    @FindBy(xpath = "//h5[text()='Elements']")
    WebElement elementsCard;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openHomePage(String url) {
        driver.get(url);
    }

    public void clickElementsCard() {
        // Hide the fixed banner if present
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var banner = document.getElementById('fixedban'); if(banner){banner.style.display='none';}");
        } catch (Exception e) {
            // ignore if banner not present
        }

        // Scroll element into view
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", elementsCard);

        // Wait until element is clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(elementsCard));

        // Click the element
        elementsCard.click();
    }
}
