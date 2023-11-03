package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_031_RecordingTest_with11mints extends AndroidBase {

	@Test
	public void recordingTest() {
		GenerateOtp getOtp = new GenerateOtp(driver);

		try {
			// __________________________________Login into Application__________________________________________________
			assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			ExtentManager.logInfoDetails("<b>Calender Schedule View</b> screen is displayed as expected");

			getOtp.fillOtp();
			click(CommonLocators.continueButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
			
			// __________________________________Skip tour pages__________________________________________________________
			tourPages.skipTourPages();
			wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));
			ExtentManager.logInfoDetails("User is redirected to Calendar Page successfully");

			// __________________________________Select date______________________________________________________________
			int[] date = calendarPage.getDateMonthYear(readData("testData", "recording11mintsDate"));
			calendarPage.selectMonthYear(date[0],date[1],date[2]);
			ExtentManager.logInfoDetails("Day,Month and year is selected successfully");

			// __________________________________Click patient____________________________________________________________
			calendarPage.clickPatient();
			assertTrue(calendarPage.clickVerifyStartRecording("verify", "Start"));
			ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed as expected");

			// __________________________________start Recording___________________________________________________________
			calendarPage.clickVerifyStartRecording("click", "Start");
			calendarPage.clickWhileUsingAppButton();
			calendarPage.allowAccessMedia();
			Thread.sleep(5000);
			calendarPage.closeRecordingPausePopup();
			calendarPage.runAudio("", 660000);  //11 mints

			// ___________________________Stop Recording_______________________
			calendarPage.clickPauseStopButton("stop");
			Thread.sleep(5000);

			// ____________submit soap report__________________
			assertTrue(calendarPage.verifyPatientButton(calendarPage.patientName, "Review"));
			click(By.xpath("//android.widget.TextView[@text='" + calendarPage.patientName + "']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='Review']"), driver);
			calendarPage.clickAdoptSignature();
			calendarPage.drawSignature();
			assertTrue(calendarPage.verifyPatientButton(calendarPage.patientName, "Reviewed"));

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
