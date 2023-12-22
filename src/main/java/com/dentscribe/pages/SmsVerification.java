package com.dentscribe.pages;

import static org.testng.Assert.assertFalse;

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
	public static By textSmsVerification = By.xpath("//android.view.ViewGroup[@resource-id='sms-verification-view']//android.widget.TextView[@text='SMS Verification']");
	public By textEnterValidCode = By.xpath("//android.widget.TextView[@text='Please enter the valid code.']");
	public By linkResendCode = By.xpath("//android.view.ViewGroup[@resource-id='resend-code-button']//android.widget.TextView[@text='Resend Code']");
	public By inputSmsOtp = By.className("android.widget.EditText");
	public By iconBack = By.className("android.widget.ImageView");
	public By textViews = By.xpath("//android.widget.TextView");
	public By firstOtpBox = By.xpath("//android.widget.EditText[@resource-id='textInput'][@index=0]");

	// _______________verify whether SMS Verification page exists or not_______________
	public void validateSmsVerificationPage()
	{
		if (IsElementPresent(driver, textSmsVerification, "page header SMS Verification") && IsElementPresent(driver, linkResendCode, "Resend code link")) {
			ExtentManager.logInfoDetails("<b>User is now on SMS Verification page as expected");
		} else {
			ExtentManager.logFailureDetails("Either expected SMS Verification page verified element not found or page not exists. please check");
			Assert.fail();
		}
	}
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
	public void enterOtpAndClickContinueButton(String otp_value) throws InterruptedException 
	{
		if (otp_value != "")
		{
			CommonMethods.fillOTPBoxes(driver, otp_value);
		}
		click(driver, CommonLocators.continueButton, "Continue button on sms verification page");	
	}
	
	// ___________________validate ReSend link_______________________
	public void verifyResendCodeLink() throws InterruptedException 
	{
		click(driver, linkResendCode, "Resend Code link");
		Thread.sleep(5000);
		assertFalse(driver.getPageSource().contains("00:00"));
		ExtentManager.logInfoDetails("Timer is started again as expected");
	}
	
	// ________________click back icon_____________
	public void clickcBackIconSmsVerificationPage()
	{
		click(driver, iconBack, "Back icon on SMS Verification page");
//		if (IsElementPresent(driver, CommonLocators.labelPMS, "PMS Field")) {
//			ExtentManager.logInfoDetails("User is landed on signup page successfully as expected");
//			return true;
//		} else {
//			ExtentManager.logFailureDetails("Either signup page not opened or expected PMS field not found. Please check.");
//			return false;
//		}
	}
}