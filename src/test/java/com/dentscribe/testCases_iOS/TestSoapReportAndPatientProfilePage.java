package com.dentscribe.testCases_iOS;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Month;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;
import Api.GenerateOTP;
import io.appium.java_client.AppiumBy;

public class TestSoapReportAndPatientProfilePage extends iOSBase 
{
	String patientName = null;
	
	@Test (priority = 1)
	public void verifyIsRecordingPageOpenedForExpectedPatient() throws InterruptedException, IOException 
	{
		loginPage.verifyIsApplicationLaunched();
		
		// __________________________________Login into Application__________________________________________________
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "sms page");
		
		GenerateOTP.fillOtp(driver, GenerateOTP.getOTP());
		click(driver, loginPage.continueButtonSMSVerification, "Continue on sms verification page");
		
		// __________________________________Skip tour pages__________________________________________________________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();

		// __________________________________Select date______________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		int[] date = calendarPage.getDateMonthYear(readData("testData", "shortAppointmentsDate"));
		Month month = Month.of(date[1]);    
		month.toString();
 		click(driver, calendarPage.dropdownIconCalendar, "Calendar Dropdown");
		ExtentManager.logInfoDetails("Clicked on Calendar dropdown");
		calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
		ExtentManager.logInfoDetails("Day, Month and year is selected successfully");
		click(driver, calendarPage.doneButtonCalendarPopup, "Done");

		// __________________________________Click patient____________________________________________________________
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickPatient("Start");
		calendarPage.verifyPatientDetailsPopup();
		patientName = getText(driver, calendarPage.patientNameOnPatientDetailsPopup);
		patientName = patientName.trim();
		ExtentManager.logInfoDetails("Selected patient name - <b>" + patientName + "</b> for recording on calendar view page");
		
		calendarPage.startRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(10000);
		recordingPage.validateRecordingPage();
		
		// to verify the patient name appear on recording screen;
		verifyText(getText(driver, AppiumBy.accessibilityId(patientName)), patientName, patientName);
		ExtentManager.logInfoDetails("Recording page successfully opened for patient - <b>" + patientName);
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsRecordingPageOpenedForExpectedPatient" })
	public void verifyBackIconRecordingPage() throws InterruptedException
	{	
		// to click on back icon button 
		recordingPage.perfromClickBackIconButton();
		
		// to verify that 'Do you want to pause the recording ?' popup appears 
		verifyText(getText(driver, recordingPage.doYouWantPausePopup), "Do you want to pause the recording ?", "Do you want to pause the recording ? popup on reocrding page");
		
		// to click on 'Cancel' button 'Do you want to pause the recording ?' popup
		recordingPage.performclick_OK_Cancel("cancel");
		
		// to verify the patient name appear on recording screen;
		verifyText(getText(driver, AppiumBy.accessibilityId(patientName)), patientName, patientName+": Appears as expected");

		// to click on back icon button
		recordingPage.perfromClickBackIconButton();
		 	
		// to click on 'OK' button 'Do you want to pause the recording ?' popup
		recordingPage.performclick_OK_Cancel("ok");
		
		// Verify the continue button on calendar view page
		calendarPage.verifyClickContinueButtonForAppointment("verify", patientName);
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyBackIconRecordingPage" })
	public void verifyIsSoapReportCreated() throws InterruptedException
	{
		calendarPage.clickPatient("Continue");
		calendarPage.continueRecordingButtonOnPatientDetailsPopup("click");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		calendarPage.clickWhileUsingAppButton();
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
		
		// ___________________________Stop Recording_______________________
		recordingPage.clickPauseStopButton("stop");
		waitUntilLoaderDisappear(driver);
		System.out.println("Loading Done");
		
		//verify review button
		calendarPage.verifyClickReviewButtonForAppointment("verify", patientName);
		// ____________click review button and go to soap report__________________
		calendarPage.verifyClickReviewButtonForAppointment("click", patientName);
		soapReportPage.validateSoapReportPage();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsSoapReportCreated" })
	public void verifyEditIconSoapReport()
	{
		//_____________verify edit icon soap report________
		click(driver, soapReportPage.iconEditSoapReportPage, "Edit icon on Soap report page");
		assertTrue(IsElementPresent(driver, soapReportPage.iconSaveSoapReportPage));
		ExtentManager.logInfoDetails("Save icon visible on Edit icon click as expected");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyEditIconSoapReport" })
	public void verifyEditSoapReportAndAddSignatureDuringEditableReport() throws InterruptedException
	{
		//_____________Update signature and verified reviewed button________	
		soapReportPage.scrollToAdoptSignatureButton();
		click(driver, soapReportPage.buttonAdoptSignature, "Adopt Signature on Soap report page");
		click(driver, soapReportPage.buttonSubmitAdoptSignaturePopup, "Submit");
		assertTrue(IsElementPresent(driver, soapReportPage.labelSignature));
		ExtentManager.logInfoDetails("Signature submitted successfully");
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyEditSoapReportAndAddSignatureDuringEditableReport" })
	public void updateUserDetailsAndVerifySame()
	{
		soapReportPage.updateUserDetails();
		click(driver, soapReportPage.iconSaveSoapReportPage, "Save icon on Soap report page");
		assertTrue(IsElementPresent(driver, soapReportPage.iconEditSoapReportPage));
		ExtentManager.logInfoDetails("Edit icon visible on Save icon click as expected and report submitted");
		soapReportPage.verifyUpdatedFields();
	}
	
	@Test (priority = 7, dependsOnMethods = { "updateUserDetailsAndVerifySame" })
	public void verifyBackIconSoapReportAndReviewedButtonForAppointment() throws InterruptedException
	{
		soapReportPage.validateSoapReportPage();
		soapReportPage.clickBackIconSoapReport();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 8, dependsOnMethods = { "verifyBackIconSoapReportAndReviewedButtonForAppointment" })
	public void verifyPatientProfilePage()
	{
		// search result should be display after searching
		calendarPage.clickSearchIconCalendarPage();
		patientSearchPage.validatePatientSearchPage();

		// Search By patientName and verify
		ExtentManager.logInfoDetails("Searching By name : <b> " + readData("testData", "patientName") + "</b>");
		patientSearchPage.searchPatient(readData("testData", "patientName"));
		waitUntilLoaderDisappear(driver);
		
		// To click on patient name and verify Profile page
		patientSearchPage.performClickONPatient();
		patientProfilePage.validatePatientProfilePage();
		
		// To verify the patient name 
		assertTrue(patientProfilePage.verifyPatientName(readData("testData", "patientName")));
		ExtentManager.logInfoDetails(readData("testData", "patientName")+": Profile Opened as expected");
	}
	
	@Test (priority = 9, dependsOnMethods = { "verifyPatientProfilePage" })
	public void verifyBackIconPatientProfilePage() throws InterruptedException
	{
		patientProfilePage.clickBackIconPatientProfilePage();
		patientSearchPage.validatePatientSearchPage();
	}
	
}
