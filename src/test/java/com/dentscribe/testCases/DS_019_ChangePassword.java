package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;


import java.io.IOException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_019_ChangePassword extends AndroidBase {

	@Test
	public void changePassword() throws IOException, InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		// ________________Logged in application__________
		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "password"), "valid"));
		ExtentManager.logInfoDetails("User is logged in successfully as expected");

		// ___________________skip tour pages________________
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		getOtp.fillOtp();
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));
		ExtentManager.logInfoDetails("Skipped the tour pages");

		// __________________Navigate to setting________________
		click(calendarPage.iconSetting, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

		// _________________input old password______________________
//		actions.scrollToText("Save");
		actions.scrollToPartialText("Enter the password");
		sendKeys(settingPage.inputCurrentPassword, readData("ChangePassword", "password"), driver);
		ExtentManager.logInfoDetails("Entered value in current password input field : " + getAttribute(settingPage.inputCurrentPassword));
		writeData("ChangePassword", "oldPassword", readData("ChangePassword", "password"));

		// ________________input new password___________________
		String pwd = settingPage.generatePassword();
		actions.scrollToPartialText("Enter the new password");
		sendKeys(settingPage.inputNewPassword, pwd, driver);
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		writeData("ChangePassword", "password", pwd);
//		actions.scrollableClick("Save");
		actions.scrollToPartialText("Save");
		click(settingPage.buttonSave, driver);

		explicitWait(driver, CommonLocators.txtUsername, 20);

		// __________________try to Login with old password___________________
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "oldPassword"), "invalid"));
		ExtentManager.logInfoDetails("User is not logged in with previous credentials as expected");

		// ___________________try to login with new password___________________
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "password"), "valid"));
		ExtentManager.logInfoDetails("User is  logged in successfully as expected");

		// _____________________verify biometric popup__________________
		assertTrue(loginPage.verifyBiometricPopupButton());
		ExtentManager.logInfoDetails("Biometric popup appearing means New Password working fine as expected.");
	}
}
