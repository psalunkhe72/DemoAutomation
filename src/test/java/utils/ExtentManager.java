package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

    public class ExtentManager {
        private static ExtentReports extent;

        private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

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


        public static ExtentTest createTest(String name) {
            ExtentTest extentTest = getInstance().createTest(name);
            test.set(extentTest);   // ✅ store in ThreadLocal
            return extentTest;
        }

        public static ExtentTest getTest() {
            return test.get();   // ✅ retrieve for current thread
        }
        }



