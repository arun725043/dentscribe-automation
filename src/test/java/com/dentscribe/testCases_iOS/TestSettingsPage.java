package com.dentscribe.testCases_iOS;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;

public class TestSettingsPage extends iOSBase{
	
	@Test(priority = 1)
	public void verifyIsSettingsPageExists() throws IOException, InterruptedException {
		
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Perform Login
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");

		// To fill the OTP
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Contiune on sms verification screen");

		//skip tour pages
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		
		// to click on setting icon on calendar page
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsSettingsPageExists" })
	public void verifyBackIconSettingsPage()
	{
		// To click on back icon button 
		settingPage.performClickBackIcon();
		calendarPage.validateCalendarPage();
		
		// Go back to settings page
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyBackIconSettingsPage" })
	public void verifyPushNotificationsSwitch() {
		// To verify the push notifications 
		settingPage.verifyNotifications("push");
	}
	
	@Test(priority = 4)
	public void verifySmsNotificationsSwitch() {
		// To verify the sms notifications 
		settingPage.verifyNotifications("sms");
	}
	
	@Test(priority = 5)
	public void verifyEmailNotificationsSwitch() {
		// To verify the email notifications 
		settingPage.verifyNotifications("email");
	}
	
	@Test(priority = 6)
	public void verifyAccountInfoUpdate() {
		
		// To change the mobile number 
		String num = settingPage.updatePhoneNumber();
		
		// To click on back icon button 
		settingPage.performClickBackIcon();
		
		// to click on setting icon on calendar page
		calendarPage.clickSettingIconButton();
				
		// to verify that setting page appears as expected
		verifyText(getText(driver, settingPage.textSetting), "Settings", "Settings title");
		
		// To verify phone number on setting page in accounting section
		settingPage.verifyPhoneNumber(num);
	}
	
	@Test(priority = 7)
	public void verifyPracticeUpdate() {
		
		// To update the city in practice form
		String city = settingPage.updateCity();
		
		// To click on back icon button 
		settingPage.performClickBackIcon();
				
		// to click on setting icon on calendar page
		calendarPage.clickSettingIconButton();
						
		// to verify that setting page appears as expected
		verifyText(getText(driver, settingPage.textSetting), "Settings", "Settings title");
		
		// to verify the updated city
		settingPage.verifyUpdatedCity(city);
	}
	
	@Test(priority = 8)
	public void verifyHelpOptionsSettingsPage()
	{
		// ________________validate Help button at Navbar_____________
		 settingPage.performClickVerifyHelpIconOnTop();
		 helpPage.validateHelpPage();
		 helpPage.performClickONBackIconButton();

		// ________________validate Help button in General Setting_____________
		 settingPage.performClickOnHelpOtion();
		 helpPage.validateHelpPage();
		 helpPage.performClickONBackIconButton();
	}
	
	@Test(priority = 9)
	public void verifyFAQsInHelpPage()
	{
		settingPage.performClickOnHelpOtion();
		 helpPage.validateHelpPage();
		// ________________verify whether all expected questions available_____________
		helpPage.verifyFAQsQuestions();
		ExtentManager.logInfoDetails("All questions are available in Help page as expected");
		helpPage.performClickONBackIconButton();
	}
	
	@Test (priority = 10)
	public void verifyLogout()
	{
		// ___________________click logout________________
		scrollUpTillElementVisible(driver, settingPage.buttonLogOut);
		 settingPage.clickOnLogout();
		 
		// Verify login page
		 verifyText(getText(driver, loginPage.userNameLabel), "Username", "Username on Login Page");
	}
}
