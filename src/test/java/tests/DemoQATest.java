package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.TextBoxPage;
import utils.ConfigReader;

public class DemoQATest extends BaseTest {

    @Test
    public void fillTextBoxForm() {
        //comment to check commit works
        HomePage homePage = new HomePage(driver);
        TextBoxPage textBoxPage = new TextBoxPage(driver);

        homePage.openHomePage(ConfigReader.get("base.url"));
        homePage.clickElementsCard();

        driver.get(ConfigReader.get("base.url") + "/text-box");

        textBoxPage.enterFullName("Pramod Salunkhe");
        textBoxPage.enterEmail("pramod@example.com");
        textBoxPage.submitForm();
    }
}
