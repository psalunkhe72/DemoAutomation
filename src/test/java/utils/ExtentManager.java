package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

    public class ExtentManager {
        private static ExtentReports extent;

        public static ExtentReports getInstance() {
            if (extent == null) {
                String reportPath = System.getProperty("user.dir") + "/target/ExtentReport.html";
                ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
                reporter.config().setReportName("DemoQA Automation Results");
                reporter.config().setDocumentTitle("Automation Test Report");

                extent = new ExtentReports();
                extent.attachReporter(reporter);
                extent.setSystemInfo("Environment", System.getProperty("env", "local"));
                extent.setSystemInfo("Tester", "Pramod");
            }
            return extent;
        }
    }


