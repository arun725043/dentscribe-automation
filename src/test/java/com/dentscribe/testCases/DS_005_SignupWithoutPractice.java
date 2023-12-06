package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class DS_005_SignupWithoutPractice extends AndroidBase {

	@Test (priority = 0)
	public void verifyWishToContinuePopupBackButton() {
		try {
			//Launch application and go to Signup page and verify
			ExtentManager.logInfoDetails("Application launched successfully");
			signUpPage.verifySignupLandingPage();

			// ________fill form and verify confirmation popup___________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), "");
			signUpPage.clickVerifySignUpContinueButton("confirmation popup");
			assertTrue(signUpPage.verifyConfirmationPopupButton());

			// _____________verify back and continue button_______________
			assertTrue(signUpPage.clickSignupConfirmationPopupButtons("back"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 1, dependsOnMethods = { "verifyWishToContinuePopupBackButton" })
	public void verifyPracticeInfoMandatoryFields() {
		try {
			signUpPage.clickVerifySignUpContinueButton("confirmation popup");
			wait.until(ExpectedConditions.visibilityOfElementLocated(signUpPage.textAskForContinue));
			ExtentManager.logInfoDetails("As expected confirmation popup opened again");
			
			//_____________continue confirmation popup and verify SMS Verification page_______________
			assertTrue(signUpPage.clickSignupConfirmationPopupButtons("continue"));

			// __________________validate OTP functionality and verify expected page_____________
			String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.validateValidOTP(getOtp, "Practice form");			
			practiceInfoPage.clickPracticeContinueButton();
			practiceInfoPage.validateMandatoryFieldsErrorMessages();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyPracticeInfoMandatoryFields" })
	public void verifyLoginWithoutRegisterPractice() throws IOException, InterruptedException
	{
		Thread.sleep(5000);
		// ________________verify user should not be logged in with these credentials without filling practice form______________
		click(driver, loginPage.buttonLogin, "Login tab");
		loginPage.validateLoginPageNote();
		assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "error"));
	}
}
