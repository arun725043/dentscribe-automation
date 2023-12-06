package com.dentscribe.testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_020_022_023_024_028_SettingsPageTest extends AndroidBase {

	String output = null;
	String practiceName = "Dental Practice " + GenerateRandomNumber(3);
	String address1 = "Test Address " + GenerateRandomNumber(3);
	String address2 = "Test Address " + GenerateRandomNumber(3);
	
	@Test (priority = 0)
	public void verifyIsSettingsPageExists() throws IOException, InterruptedException 
	{
		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
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
	}
	
	@Test (priority = 1, dependsOnMethods = { "verifyIsSettingsPageExists" })
	public void verifyBackIconSettingsPage() throws IOException, InterruptedException
	{
		// ________clicked back button on setting page and verify calendar page_______
		click(driver, settingPage.backIconSettingsPage, "Back icon settings page");
		calendarPage.validateCalendarPage();

		//Again go to settings page
		click(driver, calendarPage.iconSetting, "Settings icon on calendar page");
		settingPage.validateSettingsPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyIsBothHelpOptionsWorkingAsExpected() throws IOException, InterruptedException 
	{		
		// ________clicked help icon on settings page and verify help page_______
		click(driver, settingPage.iconHelp, "Help icon settings page");
		settingPage.validateHelpPage();

		// ________clicked back icon on help page and verify settings page_______
		click(driver, settingPage.backIconHelpPage, "Back icon Help page");
		settingPage.validateSettingsPage();
		
//		Thread.sleep(5000);
		// ________clicked help option given in General Settings on settings page and verify help page_______
		click(driver, settingPage.buttonHelp, "Help icon settings page");
		settingPage.validateHelpPage();

		// ________clicked back icon on help page and verify settings page_______
		click(driver, settingPage.backIconHelpPage, "Back icon Help page");
		settingPage.validateSettingsPage();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyPushNotificationSwitch() throws InterruptedException
	{
		settingPage.enableDisableNotifications("off", settingPage.switchPushNotification, "Push Notifications", CommonLocators.successMessagePushNotifications);
		settingPage.enableDisableNotifications("on", settingPage.switchPushNotification, "Push Notifications", CommonLocators.successMessagePushNotifications);
	}
		
	@Test (priority = 4)
	public void verifySmsNotificationSwitch() throws InterruptedException
	{
		settingPage.enableDisableNotifications("off", settingPage.switchSmsNotification, "SMS Notifications", CommonLocators.successMessageSmsNotifications);
		settingPage.enableDisableNotifications("on", settingPage.switchSmsNotification, "SMS Notifications", CommonLocators.successMessageSmsNotifications);
	}
	
	@Test (priority = 5)
	public void verifyEmailNotificationSwitch() throws InterruptedException
	{
		settingPage.enableDisableNotifications("off", settingPage.switchEmailNotification, "Email Notifications", CommonLocators.successMessageEmailNotifications);
		settingPage.enableDisableNotifications("on", settingPage.switchEmailNotification, "Email Notifications", CommonLocators.successMessageEmailNotifications);
	}

	@Test(priority = 6)
	public void verifyAccountInfoUpdate() throws InterruptedException, IOException 
	{
		actions.scrollToPartialText("Enter the password");

		// ____________________Edit phone and update______________________
		settingPage.editPhoneNumber();
		actions.scrollToPartialText("Save");
		click(driver, settingPage.buttonSave, "Save button");
		Thread.sleep(3000);

		// __________________verify updated phone number to confirm__________________
		click(driver, settingPage.backIconSettingsPage, "Back icon on settings page");
		calendarPage.validateCalendarPage();
		click(driver, calendarPage.iconSetting, "Settings icon");
		settingPage.validateSettingsPage();

		// _____________________verify number_______________________
		actions.scrollToPartialText("Enter the password");
		assertEquals(getAttribute(settingPage.inputPhoneNumber), settingPage.phoneNumber);
		ExtentManager.logInfoDetails("Actual : " + getAttribute(settingPage.inputPhoneNumber) + " expected : " + settingPage.phoneNumber + " are same");
		ExtentManager.logInfoDetails("Account Info has been updated successfully and verified");
	}
	
	@Test(priority = 7)
	public void verifyPracticeInfoUpdate() throws InterruptedException, IOException 
	{
		// __________________update info__________________
		settingPage.fillPracticeInfo(practiceName, address1, address2, "", "", "", "", "", "");
		settingPage.clickContinueButtonPracticeInfo();
		getText(CommonLocators.successMessagePracticeInfo);
		click(driver, settingPage.backIconSettingsPage, "Back icon on settings page");
		calendarPage.validateCalendarPage();
		click(driver, calendarPage.iconSetting, "Settings icon");
		settingPage.validateSettingsPage();

		// ______________________verify info___________________
		Thread.sleep(2000);
		settingPage.verifyUpdatedPracticeInfo(practiceName, address1, address2, "", "", "", "", "", "");
		ExtentManager.logInfoDetails("Practice Info has been updated successfully and verified");
	}
}
