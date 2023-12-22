package com.dentscribe.testCases_iOS;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;

public class TestFeedbackAndNewFeedbackPages extends iOSBase
{
	String titleString = "Testing Feedback Title - " + GenerateRandomNumber(6); 
	String descriptionString = "Testing Feedback Description - " + GenerateRandomNumber(6);
	
	@Test(priority = 1)
	public void verifyIsFeedbackPageExists() throws IOException, InterruptedException {
		
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Perform Login
		loginPage.loginApplication(readData("UserDetails", "newuser"), readData("UserDetails", "newpassword"), "valid");

		// To fill the OTP
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Contiune on sms verification screen");

		//skip tour pages
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		
		// to click on setting icon on calendar page
		calendarPage.clickSettingIconButton();
		settingPage.validateSettingsPage();
		
		// ________________validate Feedback option in General Setting_____________
		settingPage.clickOnFeedbackOption();
		feedbackPage.validateFeedbackPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsFeedbackPageExists" })
	public void verifyIsExpectedFieldsExistsOnFeedbackPage()
	{
		IsElementPresent(driver, feedbackPage.headerTextFeedbackPageBy, "Header text feedback page");
		IsElementPresent(driver, feedbackPage.iconNewFeedbackBy, "Icon New Feedback on feedback page");
		IsElementPresent(driver, feedbackPage.iconBackFeedbackPageBy, "Back icon on feedback page");
		IsElementPresent(driver, feedbackPage.msgNoFeedbacksBy, "No feedback found. message on feedback page");
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyIsExpectedFieldsExistsOnFeedbackPage" })
	public void verifyIsNewFeedbackPageExists()
	{
		feedbackPage.clickNewFeedbackIcon();
		feedbackPage.validateNewFeedbackPage();
	}

	@Test (priority = 4, dependsOnMethods = { "verifyIsNewFeedbackPageExists" })
	public void verifyIsExpectedFieldsExistsOnNewFeedbackPage()
	{
		IsElementPresent(driver, feedbackPage.headerTextNewFeedbackPageBy, "Header text new feedback page");
		IsElementPresent(driver, feedbackPage.iconBackNewFeedbackPageBy, "Icon back on new feedback page");
		IsElementPresent(driver, feedbackPage.labelTitleBy, "Label title on new feedback page");
		IsElementPresent(driver, feedbackPage.labelDescriptionBy, "label description on new feedback page");
		IsElementPresent(driver, feedbackPage.linkAddAttachmentBy, "Add Attachment link on new feedback page");
		IsElementPresent(driver, feedbackPage.buttonSubmitNewFeedbackPageBy, "Submit button on new feedback page");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsExpectedFieldsExistsOnNewFeedbackPage" })
	public void verifyBackIconNewFeedbackPage()
	{
		feedbackPage.clickBackIconNewFeedbackPage();
		feedbackPage.validateFeedbackPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyBackIconNewFeedbackPage" })
	public void createNewFeedbackAndVerify() throws InterruptedException
	{
		feedbackPage.clickNewFeedbackIcon();
		feedbackPage.validateNewFeedbackPage();
		feedbackPage.fillFeedbackFormAndSubmit(titleString, descriptionString);
		feedbackPage.validateFeedbackPage();
		feedbackPage.verifyNewlyAddedFeedback(titleString, descriptionString);
	}
	
	@Test (priority = 7, dependsOnMethods = { "createNewFeedbackAndVerify" })
	public void verifyBackIconFeedbackPage()
	{
		feedbackPage.clickBackIconFeedbackPage();
		settingPage.validateSettingsPage();
	}
}
