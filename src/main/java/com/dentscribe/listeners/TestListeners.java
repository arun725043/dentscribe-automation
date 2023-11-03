package com.dentscribe.listeners;

import java.util.Arrays;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.dentscribe.reports.ExtentManager;

import io.appium.java_client.AppiumDriver;

public class TestListeners implements ITestListener{

	AppiumDriver driver;
	//This reference is used by test cases (classes) to create test steps in Report
	private static ExtentReports extent= ExtentManager.getInstance();
	private static ExtentTest test;
	//Making report to be free from multiple threads that could be formed during parallel execution
	ThreadLocal<ExtentTest> extentTest= new ThreadLocal<ExtentTest>();

	@Override
	public void onTestStart(ITestResult result) 
	{	
		test = extent.createTest(result.getTestClass().getName()+ " -> " + result.getMethod().getMethodName()); 
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) 
	{
		String logText = "<b> Test Case '" + result.getMethod().getMethodName() + "' is Successful </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		test.log(Status.PASS, m);
	}

	@Override
	public void onTestFailure(ITestResult result) {

		try {
			driver = (AppiumDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String exceptionMessage = Arrays.deepToString(result.getThrowable().getStackTrace());
		test.fail("<details><summary><b><font color = red>" 
			+ "Exception occured (Click Here)" + "</font></b></summary>"
			+ exceptionMessage.replaceAll(",", "<br>") + "</details> \n");

		//test.fail(getLog());
		String logText = "<b> Test Case '" + result.getMethod().getMethodName() + "' is Failed </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		test.log(Status.FAIL, m);
		test.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64(result.getMethod().getMethodName(), driver)).build()); 
	}
		
	@Override
	public void onTestSkipped(ITestResult result) 
	{
		String logText = "<b> Test Case  " + result.getMethod().getMethodName() + " is Skipped </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.BLUE);
		test.log(Status.SKIP, m); 
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{
	}

	@Override
	public void onStart(ITestContext context) 
	{
	}

	@Override
	public void onFinish(ITestContext context) 
	{
		if (extent != null) {
			extent.flush();
		}
	}
		
	public ExtentTest testReference() {
		return test;
	}
	
	
	public static String getBase64(String testCaseName, AppiumDriver driver)  {
		return ((TakesScreenshot)driver) .getScreenshotAs(OutputType.BASE64);
	}

}