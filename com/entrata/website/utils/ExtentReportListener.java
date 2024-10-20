package entrata.website.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportListener implements ITestListener {
	ExtentHtmlReporter htmlReporter;
	ExtentReports reports;
	ExtentTest test;
	public static String testName = "";
	public static String reportName = "finalReport.html";
	public static String testCaseName = "";

	public void configureReport() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + ".reports//" + reportName);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);

		// system information
		reports.setSystemInfo("Machine", "hp");
		reports.setSystemInfo("OS", "windows");
		reports.setSystemInfo("user", "shweta");

		// configuration
		htmlReporter.config().setDocumentTitle("Automation Testing Report");
		htmlReporter.config().setReportName("Entrata Website Automation Testing Report");
		htmlReporter.config().setTheme(Theme.DARK);

	}

	public void onTestStart(ITestResult result) {
		System.out.println("I am start" + "=" + result.getName());
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("I am success" + "=" + result.getName());
		test = reports.createTest(result.getName() + testCaseName);
		test.log(Status.PASS, MarkupHelper.createLabel(
				"Name of the pass test case is" + "=" + result.getName() + testCaseName, ExtentColor.GREEN));
	}



	public void onTestFailure(ITestResult result) {
		System.out.println("I am Fail" + "=" + result.getName());
		test = reports.createTest(result.getName());

		Object driverAttribute = result.getTestContext().getAttribute("WebDriver");
		try {
			if (driverAttribute instanceof WebDriver) {
				test.log(Status.FAIL, result.getName(),
						MediaEntityBuilder
								.createScreenCaptureFromPath(captureScreenshotWebDriver(((WebDriver) driverAttribute)))
								.build());
				SetResultWebDriver(result, ((WebDriver) driverAttribute));
			
			}
		} catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("I am skipped" + "=" + result.getName());
		test = reports.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel("Name of the failed test case is" + "=" + result.getName(),
				ExtentColor.YELLOW));
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("I am failed with successpercentage");
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	public void onStart(ITestContext context) {
		configureReport();
		System.out.println("I am on start method");
	}

	public void onFinish(ITestContext context) {
		System.out.println("I am on Finish method");
		reports.flush();
	}

	
	public String captureScreenshotWebDriver(WebDriver driver) throws IOException {
		String Concat = ".";
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String dest = Concat + "./EntrataIndia.reports/screenshots/" + System.currentTimeMillis() + ".png";
		FileUtils.copyFile(source, new File(dest));
		return dest;
	}

	public void SetResultWebDriver(ITestResult result, WebDriver driver) throws IOException, InterruptedException {
		String screenShot = captureScreenshotWebDriver(driver);
		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail("Test Case: " + test.addScreenCaptureFromPath(screenShot));
		}
	}
}
