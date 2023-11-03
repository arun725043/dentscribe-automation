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

public class DS_SoapReportTest extends AndroidBase {

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
			int[] date = calendarPage.getDateMonthYear("31-07-2023");
			calendarPage.selectMonthYear(date[0],date[1],date[2]);
			ExtentManager.logInfoDetails("Day,Month and year is selected successfully");

			// __________________________________Click patient____________________________________________________________
			calendarPage.clickPatient();
			assertTrue(calendarPage.clickVerifyStartRecording("verify", "Start"));
			ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed as expected");

			// __________________________________start Recording___________________________________________________________
			calendarPage.clickVerifyStartRecording("click", "Start");
			calendarPage.clickWhileUsingAppButton();

			// __________________________________Verify continue button after pause____________________________________________________________
			assertTrue(calendarPage.verifyStopPauseButton());
			ExtentManager.logInfoDetails("<b>Stop,Pause</b> button is displayed as expected");

			calendarPage.runAudio("", 30000);

			// ___________________________Stop Recording_______________________
			calendarPage.clickPauseStopButton("stop");
			Thread.sleep(5000);

			// ____________submit soap report__________________
			Thread.sleep(60000);
			click(By.xpath("//android.widget.TextView[@text='"+date[0]+"']"), driver);
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
