package com.dentscribe.testCases_iOS;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import Api.GenerateOTP;


public class TestSignupAndLoginWithoutRegisterPractice extends iOSBase {
	
	String emailString = "kapoor.arun+auto" + CommonMethods.GenerateRandomNumber(4) + "@thinksys.com";

	@Test (priority = 1)
	public void DS_028_verifySignupWithNonSupportedPMS() throws InterruptedException 
	{
		// ___________Application launched_______________
		loginPage.verifyIsApplicationLaunched();
		signUpPage.validateSignUpPage();

		// ________fill form and verify confirmation popup___________
		signUpPage.fillSignupForm(signUpPage.getSignupDetail(), emailString, "eaglesoft");
		signUpPage.verifyNonSupportedPmsPopupAndClose();
	}
	
	@Test (priority = 2)
	public void DS_029_verifySignupWithSupportedPMS()
	{
		signUpPage.selectPracticeManagementSoftware("dentrix");
		signUpPage.clickContinueButtonSignupPage();
		signUpPage.verifyConfirmationPopup();
	}
	
	@Test (priority = 3)
	public void DS_030_verifyWishToContinuePopupBackButton()
	{		
		// _____________verify back button_______________
		signUpPage.clickConfirmationPopupButton("back");
	}
	
	@Test (priority = 4)
	public void DS_031_verifyPracticeInfoMandatoryFields()
	{
		signUpPage.clickContinueButtonSignupPage();
		signUpPage.clickConfirmationPopupButton("continue");
		// __________________validate OTP functionality_____________
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, signUpPage.smsVerificationContinueButton, "Continue");
		practiceInfoPage.validatePracticeInfoPage();
		// ____________verify mandatory fields on practice info form________
		practiceInfoPage.verifyMandatoryFields();
	}
	
	@Test (priority = 5)
	public void DS_032_verifyLoginWithoutRegisterPractice()
	{
		// ________________verify user should not be logged in with these credentials______________
		click(driver, loginPage.loginButtonBy, "Login Button");
		loginPage.performLoginWithoutPractice(CommonVariables.email, CommonVariables.actualPass);
		verifyText(getAttribute_ios(loginPage.textPracticeIsNotAuthorized, "name"), CommonVariables.errorMsgWithoutRegisterPractice, "Expected error message");
		ExtentManager.logInfoDetails("User is still on login page as expected");
	}
}
