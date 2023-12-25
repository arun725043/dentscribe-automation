package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class TestSoapReportAndPatientProfilePage extends AndroidBase 
{
	String patientName = null;
	String readAppointmentsDate = readData("testData", "shortAppointmentDate");
	
	@Test (priority = 1)
	public void verifyIsRecordingPageOpenedForExpectedPatient() throws InterruptedException, IOException 
	{
		// _____________Open application_____________
		loginPage.verifyIsApplicationLaunched();
		// _____________login with valid credentials_____________
		loginPage.loginApplication(readData("UserDetails", "username"), readData("UserDetails", "password"), "valid");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		tourPages.validateTourPageCalendarScheduleView();

		// _____________select appointments date________________
		Thread.sleep(5000);
		calendarPage.selectAppointmentsDate(readAppointmentsDate);

		// _______________Click patient and verify PatientDetails PopUp opened or not_______________
		Thread.sleep(10000);
		calendarPage.clickAppointmentHasStartButton();
		calendarPage.verifyIsPatientDetailsPopupOpenedForExpectedPatient(calendarPage.patientName);
		calendarPage.clickVerifyPatientDetailsPopupButton("verify", "Start");

		// _______________start Recording and verify Recording page_______________
		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Start");
		calendarPage.clickWhileUsingAppButton();
		calendarPage.allowAccessMedia();
		calendarPage.allowAccessRecordAudio();
		recordingPage.validateRecordingPage();
		
		// _______________to verify the patient name appear on recording screen_______________
		recordingPage.verifyRecordingPageFields(calendarPage.patientName);
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsRecordingPageOpenedForExpectedPatient" })
	public void verifyBackIconRecordingPage() throws InterruptedException
	{	
		// _______________click back icon and then click cancel button on opened popup_______________
		recordingPage.clickVerifyBackIconRecordingPage("cancel");
		recordingPage.validateRecordingPage();

		// _______________click on back icon again and then click OK button this time_______________
		recordingPage.clickVerifyBackIconRecordingPage("ok");
		
		// _______________Verify the continue button on calendar view page_______________
		calendarPage.verifyPatientButton(calendarPage.patientName, "Continue");
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyBackIconRecordingPage" })
	public void verifyIsSoapReportCreated() throws InterruptedException
	{
		// _______________Click continue button and verify popup_______________
		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Continue");
		calendarPage.verifyIsPatientDetailsPopupOpenedForExpectedPatient(calendarPage.patientName);

		calendarPage.clickVerifyPatientDetailsPopupButton("verify", "Continue");
		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Continue");
		calendarPage.clickWhileUsingAppButton();
		calendarPage.allowAccessMedia();
		calendarPage.allowAccessRecordAudio();
		recordingPage.validateRecordingPage();
	
		// _______________Stop recording and verify Review status_______________
		Thread.sleep(20000);
		recordingPage.clickPauseStopButton("stop");
		calendarPage.verifyPatientButton(calendarPage.patientName, "In Progress");
		calendarPage.verifyPatientButton(calendarPage.patientName, "Review");
		soapReportPage.validateSoapReportPage();
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsSoapReportCreated" })
	public void verifyEditIconSoapReport()
	{
		//_____________verify edit icon soap report________
		soapReportPage.clickVerifyEditSaveIconSoapReport("edit");
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyEditIconSoapReport" })
	public void verifyEditSoapReportAndAddSignatureDuringEditableReport() throws InterruptedException
	{
		//_____________Update signature and verified reviewed button________
		soapReportPage.clickAdoptSignature();
		soapReportPage.addSignature();
		soapReportPage.submitSoapReport();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyEditSoapReportAndAddSignatureDuringEditableReport" })
	public void updateUserDetailsAndVerifySame()
	{
		soapReportPage.updateUserDetails();
		soapReportPage.clickVerifyEditSaveIconSoapReport("save");
		soapReportPage.verifyUpdatedUserDetails();
	}
	
	@Test (priority = 7, dependsOnMethods = { "updateUserDetailsAndVerifySame" })
	public void verifyBackIconSoapReport() throws InterruptedException
	{
		soapReportPage.validateSoapReportPage();
		soapReportPage.clickBackIconSoapReport();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 8, dependsOnMethods = { "verifyBackIconSoapReport" })
	public void verifyPatientProfilePage() throws InterruptedException
	{
		// search result should be display after searching
		calendarPage.clickSearchIconCalendarPage();
		searchPage.validatePatientSearchPage();

		// Search By patientName and verify
		searchPage.verifySearchPatientResults("name", readData("testData", "patientName"));
		Thread.sleep(10000);
		
		// To click on patient name and verify Profile page
		searchPage.clickPatientDetailsToOpenProfilePage(readData("testData", "patientName"));
		assertTrue(profilePage.validatePatientProfilePage());
		profilePage.verifyProfilePageDetails(readData("testData", "patientName"), readData("testData", "dobProfilePage"), readData("testData", "lastVisitProfilePage"));
	
	}
	
	@Test (priority = 9, dependsOnMethods = { "verifyPatientProfilePage" })
	public void verifyBackIconPatientProfilePage() throws InterruptedException
	{
		profilePage.clickBackIconPatientProfilePage();
		searchPage.validatePatientSearchPage();
	}
}
