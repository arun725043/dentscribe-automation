package com.dentscribe.testCases_iOS;

import java.io.IOException;
import org.testng.annotations.Test;
import com.dentscribe.base.iOSBase;
import com.dentscribe.common.CommonMethods;

import Api.GenerateOTP;

public class TestChangePassword extends iOSBase
{	
	String newPasswordString = "Test@" + CommonMethods.GenerateRandomNumber(4);
	
	@Test(priority = 1)
	public void verifyChangePasswordFromSettingsPage() throws IOException, InterruptedException {
		
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Perform Login
		loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "currentPassword"), "sms page");

		// To fill the OTP
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification,  "Contiune on sms verification screen");

		//skip tour pages
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		
		// to click on setting icon on calendar page and verify settings page
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();
		
		// To update the password and verify login page
		settingPage.updatePassword(readData("ChangePassword", "currentPassword"), newPasswordString);
		loginPage.validateLoginPage();		// To verify that login page appears
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyChangePasswordFromSettingsPage" })
	public void verifyUserShouldNotLoginFromOldPassword() throws IOException, InterruptedException
	{
		// To verify that user is not able to login with invalid id and password
		loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "oldPassword"), "invalid");
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyChangePasswordFromSettingsPage" })
	public void verifyUserShouldLoginFromNewChangedPassword() throws IOException, InterruptedException
	{	
		// To verify that user is able to login with new id and password
		loginPage.loginApplication(readData("ChangePassword", "username"), readData("ChangePassword", "currentPassword"), "sms page");
	}
	
}
