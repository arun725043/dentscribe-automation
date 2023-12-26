package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class TestTourPages extends AndroidBase {

	@Test (priority = 1)
	public void verifyIsTourPageExists() throws InterruptedException, IOException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Fill the login form
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		tourPages.validateTourPageCalendarScheduleView();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsTourPageExists" })
	public void verifyNextButtonFromFirstToLastTourPage()
	{
		// _______________verify next button functionality___________________
		tourPages.verifyTourPagesOnNextButton("no");
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyNextButtonFromFirstToLastTourPage" })
	public void verifyBackButtonFromLastToFirstTourPage()
	{	
		// _______________verify back button functionality____________________
		tourPages.verifyTourPagesOnBackButton();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyBackButtonFromLastToFirstTourPage" })
	public void verifyNextButtonOfPatientDatabaseTourPage()
	{
		// _______________verify next button functionality____________________
		tourPages.verifyTourPagesOnNextButton("yes");
		calendarPage.validateCalendarPage();
	}

}
