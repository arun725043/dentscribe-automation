package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class TestFeedbackAndNewFeedbackPages extends AndroidBase
{
	String titleString = "Testing Feedback Title - " + GenerateRandomNumber(6); 
	String descriptionString = "Testing Feedback Description - " + GenerateRandomNumber(6);
	
	@Test(priority = 1)
	public void verifyIsFeedbackPageExists() throws IOException, InterruptedException
	{	
		//_______________verify Application Launched and login_______________
		loginPage.verifyIsApplicationLaunched();
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "newUser"), readData(CommonVariables.inputFileUserDetails, "newPassword"), "valid");
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
		
		// _____________click Feedback option and validate____________
		verifyClickListOption(driver, settingPage.generalSettingsOptionsList, "Feedback");
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
