package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalTime;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonVariables;

public class TestRecordingfor50mintsAndGenerateSoapReport extends AndroidBase 
{
String readAppointmentsDate = readData(CommonVariables.inputFileTestData, "appointmentDate");
	
	@Test
	public void verifyWhetherSoapReportCreatedFor50mintsRecording() throws InterruptedException, IOException 
	{
		// _____________Open application_____________
		loginPage.verifyIsApplicationLaunched();
		// _____________login with valid credentials_____________
		loginPage.loginApplication(readData(CommonVariables.inputFileUserDetails, "username"), readData(CommonVariables.inputFileUserDetails, "password"), "valid");
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		//______________validate otp and verify expected opened page______________
		String getOtp = GetOtp.generateOTP(readData(CommonVariables.inputFileUserDetails, "countryCode"), readData(CommonVariables.inputFileUserDetails, "mobile"));
		smsVerificationPage.enterOtpAndClickContinueButton(getOtp);
		tourPages.validateTourPageCalendarScheduleView();
		
		//skip tour pages and go to calendar view page
		tourPages.skipTourPages();
		calendarPage.validateCalendarPage();

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
	
		// _______________Stop recording and verify Review status_______________
		System.out.println("Recording start at - " + LocalTime.now());
		ExtentManager.logInfoDetails("Recording start at - " + LocalTime.now());
		doRecordingForMinutes(driver, 30);
		System.out.println("About to click Stop button at - " + LocalTime.now());
		ExtentManager.logInfoDetails("About to click Stop button at - " + LocalTime.now());
		recordingPage.clickPauseStopButton("stop");
		calendarPage.verifyPatientAppointmentButton(calendarPage.patientName, "In Progress");
		calendarPage.verifyPatientAppointmentButton(calendarPage.patientName, "Review");
		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Review");
		soapReportPage.validateSoapReportPage();
	}
}
