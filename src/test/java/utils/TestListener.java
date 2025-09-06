package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;

public class TestListener implements ITestListener {



    @Override
    public void onTestStart(ITestResult result) {
        ExtentManager.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentManager.getTest().fail(result.getThrowable());

        WebDriver driver = getDriverFromTestInstance(result);
        if (driver != null) {
            try {
                String screenshotPath = takeScreenshot(driver, result.getMethod().getMethodName());
                ExtentManager.getTest().addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                ExtentManager.getTest().warning("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            ExtentManager.getTest().warning("WebDriver instance was null, screenshot not captured.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().skip(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flushReport();
    }

    // ---------------- Utility methods ----------------

    /**
     * Safely extract WebDriver from test instance, works even if the field is private
     */
    private WebDriver getDriverFromTestInstance(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            Field driverField = null;

            // Look for "driver" field in class hierarchy
            Class<?> clazz = testInstance.getClass();
            while (clazz != null) {
                try {
                    driverField = clazz.getDeclaredField("driver");
                    break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }

            if (driverField != null) {
                driverField.setAccessible(true); // allow access to private field
                Object driverObj = driverField.get(testInstance);
                if (driverObj instanceof WebDriver) {
                    return (WebDriver) driverObj;
                }
            }
        } catch (IllegalAccessException e) {
            // ignore
        }
        return null; // driver not found or inaccessible
    }

    /**
     * Take screenshot and save under target/screenshots
     */
    private String takeScreenshot(WebDriver driver, String methodName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "target/screenshots/" + methodName + ".png";
        File dest = new File(path);
        dest.getParentFile().mkdirs();
        Files.copy(src.toPath(), dest.toPath());
        return path;
    }
}
