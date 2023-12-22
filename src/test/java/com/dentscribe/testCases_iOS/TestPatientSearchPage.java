package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;


public class TestPatientSearchPage extends iOSBase {

	@Test (priority = 1)
	public void DS_039_verifySkipLinkTourPages() throws IOException, InterruptedException 
	{
		//__________________Application Launched_____________________
		loginPage.verifyIsApplicationLaunched();
		
		// Perform Login
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");

		// To fill the OTP
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue Button on SMS Verification page");
		tourPages.validateTourPageCalendarScheduleView();

		//skip tour pages and go to calendar view page
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 2, dependsOnMethods = { "DS_039_verifySkipLinkTourPages" } )
	public void DS_040_verifyIsPatientSearchPageExists()
	{
		// search result should be display after searching
		calendarPage.clickSearchIconCalendarPage();
		patientSearchPage.validatePatientSearchPage();
	}
	@Test (priority = 3, dependsOnMethods = { "DS_040_verifyIsPatientSearchPageExists" } )
	public void DS_041_verifyPatientSearchByInvalidData()
	{
		// to verify the in valid user
		patientSearchPage.verifyNoMatchesFound();
	}
	
	@Test (priority = 4, dependsOnMethods = { "DS_040_verifyIsPatientSearchPageExists" } )
	public void DS_042_verifyPatientSearchByPatientName()
	{
		// Search By patientName and verify
		ExtentManager.logInfoDetails("Searching By name : <b> " + readData("testData", "patientName") + "</b>");
		patientSearchPage.searchPatient(readData("testData", "patientName"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name='"+readData("testData", "patientName")+"'])[1]")));
		assertTrue(patientSearchPage.verifySearchedPatient(readData("testData", "patientName")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}
	
	@Test (priority = 5, dependsOnMethods = { "DS_040_verifyIsPatientSearchPageExists" } )
	public void DS_043_verifyPatientSearchByPatientPhoneNumber()
	{
		// Search By patientMobile and verify
		ExtentManager.logInfoDetails("Searching By mobile : <b> " + readData("testData", "patientPhone") + "</b>");
		patientSearchPage.searchPatient(readData("testData", "patientPhone"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData("testData", "patientPhone")+"'])[1]")));   	

		String no = driver.findElements(By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData("testData", "patientPhone")+"'])[1]")).get(0).getText();
		assertEquals(no.trim(), readData("testData", "patientPhone"));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}
	
	@Test (priority = 6, dependsOnMethods = { "DS_040_verifyIsPatientSearchPageExists" } )
	public void DS_044_verifyPatientSearchByPatientDOB()
	{
		// Search By dob and verify
		ExtentManager.logInfoDetails("Searching By DOB : <b> " + readData("testData", "dob") + "</b>");
		patientSearchPage.searchPatient(readData("testData", "dob"));
		
		assertTrue(patientSearchPage.verifySearchedPatient(readData("testData", "dob")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}

	@Test (priority = 7, dependsOnMethods = { "DS_040_verifyIsPatientSearchPageExists" } )
	public void DS_045_verifyPatientSearchByPatientInsurance()
	{
		// Search By insurance and verify
		ExtentManager.logInfoDetails("Searching By insurance : <b> " + readData("testData", "insurance") + "</b>");
		patientSearchPage.searchPatient(readData("testData", "insurance"));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData("testData", "insurance")+" '])[1]")));
		assertTrue(IsElementPresent(driver, By.xpath("(//XCUIElementTypeStaticText[@name=' "+readData("testData", "insurance")+" '])[1]")));
		ExtentManager.logInfoDetails("Search Result is displayed as per the given input");
	}
}
