package NeedToFixedTestCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class DS_026_027_029_AppointmentsStatusAndRecordingTest extends AndroidBase 
{	
	String readAppointmentsDate = readData("testData", "recordingTestDate");
	
//	@Test (priority = 0)
//	public void verifyCalendarViewPage() throws IOException, InterruptedException 
//	{
//		// ___________________login, skip tour pages and verify calendar view page________________
//		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
//		// Click skip and verify tour page
//		assertTrue(loginPage.clickBiometricPopupButton("skip"));
//		
//		String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
//		smsVerification.validateValidOTP(getOtp, "Tour Screen");
//		
//		tourPages.skipTourPages();
//		calendarPage.validateCalendarPage();
//	}
//	
//	@Test (priority = 1, dependsOnMethods = { "verifyCalendarViewPage" })
	@Test
	public void verifySelectAppointmentDateAndStartRecording() throws InterruptedException 
	{	
		//_____________select appointments date________________
		calendarPage.selectAppointmentsDate(readAppointmentsDate);

		// _______________Click patient and verify PatientDetails popup opened or not_______________
		Thread.sleep(10000);
		calendarPage.clickAppointmentHasStartButton();
		calendarPage.clickVerifyPatientDetailsPopupButton("verify", "Start");

//		// _______________start Recording and verify Recording page_______________
//		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Start");
//		calendarPage.clickWhileUsingAppButton();
//		calendarPage.allowAccessMedia();
//		calendarPage.allowAccessRecordAudio();
//		recordingPage.validateRecordingPage();
	}
	
//	@Test (priority = 2, dependsOnMethods = { "verifySelectAppointmentDateAndStartRecording" })
//	public void validateAndVerifyRecordingScreen() throws InterruptedException 
//	{
//		recordingPage.verifyRecordingPageDetails();
//	}
//	
//	@Test (priority = 3, dependsOnMethods = { "validateAndVerifyRecordingScreen" })
//	public void verifyBackIconRecordingScreen() throws InterruptedException
//	{
//		// _______________verify recording page Back icon functionality for both cancel and ok buttons_______________
//		Thread.sleep(30000);
//		ExtentManager.logInfoDetails("<b>Pausing recording after approx 30 seconds using Pause button");
//		recordingPage.clickVerifyBackIconRecordingPage("cancel");
//	}
//	
//	@Test (priority = 4, dependsOnMethods = { "verifyBackIconRecordingScreen" })
//	public void verifyIsAppointmentStatusContinueOnRecordingScreenBackIconClick() throws InterruptedException
//	{
//		//____________Click OK button on pause recording popup_____________ 
//		//____________and verify whether appointment status changed to Continue or not____________
//		recordingPage.clickVerifyBackIconRecordingPage("ok");
//		assertTrue(IsElementPresent(driver, calendarPage.iconDropdownCalendar, "Calendar page"));
//		ExtentManager.logInfoDetails("User is now on <b>Calendar Page<b> as expected");
//		calendarPage.verifyPatientButton(calendarPage.patientName, "Continue");
//	}
//	
//	@Test (priority = 5, dependsOnMethods = { "verifyIsAppointmentStatusContinueOnRecordingScreenBackIconClick" })
//	public void verifyIsAppointmentStatusContinueOnRecordingScreenPauseButtonClick() throws InterruptedException
//	{
//		// _________________________Again start recording and pause it using pause icon______________________
//		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Continue");
//		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Continue");
//		calendarPage.clickWhileUsingAppButton();
//		calendarPage.allowAccessMedia();
//		calendarPage.allowAccessRecordAudio();
//		recordingPage.validateRecordingPage();
//		Thread.sleep(30000);
//		ExtentManager.logInfoDetails("<b>Pausing recording after approx 30 seconds using Pause button");
//		recordingPage.clickPauseStopButton("pause");
//		calendarPage.verifyPatientButton(calendarPage.patientName, "Continue");
//	}
//	
//	@Test (priority = 6, dependsOnMethods = { "verifyIsAppointmentStatusContinueOnRecordingScreenPauseButtonClick" })
//	public void verifyIsAppointmentStatusReviewOnRecordingScreenStopButtonClick() throws InterruptedException
//	{		
//		// _________________________Again start recording and stop recording______________________
//		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Continue");
//		calendarPage.clickVerifyPatientDetailsPopupButton("click", "Continue");
//		calendarPage.clickWhileUsingAppButton();
//		calendarPage.allowAccessMedia();
//		calendarPage.allowAccessRecordAudio();
//		recordingPage.validateRecordingPage();
//		Thread.sleep(20000);
//		recordingPage.clickPauseStopButton("stop");
//		calendarPage.verifyPatientButton(calendarPage.patientName, "In Progress");
//		calendarPage.verifyPatientButton(calendarPage.patientName, "Review");
//		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Review");
//	}
//	
//	@Test (priority = 7, dependsOnMethods = { "verifyIsAppointmentStatusReviewOnRecordingScreenStopButtonClick" })
//	public void verifyIsAppointmentStatusReviewedAfterSoapReportSubmitButtonClick() throws InterruptedException
//	{
//		// ____________Submit soap report__________________
//		getText(soapReport.textSoapReport);
//		soapReport.clickAdoptSignature();
//		soapReport.addSignature();
//		soapReport.submitSoapReport();
//		calendarPage.verifyPatientButton(calendarPage.patientName, "Reviewed");
//
//		// ___________verify Reviewed button and check whether SOAP Report page opened or not
//		calendarPage.clickVerifyPatientAppointmentButton(calendarPage.patientName, "Reviewed");
//		assertTrue(soapReport.verifyNameTitleLicense());
//	}
}
