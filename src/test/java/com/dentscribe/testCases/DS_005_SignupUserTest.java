package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class DS_005_SignupUserTest extends AndroidBase {

	@Test
	public void signupWithoutPracticeForm() {

		GenerateOtp getOtp = new GenerateOtp(driver);
		try {
			// ___________Application launched_______________
			ExtentManager.logInfoDetails("Application is launched successfully");
			click(signUpPage.signupButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Signup</b> button");

			// ________fill form and verify confirmation popup___________
			signUpPage.fillSignupForm(signUpPage.getSignupDetail(), CommonMethods.genrateRandomFirstName() + CommonMethods.GenerateRandomNumber(2) + "@gmail.com", readData("testData", "pmsName"));
			assertTrue(signUpPage.verifyConfirmationPopupButton());
			ExtentManager.logInfoDetails("<b>Back</b> and <b>Continue</b> button on confirmation popup is displayed as expected");

			// _____________verify back and continue button_______________
			assertTrue(signUpPage.clickConfirmationPopupButton("back"));
			ExtentManager.logInfoDetails("Clicked on <b>Back</b> Button on confirmation popup and user is redirected to signup landing page as expected");
			actions.scrollToText("Continue");
			click(CommonLocators.continueButton, driver);
			wait.until(ExpectedConditions.visibilityOfElementLocated(signUpPage.textAskForContinue));
			ExtentManager.logInfoDetails("Clicked on continue and confirmation popup opened as expected");

			assertTrue(signUpPage.clickConfirmationPopupButton("continue"));
			ExtentManager.logInfoDetails("Clicked on <b>Continue</b> Button on confirmation popup and user is redirected to SMS verification page as expected");

			// __________________validate OTP functionality_____________
			getOtp.fillOtp();
			click(CommonLocators.continueButton, driver);
			wait.until(ExpectedConditions.visibilityOfElementLocated(practiceInfoPage.inputName));
			ExtentManager.logInfoDetails("Clicked on Continue button, practice info page is displayed as expected");

			// ________________verify user should not be logged in with these credentials______________
			click(loginPage.buttonLogin, driver);
			ExtentManager.logInfoDetails("Clicked on Login button");
			assertTrue(loginPage.loginApplication(CommonVariables.email, CommonVariables.actualPass, "invalid"));
			ExtentManager.logInfoDetails("User is not redirected to Biometric popup as expected");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
