package com.dentscribe.testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonVariables;

public class TestSettingsPage extends AndroidBase {
	
	String output = null;
	String practiceName = "Dental Practice " + GenerateRandomNumber(3);
	String address1 = "Test Address " + GenerateRandomNumber(3);
	String address2 = "Test Address " + GenerateRandomNumber(3);
	
	@Test(priority = 1)
	public void verifyIsSettingsPageExists() throws IOException, InterruptedException {
		
		//_______________verify Application Launched and login_______________
		loginPage.verifyIsApplicationLaunched();
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), readData(CommonVariables.inputFileUserDetails, "password"), "valid");
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
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsSettingsPageExists" })
	public void verifyPracticeUpdate() throws InterruptedException {
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
	
	@Test(priority = 3)
	public void verifyBackIconSettingsPage()
	{
		// To click on back icon button 
		settingPage.clickBackIconSettingsPage();
		calendarPage.validateCalendarPage();
		
		// Go back to settings page
		calendarPage.clickSettingsIconCalendarPage();
		settingPage.validateSettingsPage();
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyAccountInfoUpdate() throws InterruptedException {
		
		actions.ScrollToElementText("account", "New Password");

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
		actions.ScrollToElementText("account", "New Password");
		assertEquals(getAttribute(settingPage.inputPhoneNumber), settingPage.phoneNumber);
		ExtentManager.logInfoDetails("Actual : " + getAttribute(settingPage.inputPhoneNumber) + " expected : " + settingPage.phoneNumber + " are same");
		ExtentManager.logInfoDetails("Account Info has been updated successfully and verified");
	}
	
	@Test(priority = 5, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyPushNotificationsSwitch() throws InterruptedException {
		// To verify the push notifications 
		settingPage.enableDisableNotifications("off", settingPage.switchPushNotification, "Push Notifications", CommonLocators.successMessagePushNotifications);
		settingPage.enableDisableNotifications("on", settingPage.switchPushNotification, "Push Notifications", CommonLocators.successMessagePushNotifications);
	}
	
	@Test(priority = 6, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifySmsNotificationsSwitch() throws InterruptedException {
		// To verify the sms notifications 
		settingPage.enableDisableNotifications("off", settingPage.switchSmsNotification, "SMS Notifications", CommonLocators.successMessageSmsNotifications);
		settingPage.enableDisableNotifications("on", settingPage.switchSmsNotification, "SMS Notifications", CommonLocators.successMessageSmsNotifications);
	}
	
	@Test(priority = 7, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyEmailNotificationsSwitch() throws InterruptedException {
		// To verify the email notifications 
		settingPage.enableDisableNotifications("off", settingPage.switchEmailNotification, "Email Notifications", CommonLocators.successMessageEmailNotifications);
		settingPage.enableDisableNotifications("on", settingPage.switchEmailNotification, "Email Notifications", CommonLocators.successMessageEmailNotifications);
	}
	
	@Test(priority = 8, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyHelpOptionsSettingsPage() throws InterruptedException
	{
		// ________clicked help icon on settings page and verify help page_______
		settingPage.clickHelpIcon();
		helpPage.validateHelpPage();

		// ________clicked back icon on help page and verify settings page_______
		helpPage.clickBackIconHelpPage();
		settingPage.validateSettingsPage();
		
		// ________clicked help option given in General Settings on settings page and verify help page_______
		actions.scrollUntilElementIsVisible("General Settings");
		verifyClickListOption(driver, settingPage.generalSettingsOptionsList, "Help");
		helpPage.validateHelpPage();

		// ________clicked back icon on help page and verify settings page_______
		helpPage.clickBackIconHelpPage();
		settingPage.validateSettingsPage();
	}
	
	@Test(priority = 9, dependsOnMethods = { "verifyHelpOptionsSettingsPage" })
	public void verifyFAQsInHelpPage() throws InterruptedException
	{
		verifyClickListOption(driver, settingPage.generalSettingsOptionsList, "Help");
		helpPage.validateHelpPage();
		// ________________verify whether all expected questions available_____________
		helpPage.verifyFAQsQuestions();
		helpPage.clickBackIconHelpPage();
		settingPage.validateSettingsPage();
	}
	
	@Test (priority = 10, dependsOnMethods = { "verifyFAQsInHelpPage" })
	public void verifyLogout() throws InterruptedException
	{
		settingPage.validateSettingsPage();
		// ___________________click logout________________
		verifyClickListOption(driver, settingPage.generalSettingsOptionsList, "Log Out");
		// Verify login page
		loginPage.validateLoginPage();
	}
}
