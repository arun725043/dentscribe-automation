package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class DS_017_018_SearchPatientAndProfileTest extends AndroidBase {

	@Test (priority = 0)
	public void verifyIsSearchPatientExists() {
		try {
			// ______________verify home page_____________________
			assertTrue(loginPage.validateLoginPageNote());
			ExtentManager.logInfoDetails("Application launched successfully");
			
			// ______________verify login with valid credentials______________
			Thread.sleep(10000);
			assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
	
			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
	
			//______________validate otp and verify expected opened page______________
			String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.validateValidOTP(getOtp, "Tour Screen");
			
			//______________skip tour pages and verify expected Calendar page opened or not______________
			tourPages.skipTourPages();
			calendarPage.validateCalendarPage();

			//______________verify Calendar page search icon and check whether Patient Search page opened or not______________
			assertTrue(calendarPage.clickCalendarPageSearchIcon());
			assertTrue(searchPage.validatePatientSearchPage());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 1, dependsOnMethods = { "verifyIsSearchPatientExists" })
	public void verifyResultResponseIfSearchedValueNotFound()
	{
		try {
			//______________search patient by invalid_________________
			searchPage.searchPatient("", "testdata");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 2, dependsOnMethods = { "verifyIsSearchPatientExists" })
	public void verifySearchWithPatientName()
	{
		try {
			//______________search by patient name_________________
			searchPage.searchPatient("name", readData("testData", "patientName"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 3, dependsOnMethods = { "verifyIsSearchPatientExists" })
	public void verifySearchWithPatientPhoneNumber()
	{
		try {	
			//______________search by patient phone number_________________
			searchPage.searchPatient("phone", readData("testData", "patientPhone"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 4, dependsOnMethods = { "verifyIsSearchPatientExists" })
	public void verifySearchWithPatientDOB()
	{
		try {	
			//______________search by patient phone number_________________
			searchPage.searchPatient("dob", readData("testData", "dobSearchPage"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 5, dependsOnMethods = { "verifyIsSearchPatientExists" })
	public void verifySearchWithPatientInsuranceName()
	{
		try {	
			//______________search by patient phone number_________________
			searchPage.searchPatient("insurance", readData("testData", "insurance"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 6, dependsOnMethods = { "verifyIsSearchPatientExists" })
	public void verifyPatientProfilePage()
	{
		try {			
			//______________verify patient profile page by patient name search_________________
			searchPage.searchPatient("name", readData("testData", "patientName"));
			searchPage.clickPatientDetailsToOpenProfilePage(readData("testData", "patientName"));
			assertTrue(profilePage.validatePatientProfilePage());
			profilePage.verifyProfilePageDetails(readData("testData", "patientName"), readData("testData", "dobProfilePage"), readData("testData", "lastVisitProfilePage"));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 7, dependsOnMethods = { "verifyPatientProfilePage" })
	public void verifyPatientProfilePageBackIcon()
	{
		try 
		{	
			//_____________verify Patient Profile back icon____________
			click(driver, profilePage.iconBackPatientProfile, "Back button of patient profile page");
			IsElementPresent(driver, searchPage.searchNote, "Search note");
			ExtentManager.logInfoDetails("<b>User is back to Patient Search page");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(priority = 8)
	public void verifySearchPatientPageBackIcon()
	{
		try 
		{
			//_____________verify Search Patient page back icon____________
			click(driver, searchPage.iconBackPatientSearch, "Back button of patient search page");
			calendarPage.validateCalendarPage();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
