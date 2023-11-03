package com.dentscribe.testCases;

import static org.testng.Assert.assertFalse;

import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;

public class DS_004_SignupSmsVerificationTest extends AndroidBase {

	@Test
	public void validateSMSscreenTest() throws InterruptedException {

		ExtentManager.logInfoDetails("Application launched successfully");
		click(signUpPage.signupButton, driver);
		ExtentManager.logInfoDetails("Clicked on Sign up button");

		// ____________________Fill signup form and verify confirmation popup button_________________
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), CommonMethods.genrateRandomFirstName() + "@gmail.com", readData("testData", "pmsName"));
		assertTrue(signUpPage.verifyConfirmationPopupButton());
		ExtentManager.logInfoDetails("<b>Back</b> and <b>Continue</b> button on confirmation popup is displayed as expected");

		assertTrue(signUpPage.clickConfirmationPopupButton("continue"));
		ExtentManager.logInfoDetails("Clicked on <b>Continue</b> Button on confirmation popup and user is redirected to SMS verification page as expected");

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
