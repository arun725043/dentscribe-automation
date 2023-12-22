package com.dentscribe.testCases_iOS;

import java.io.IOException;
import java.time.LocalTime;
import java.time.Month;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.iOSBase;

import Api.GenerateOTP;

public class TestRecordingfor50mintsAndGenerateSoapReport extends iOSBase 
{
	String patientName = null;
	
	@Test
	public void verifyWhetherSoapReportCreatedFor50mintsRecording() throws InterruptedException, IOException 
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
		int[] date = calendarPage.getDateMonthYear(readData("testData", "longAppointmentsDate"));
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
		Thread.sleep(5000);
		recordingPage.validateRecordingPage();
		System.out.println("Recording start at - " + LocalTime.now());
		ExtentManager.logInfoDetails("Recording start at - " + LocalTime.now());
		doRecordingForMinutes(driver, 50);
		System.out.println("About to click Stop button at - " + LocalTime.now());
		ExtentManager.logInfoDetails("About to click Stop button at - " + LocalTime.now());

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
}
