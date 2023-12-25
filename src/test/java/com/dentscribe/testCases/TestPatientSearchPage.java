package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;


public class TestPatientSearchPage extends AndroidBase {

	@Test (priority = 1)
	public void verifySkipLinkTourPages() throws IOException, InterruptedException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Fill the login form and go to SMS verification
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		smsVerificationPage.validateSmsVerificationPage();
		
		//______________validate otp and go to tour page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		tourPages.validateTourPageCalendarScheduleView();

		//skip tour pages and go to calendar view page
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifySkipLinkTourPages" } )
	public void verifyIsPatientSearchPageExists()
	{
		// search result should be display after searching
		calendarPage.clickSearchIconCalendarPage();
		searchPage.validatePatientSearchPage();
	}
	@Test (priority = 3, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByInvalidData() throws InterruptedException
	{
		// to verify the in valid user
		searchPage.verifySearchPatientResults("", "testingdata");
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientName() throws InterruptedException
	{
		// Search By patientName and verify
		searchPage.verifySearchPatientResults("name", readData("testData", "patientName"));
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientPhoneNumber() throws InterruptedException
	{
		// Search By patientMobile and verify
		searchPage.verifySearchPatientResults("mobile", readData("testData", "patientPhone"));
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientDOB() throws InterruptedException
	{
		// Search By dob and verify
		searchPage.verifySearchPatientResults("dob", readData("testData", "dob"));
		
		assertTrue(searchPage.verifySearchedPatient(readData("testData", "dob")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}

	@Test (priority = 7, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyPatientSearchByPatientInsurance() throws InterruptedException
	{
		// Search By insurance and verify
		searchPage.verifySearchPatientResults("insurance", readData("testData", "insurance"));
	}
	
	@Test(priority = 8, dependsOnMethods = { "verifyIsPatientSearchPageExists" } )
	public void verifyBackIconSearchPatientPage()
	{
		//_____________verify Search Patient page back icon____________
		searchPage.clickBackIconPatientSearchPage();
		calendarPage.validateCalendarPage();
	}
}
