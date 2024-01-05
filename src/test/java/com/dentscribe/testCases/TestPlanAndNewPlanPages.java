package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.testng.annotations.Test;

import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class TestPlanAndNewPlanPages extends AndroidBase
{
	String codeString = "D" + GenerateRandomNumber(4); 
	String keyString = "External Oral Plan Key- " + GenerateRandomNumber(6); 
	String descriptionString = "Testing Plan Description - " + GenerateRandomNumber(6);
	String planString = "Testing Plan";
	
	@Test(priority = 1)
	public void verifyIsPlanPageExists() throws IOException, InterruptedException
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
		
		// _____________click Plan option and validate____________
		verifyClickListOption(driver, settingPage.generalSettingsOptionsList, "Plan");
		planPage.validatePlanPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsPlanPageExists" })
	public void verifyIsExpectedFieldsExistsOnPlanPage()
	{
		IsElementPresent(driver, planPage.iconNewPlanBy, "Icon New Plan on Plan page");
		IsElementPresent(driver, planPage.iconResetPlanBy, "Icon Reset Plan on Plan page");
		IsElementPresent(driver, planPage.iconBackPlanPageBy, "Back icon on Plan page");
		IsElementPresent(driver, planPage.inputSearchBoxBy, "Search textbox on Plan page");
		IsElementPresent(driver, planPage.textMasterPLan, "Master Plan heading on Plan page");
	}

	@Test (priority = 3, dependsOnMethods = { "verifyIsExpectedFieldsExistsOnPlanPage" })
	public void verifyIsNewPlanPageExists()
	{
		planPage.clickNewPlanIcon();
		planPage.validateNewPlanPage();
	}

	@Test (priority = 4, dependsOnMethods = { "verifyIsNewPlanPageExists" })
	public void verifyIsExpectedFieldsExistsOnNewPlanPage()
	{
		IsElementPresent(driver, planPage.iconBackNewPlanPageBy, "Icon back on new Plan page");
		IsElementPresent(driver, planPage.labelProcedureCodeBy, "Label Procedure Key on new Plan page");
		IsElementPresent(driver, planPage.labelProcedureKeyBy, "Label Procedure Code on new Plan page");
		IsElementPresent(driver, planPage.labelDescriptionBy, "Label Description on new Plan page");
		IsElementPresent(driver, planPage.labelProcedurePlanBy, "Label Plan on new Plan page");
		actions.scrollToText("Submit");
		IsElementPresent(driver, planPage.buttonSubmitNewPlanPageBy, "Submit button on new Plan page");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsExpectedFieldsExistsOnNewPlanPage" })
	public void verifyBackIconNewPlanPage()
	{
		planPage.clickBackIconNewPlanPage();
		planPage.validatePlanPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyBackIconNewPlanPage" })
	public void createNewPlanAndVerify() throws InterruptedException
	{
		planPage.clickNewPlanIcon();
		planPage.validateNewPlanPage();
		planPage.fillPlanFormAndSubmit(codeString, keyString, descriptionString, planString);
		planPage.validatePlanPage();
		planPage.verifyNewlyAddedPlan(codeString, keyString, descriptionString);
	}
	
	@Test (priority = 7, dependsOnMethods = { "createNewPlanAndVerify" })
	public void verifyBackIconPlanPage()
	{
		planPage.clickBackIconPlanPage();
		settingPage.validateSettingsPage();
	}
}
