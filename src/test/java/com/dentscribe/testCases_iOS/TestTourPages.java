package com.dentscribe.testCases_iOS;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.base.iOSBase;

import Api.GenerateOTP;

public class TestTourPages extends iOSBase {

	@Test (priority = 1)
	public void DS_033_verifyIsTourPageExists() throws InterruptedException, IOException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Fill the login form
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");

		// Fill the OTP and continue
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		
		// Verify that tour page appears as expected
		verifyText(getText(driver, loginPage.txtCalenerScheduleView), "Calendar Schedule View", "Calendar Schedule View");
	}
	
	@Test (priority = 2, dependsOnMethods = { "DS_033_verifyIsTourPageExists" })
	public void DS_034_verifyNextButtonFromFirstToLastTourPage()
	{
		// _______________verify next button functionality____________________
		 tourPages.verifyNextButtonFunctionality("no");
	}
	
	@Test (priority = 3, dependsOnMethods = { "DS_034_verifyNextButtonFromFirstToLastTourPage" })
	public void DS_035_verifyBackButtonFromLastToFirstTourPage()
	{	
		// _______________verify back button functionality____________________
		tourPages.verifyBackButtonFunctionality();
	}
	
	@Test (priority = 4, dependsOnMethods = { "DS_035_verifyBackButtonFromLastToFirstTourPage" })
	public void DS_036_verifyRightSwipeFromFirstToLastTourPage()
	{
		// ________________________swipe right functionality__________________
		tourPages.verifySwipeRightFunctionality();
	}
	
	@Test (priority = 5, dependsOnMethods = { "DS_036_verifyRightSwipeFromFirstToLastTourPage" })
	public void DS_037_verifyLeftSwipeFromLastToFirstTourPage()
	{	
		// ________________________swipe left functionality___________________
		tourPages.verifySwipeLeftFunctionality();
	}
	
	@Test (priority = 6, dependsOnMethods = { "DS_037_verifyLeftSwipeFromLastToFirstTourPage" })
	public void DS_038_verifyNextButtonOfPatientDatabaseTourPage()
	{
		// _______________verify next button functionality____________________
		 tourPages.verifyNextButtonFunctionality("yes");
		 calendarPage.validateCalendarPage();
	}

}
