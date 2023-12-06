package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_019_ChangePassword extends AndroidBase {

	String pwd = null;
	@Test (priority = 0)
	public void verifyChangePasswordWithInvalidCurrentPassword() throws IOException, InterruptedException 
	{
		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "newPassword"), "valid"));
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.validateValidOTP(getOtp, "Tour Screen");
		
		// ______skip tour pages__________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();

		// ____clicked setting icon on calendar page and verify setting page_______
		click(driver, calendarPage.iconSetting, "Settings icon on calendar page");
		settingPage.validateSettingsPage();

		// _________________input old password______________________
		actions.scrollToPartialText("Enter the password");
		sendKeys(driver, settingPage.inputCurrentPassword, "Current Password field", "Kapoor@123");
		ExtentManager.logInfoDetails("Entered value in current password input field : " + getAttribute(settingPage.inputCurrentPassword));

		// ________________input new password___________________
		pwd = settingPage.generatePassword();
		actions.scrollToPartialText("Enter the new password");
		sendKeys(driver, settingPage.inputNewPassword, "New password field", pwd);
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		actions.scrollToPartialText("Save");
		click(driver, settingPage.buttonSave, "Save button");
		explicitWait(driver, CommonLocators.errorMessageCurrentPasswordNotMatched, 20);
		getText(CommonLocators.errorMessageCurrentPasswordNotMatched);
	}
	
	@Test(priority = 1)
	public void verifyNewPasswordWeakValidation()
	{
		clear(settingPage.inputNewPassword);
		sendKeys(driver, settingPage.inputNewPassword, "New password field", "Pass1234");
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		getText(settingPage.ErrorMsgNewPassword);
	}
	
	@Test (priority = 2)
	public void verifyChangePasswordAndUserShouldLogout() throws IOException, InterruptedException
	{
		// _________________input old password______________________
		actions.scrollToPartialText("Current Password");
		clear(settingPage.inputCurrentPassword);
		sendKeys(driver, settingPage.inputCurrentPassword, "Old Password field", readData("ChangePassword", "newPassword"));
		ExtentManager.logInfoDetails("Entered value in current password input field : " + getAttribute(settingPage.inputCurrentPassword));
		writeData("ChangePassword", "oldPassword", readData("ChangePassword", "newPassword"));

		// ________________input new password___________________
		pwd = settingPage.generatePassword();
		actions.scrollToPartialText("New Password");
		clear(settingPage.inputNewPassword);
		sendKeys(driver, settingPage.inputNewPassword, "New password field", pwd);
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		writeData("ChangePassword", "newPassword", pwd);
		actions.scrollToPartialText("Save");
		click(driver, settingPage.buttonSave, "Save button");
		explicitWait(driver, CommonLocators.inputTxtUsername, 20);
		loginPage.validateLoginPageNote();
	}
	
	@Test (priority = 3)
	public void verifyLoginWithBothOldNewPassword() throws IOException, InterruptedException
	{
		// __________________try to Login with old password___________________
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "oldPassword"), "invalid"));
		ExtentManager.logInfoDetails("User is not logged in with old password as expected");

		// ___________________try to login with new password___________________
		assertTrue(loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "newPassword"), "valid"));
		ExtentManager.logInfoDetails("User logged in successfully with New Password as expected.");
	}
}
