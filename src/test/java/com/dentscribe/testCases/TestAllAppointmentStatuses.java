package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class TestAllAppointmentStatuses extends AndroidBase 
{	
	String readAppointmentsDate = readData("testData", "shortAppointmentDate");
	
	@Test (priority = 0)
	public void goToCalendarPageAndSelectAppointmentDate() throws IOException, InterruptedException 
	{
		loginPage.verifyIsApplicationLaunched();
		
		// _______________login into the application_______________
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
		smsVerificationPage.validateValidOTP(getOtp, "Tour Screen");
		
		// _______________Click skip and verify tour page_______________
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();
		
		//_____________select appointments date________________
		Thread.sleep(5000);
		calendarPage.selectAppointmentsDate(readAppointmentsDate);
	}
	
	@Test (priority = 1, dependsOnMethods = { "goToCalendarPageAndSelectAppointmentDate" })
	public void verifyIsPatientPopupAppearingOnStartButtonClick() throws InterruptedException 
	{
		// _______________verify PatientDetails popup opened or not_______________
		Thread.sleep(10000);
		calendarPage.clickAppointmentHasStartButton();
		calendarPage.verifyIsPatientDetailsPopupOpenedForExpectedPatient(calendarPage.patientName);
	}
	
	@Test (priority = 2, dependsOnMethods = { "verifyIsPatientPopupAppearingOnStartButtonClick" })
	public void verifyStartRecordingButtonOnPatientDetailsPopupAndClick() throws InterruptedException 
	{
		calendarPage.clickVerifyPatientDetailsPopupButton("verify", "Start");

		// _______________start Recording and verify Recording page_______________
		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Start");
		calendarPage.clickWhileUsingAppButton();
		calendarPage.allowAccessMedia();
		calendarPage.allowAccessRecordAudio();
		recordingPage.validateRecordingPage();
	}
	
	@Test (priority = 3, dependsOnMethods = { "verifyStartRecordingButtonOnPatientDetailsPopupAndClick" })
	public void verifyIsAppointmentStatusContinueAfterPauseRecording() throws InterruptedException
	{
		recordingPage.verifyRecordingPageFields(calendarPage.patientName);
		Thread.sleep(30000);
		ExtentManager.logInfoDetails("<b>Pausing recording after approx 30 seconds using Pause button");
		recordingPage.clickPauseStopButton("pause");
		calendarPage.verifyPatientButton(calendarPage.patientName, "Continue");
	}
	
	@Test (priority = 4, dependsOnMethods = { "verifyIsAppointmentStatusContinueAfterPauseRecording" })
	public void verifyIsPatientPopupAppearingOnContinueButtonClick() throws InterruptedException 
	{
		// _______________Click continue button and verify popup_______________
		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Continue");
		calendarPage.verifyIsPatientDetailsPopupOpenedForExpectedPatient(calendarPage.patientName);
	}
	
	@Test (priority = 5, dependsOnMethods = { "verifyIsPatientPopupAppearingOnContinueButtonClick" })
	public void verifyContinueRecordingButtonOnPatientDetailsPopupAndClick() throws InterruptedException
	{
		calendarPage.clickVerifyPatientDetailsPopupButton("verify", "Continue");
		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Continue");
		calendarPage.clickWhileUsingAppButton();
		calendarPage.allowAccessMedia();
		calendarPage.allowAccessRecordAudio();
		recordingPage.validateRecordingPage();
	}
	
	@Test (priority = 6, dependsOnMethods = { "verifyContinueRecordingButtonOnPatientDetailsPopupAndClick" })
	public void verifyIsAppointmentStatusReviewAfterStopRecording() throws InterruptedException
	{		
		// _______________Stop recording and verify Review status_______________
		Thread.sleep(20000);
		recordingPage.clickPauseStopButton("stop");
		calendarPage.verifyPatientButton(calendarPage.patientName, "In Progress");
		calendarPage.verifyPatientButton(calendarPage.patientName, "Review");
	}
	
	@Test (priority = 7, dependsOnMethods = { "verifyIsAppointmentStatusReviewAfterStopRecording" })
	public void addSignatureInSoapReportAndSubmit() throws InterruptedException
	{
		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Review");
		// ____________Submit soap report__________________
		getText(soapReportPage.textSoapReport);
		soapReportPage.clickAdoptSignature();
		soapReportPage.addSignature();
		soapReportPage.submitSoapReport();
		calendarPage.validateCalendarPage();
	}
	
	@Test (priority = 8, dependsOnMethods = { "addSignatureInSoapReportAndSubmit" })
	public void verifyIsAppointmentStatusReviewedAndSoapReportAfterClickIt() throws InterruptedException
	{	
		calendarPage.verifyPatientButton(calendarPage.patientName, "Reviewed");

		// ___________verify Reviewed button and check whether SOAP Report page opened or not
		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Reviewed");
	}
}
