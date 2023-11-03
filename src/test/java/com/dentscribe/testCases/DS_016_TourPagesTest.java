package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_016_TourPagesTest extends AndroidBase {

	@Test
	public void tourPageTest() throws InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		try 
		{
			assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));

			// ___________Click on Skip and verify calendar schedule page screen_______________
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			ExtentManager.logInfoDetails("Clicked on <b>Skip</b> button , <b>SMS Verification</b> screen is displayed as expected");

			getOtp.fillOtp();
			click(CommonLocators.continueButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
			
			assertTrue(tourPages.verifyTourPagesOnNextButton("calendar"));
			ExtentManager.logInfoDetails("Clicked on 'Next', <b>Paitent List View</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnNextButton("patient view"));
			ExtentManager.logInfoDetails("Clicked on 'Next', <b>Paitent Profile</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnNextButton("patient profile"));
			ExtentManager.logInfoDetails("Clicked on 'Next' , <b>Recording</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnNextButton("recording"));
			ExtentManager.logInfoDetails("Clicked on 'Next', <b>SOAP Report</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnNextButton("soap report"));
			ExtentManager.logInfoDetails("Clicked on 'Next' , <b>Patient Database Integration</b> screen is displayed as expected");

			// _______________verify back button functionality____________________
			assertTrue(tourPages.verifyTourPagesOnBackButton("patientDatabase"));
			ExtentManager.logInfoDetails("Clicked on 'Back', <b>SOAP Report</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnBackButton("soap report"));
			ExtentManager.logInfoDetails("Clicked on 'Back', <b>Recording</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnBackButton("recording"));
			ExtentManager.logInfoDetails("Clicked on 'Back', <b>Paitent Profile</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnBackButton("patient profile"));
			ExtentManager.logInfoDetails("Clicked on 'Back', <b>Paitent List View</b> screen is displayed as expected");

			assertTrue(tourPages.verifyTourPagesOnBackButton("patient view"));
			ExtentManager.logInfoDetails("Clicked on 'Back', <b>Calender Schedule View</b> screen is displayed as expected");

			// ________________________swipe left functionality___________________________

			tourPages.swipeTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textParentListView, driver));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Paitent List View</b> screen is displayed as expected");

			tourPages.swipeTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textPatientProfile, driver));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Paitent Profile</b> screen is displayed as expected");

			tourPages.swipeTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textRecording, driver));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Recording</b> screen is displayed as expected");

			tourPages.swipeTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textSoapReport, driver));
			ExtentManager.logInfoDetails("Swipe left and verify <b>SOAP Report</b> screen is displayed as expected");

			tourPages.swipeTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textPatientDatabase, driver));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Patient Database Integration</b> screen is displayed as expected");

			tourPages.swipeTourScreen(driver);
			Thread.sleep(3000);
			assertTrue(IsElementPresent(tourPages.textPatientDatabase, driver));
			ExtentManager.logInfoDetails("Swipe left, Still user is on <b>Patient Database Integration</b> screen as expected");

			// ________________________swipe right functionality___________________________

			tourPages.swipeRightTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textSoapReport, driver));
			ExtentManager.logInfoDetails("Swipe right and verify <b>SOAP Report</b> screen is displayed as expected");

			tourPages.swipeRightTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textRecording, driver));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Recording</b> screen is displayed as expected");

			tourPages.swipeRightTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textPatientProfile, driver));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Paitent Profile</b> screen is displayed as expected");

			tourPages.swipeRightTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textParentListView, driver));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Paitent List View</b> screen is displayed as expected");

			tourPages.swipeRightTourScreen(driver);
			assertTrue(IsElementPresent(tourPages.textCalendarSchecule, driver));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Calendar schedule</b> screen is displayed as expected");

			tourPages.swipeRightTourScreen(driver);
			Thread.sleep(3000);
			assertTrue(IsElementPresent(tourPages.textCalendarSchecule, driver));
			ExtentManager.logInfoDetails("Swipe right, Still user is on <b>Calendar schedule view</b> screen as expected");

		} catch (Exception e) {
			e.getMessage();
			Assert.fail();
		}
	}

}
