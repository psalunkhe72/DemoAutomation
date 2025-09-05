package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testThread.get().log(Status.FAIL, "Test failed: " + result.getThrowable());

        Object currentClass = result.getInstance();
        try {
            WebDriver driver = (WebDriver) result.getTestClass().getRealClass()
                    .getDeclaredField("driver").get(currentClass);

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destPath = System.getProperty("user.dir") + "/target/screenshots/" + result.getMethod().getMethodName() + ".png";
            Files.createDirectories(Paths.get("target/screenshots"));
            Files.copy(screenshot.toPath(), Paths.get(destPath));

            testThread.get().addScreenCaptureFromPath(destPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
