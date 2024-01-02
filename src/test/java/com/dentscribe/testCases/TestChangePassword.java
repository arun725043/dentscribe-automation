package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;

public class TestChangePassword extends AndroidBase
{	
	String newPasswordString = "Test@" + CommonMethods.GenerateRandomNumber(4);
	
	@Test(priority = 1)
	public void verifyChangePasswordFromSettingsPage() throws IOException, InterruptedException {
		
		//_______________verify Application Launched and login_______________
		loginPage.verifyIsApplicationLaunched();
		loginPage.loginApplication(readData(CommonVariables.inputFileChangePassword, "username"), readData(CommonVariables.inputFileChangePassword, "newPassword"), "valid");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		//______________validate OTP and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData(CommonVariables.inputFileTestData, "countryCode"), readData(CommonVariables.inputFileTestData, "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		tourPages.validateTourPageCalendarScheduleView();

		// ______________skip tour pages______________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		
		// ______________go to settings page and verify______________
		calendarPage.clickSettingsIconCalendarPage();
		settingPage.validateSettingsPage();
		
		// _________________input old password______________________
		actions.scrollToPartialText("Enter the password");
		sendKeys(driver, settingPage.inputCurrentPassword, "Current Password field", "Kapoor@123");
		ExtentManager.logInfoDetails("Entered value in current password input field : " + getAttribute(settingPage.inputCurrentPassword));

		// ________________input new password___________________
		actions.scrollToPartialText("Enter the new password");
		sendKeys(driver, settingPage.inputNewPassword, "New password field", newPasswordString);
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		actions.scrollToPartialText("Save");
		click(driver, settingPage.buttonSave, "Save button");
		explicitWait(driver, CommonLocators.errorMessageCurrentPasswordNotMatched, 20);
		getText(CommonLocators.errorMessageCurrentPasswordNotMatched);
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyChangePasswordFromSettingsPage" })
	public void verifyNewPasswordWeakValidation()
	{
		clear(settingPage.inputNewPassword);
		sendKeys(driver, settingPage.inputNewPassword, "New password field", "Pass1234");
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		getText(settingPage.ErrorMsgNewPassword);
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyChangePasswordFromSettingsPage" })
	public void verifyUserShouldNotLoginFromOldPassword() throws IOException, InterruptedException
	{
		// _________________input old password______________________
		actions.scrollToPartialText("Current Password");
		clear(settingPage.inputCurrentPassword);
		sendKeys(driver, settingPage.inputCurrentPassword, "Old Password field", readData(CommonVariables.inputFileChangePassword, "newPassword"));
		ExtentManager.logInfoDetails("Entered value in current password input field : " + getAttribute(settingPage.inputCurrentPassword));
		writeData(CommonVariables.inputFileChangePassword, "oldPassword", readData(CommonVariables.inputFileChangePassword, "newPassword"));

		// ________________input new password___________________
		actions.scrollToPartialText("New Password");
		clear(settingPage.inputNewPassword);
		sendKeys(driver, settingPage.inputNewPassword, "New password field", newPasswordString);
		ExtentManager.logInfoDetails("Entered value in New password input field : " + getAttribute(settingPage.inputNewPassword));
		writeData(CommonVariables.inputFileChangePassword, "newPassword", newPasswordString);
		actions.scrollToPartialText("Save");
		click(driver, settingPage.buttonSave, "Save button");
		explicitWait(driver, loginPage.labelUsername, 20);
		loginPage.validateLoginPage();
				
		// To verify that user is not able to login with invalid id and password
		loginPage.loginApplication(readData(CommonVariables.inputFileChangePassword, "username"), readData(CommonVariables.inputFileChangePassword, "oldPassword"), "invalid");
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyChangePasswordFromSettingsPage" })
	public void verifyUserShouldLoginFromNewChangedPassword() throws IOException, InterruptedException
	{	
		// To verify that user is able to login with new id and password
		loginPage.loginApplication(readData(CommonVariables.inputFileChangePassword, "username"), readData(CommonVariables.inputFileChangePassword, "newPassword"), "valid");
	}
	
}
