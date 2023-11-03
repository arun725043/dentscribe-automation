package com.dentscribe.ExtentReport;

import java.util.Arrays;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.dentscribe.base.AndroidBase;

import io.appium.java_client.AppiumDriver;

public class TestListeners extends AndroidBase implements ITestListener {

	// This reference is used by test cases (classes) to create test steps in Report
	private ExtentReports extent = new ExtentManager().createInsatance();
	private ExtentTest test;
	private ExtentReports extentReport;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	@Override
	public void onStart(ITestContext context) {
		extentReport = ExtentManager.createInsatance();
		extentTest.set(test);
	}

	@Override
	public void onFinish(ITestContext context) {
		if (extentReport != null) {
			extentReport.flush();
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		test = extentReport.createTest(result.getTestClass().getName() + " -> " + result.getMethod().getMethodName());
		extentTest.set(test);
		// System.out.println("Pool on different test case iteration"+extentTest);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println(Arrays.toString(result.getThrowable().getStackTrace()));
		String logText = "<b> Test Case '" + result.getMethod().getMethodName() + "' is Skipped </b>";
		ExtentManager.logSkipDetails(logText);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String logText = "<b> Test Case '" + result.getMethod().getMethodName() + "' is Successful </b>";
		ExtentManager.logPassDetails(logText);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		String stackTrace = Arrays.toString(result.getThrowable().getStackTrace());

		stackTrace = stackTrace.replaceAll(",", "<br>");
		String formatedTrace = "<details>\n" + " <summary>Click Here to See Exception Logs</summary>\n" + " " + stackTrace + "\n" + "</details\n>";
		ExtentManager.logExceptionDetails(formatedTrace);

		Object currentClass = result.getInstance();
		AppiumDriver driver = ((AndroidBase) currentClass).driver;
		if (driver != null) {
			test.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(Screenshot.getBase64(driver)).build());
		} else {
			System.out.println("Driver is null");
		}
	}
}
