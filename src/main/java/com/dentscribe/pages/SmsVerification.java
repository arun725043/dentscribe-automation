package com.dentscribe.pages;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;

public class SmsVerification extends AndroidActions {

	AndroidDriver driver;
	public boolean flag = false;

	public SmsVerification(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	
	// _________locators______
	public static By textSmsVerification = By.xpath("//android.widget.TextView[@text='SMS Verification']");
	public By textEnterValidCode = By.xpath("//android.widget.TextView[@text='Please enter the valid code.']");
	public By linkResendCode = By.xpath("//android.widget.TextView[@text='Resend Code']");
	public By inputSmsOtp = By.className("android.widget.EditText");
	public By iconBack = By.className("android.widget.ImageView");
	public By textViews = By.xpath("//android.widget.TextView");
	public By firstOtpBox = By.xpath("//android.widget.EditText[@resource-id='textInput'][@index=0]");
	
	// _____________validate OTP with blank or wrong/invalid values_____________
	public void validateErrorMessageForBlankWrongOTP(String otp_value) throws InterruptedException 
	{
		if (otp_value != "")
		{
			sendKeys(driver, inputSmsOtp, "OTP", otp_value);
		}
		else {
			ExtentManager.logInfoDetails("User not provided any otp so testing with blank otp values.");
		}
		click(driver, CommonLocators.continueButton, "Continue button");
		Thread.sleep(2000);
		if(IsElementPresent(driver, textEnterValidCode, "Error message"))
		{
			ExtentManager.logInfoDetails("Expected error message found :- " + CommonVariables.otpErrorMessageBlank);
		}
		else {
			ExtentManager.logFailureDetails("Expected error message <b>" + CommonVariables.otpErrorMessageBlank + "<b> not found");
		}
	}
	
	// _____________validate OTP with valid values_____________
	public void validateValidOTP(String otp_value, String expectedScreen) throws InterruptedException 
	{
		if (otp_value != "")
		{
			CommonMethods.fillOTPBoxes(driver, otp_value);
		}
		click(driver, CommonLocators.continueButton, "Continue button on sms verification page");
		Thread.sleep(5000);
		if (expectedScreen.equalsIgnoreCase("TOUR SCREEN"))
		{
			assertTrue(IsElementPresent(driver, CommonLocators.pageCalendarScheduleViewText, "Calendar Schedule View"));
			ExtentManager.logInfoDetails("User is now on <b>Calendar Schedule View</b> tour page as expected");
		}
		else if (expectedScreen.equalsIgnoreCase("EULA SCREEN"))
		{
			assertTrue(IsElementPresent(driver, CommonLocators.pageEulaAgreementHeader, "EULA Agreement screen"));
			ExtentManager.logInfoDetails("User is now on <b>EULA Agreement</b> page as expected");
		}
		else if (expectedScreen.equalsIgnoreCase("PRACTICE FORM"))
		{
			assertTrue(IsElementPresent(driver, CommonLocators.pagePracticeInfoHeader, "Practice Info form"));
			ExtentManager.logInfoDetails("User is now on <b>Practice Info</b> page as expected");
		}
		else {
			ExtentManager.logFailureDetails("Please enter valid expected screen/element name");
			Assert.fail();
		}	
	}
	
	// ___________________validate ReSend link_______________________
	public void verifyResendCodeLink() throws InterruptedException 
	{
		click(driver, linkResendCode, "Resend Code link");
		Thread.sleep(5000);
		assertFalse(driver.getPageSource().contains("00:00"));
		ExtentManager.logInfoDetails("Timer is started again as expected");
	}
	
	// ________________validate back button_____________
	public boolean verifyBackIcon(String expectedPageName)
	{
		click(driver, iconBack, "Back icon");
		ExtentManager.logInfoDetails("<b>Back</b> icon clicked on SMS Verification page");
		if(expectedPageName == "login")
		{
			if (IsElementPresent(driver, CommonLocators.labelUsername, "Username field")) {
				ExtentManager.logInfoDetails("User is landed on <b>login page<b> successfully as expected");
				return true;
			} else {
				ExtentManager.logFailureDetails("Either login page not opened or expected Username field not found. Please check.");
				return false;
			}
		}
		else if(expectedPageName == "signup")
		{
			if (IsElementPresent(driver, CommonLocators.labelPMS, "PMS Field")) {
				ExtentManager.logInfoDetails("User is landed on signup page successfully as expected");
				return true;
			} else {
				ExtentManager.logFailureDetails("Either signup page not opened or expected PMS field not found. Please check.");
				return false;
			}
		}
		else {
			ExtentManager.logFailureDetails("Expected pagename could be 'login' or 'signup'. Please check.");
			return false;
		}
	}
}