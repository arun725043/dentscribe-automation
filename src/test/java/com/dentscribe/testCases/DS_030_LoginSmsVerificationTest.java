package com.dentscribe.testCases;

import static org.testng.Assert.assertFalse;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_030_LoginSmsVerificationTest extends AndroidBase {

	@Test
	public void validateSMSscreenTest() throws InterruptedException, IOException 
	{

		ExtentManager.logInfoDetails("Application launched successfully");
		// ______________verify home page_____________________
		assertTrue(loginPage.verifyHomePageElement());
		ExtentManager.logInfoDetails("<b>Login, Signup and Forgot password</b> button is displayed as expected");

		// login with valid credentials
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
		assertTrue(loginPage.verifyBiometricPopupButton());
		ExtentManager.logInfoDetails("<b>Skip</b> and <b>Enable</b>Button is displayed as expcted");

		// Click skip and verify SMS Verification page
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		ExtentManager.logInfoDetails("Clicked on skip on biometric popup");

		// _____________validate OTP with empty_____________
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Continue</b> without entering the OTP");
		assertTrue(IsElementPresent(signUpPage.textEnterValidCode, driver));
		Thread.sleep(20000);
		ExtentManager.logInfoDetails(getText(signUpPage.textEnterValidCode) + " message is displayed as expected");

		// ____________validate otp with wrong value_____________
		sendKeys(signUpPage.inputSmsOtp, readData("testData", "otp"), driver);
		ExtentManager.logInfoDetails("Entered wrong OTP in input field,Clicked on continue");
		click(CommonLocators.continueButton, driver);
		assertFalse(IsElementPresent(practiceInfoPage.inputName, driver));
		ExtentManager.logInfoDetails(getText(signUpPage.textEnterValidCode) + " message is displayed as expected");
		Thread.sleep(5000);

		// ___________validate resend link_______________
		click(signUpPage.linkResendCode, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Resend Code</b> link");
		Thread.sleep(5000);
		assertFalse(driver.getPageSource().contains("00:00"));
		ExtentManager.logInfoDetails("Timer is started again as expected");

		// ________________validate back button_____________
		click(signUpPage.iconBack, driver);
		ExtentManager.logInfoDetails("Click on <b>Back</b> Icon");
		assertTrue(IsElementPresent(loginPage.signupButton, driver));
		ExtentManager.logInfoDetails("User is landed on signup page successfully");
	}

}
