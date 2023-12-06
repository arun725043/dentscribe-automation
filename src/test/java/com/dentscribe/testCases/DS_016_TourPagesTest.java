package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class DS_016_TourPagesTest extends AndroidBase 
{
	@Test
	public void tourPageTest() throws InterruptedException {
		try 
		{
			// ______________verify home page_____________________
			assertTrue(loginPage.validateLoginPageNote());
			ExtentManager.logInfoDetails("Application launched successfully");
			
			// ______________verify login with valid credentials______________
			Thread.sleep(10000);
			assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
	
			// Click skip and verify tour page
			assertTrue(loginPage.clickBiometricPopupButton("skip"));
	
			//______________validate otp and verify expected opened page______________
			String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
			smsVerificationPage.validateValidOTP(getOtp, "Tour Screen");
			
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

//			tourPages.swipeTourScreen(driver);
			tourPages.swipe("left");
			assertTrue(IsElementPresent(driver, tourPages.textParentListView, ""));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Paitent List View</b> screen is displayed as expected");

//			tourPages.swipeTourScreen(driver);
			tourPages.swipe("left");
			assertTrue(IsElementPresent(driver, tourPages.textPatientProfile, ""));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Paitent Profile</b> screen is displayed as expected");

//			tourPages.swipeTourScreen(driver);
			tourPages.swipe("left");
			assertTrue(IsElementPresent(driver, tourPages.textRecording, ""));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Recording</b> screen is displayed as expected");

//			tourPages.swipeTourScreen(driver);
			tourPages.swipe("left");
			assertTrue(IsElementPresent(driver, tourPages.textSoapReport, ""));
			ExtentManager.logInfoDetails("Swipe left and verify <b>SOAP Report</b> screen is displayed as expected");

//			tourPages.swipeTourScreen(driver);
			tourPages.swipe("left");
			assertTrue(IsElementPresent(driver, tourPages.textPatientDatabase, ""));
			ExtentManager.logInfoDetails("Swipe left and verify <b>Patient Database Integration</b> screen is displayed as expected");

//			tourPages.swipeTourScreen(driver);
			tourPages.swipe("left");
			Thread.sleep(3000);
			assertTrue(IsElementPresent(driver, tourPages.textPatientDatabase, ""));
			ExtentManager.logInfoDetails("Swipe left, Still user is on <b>Patient Database Integration</b> screen as expected");

			// ________________________swipe right functionality___________________________

			tourPages.swipe("right");
			assertTrue(IsElementPresent(driver, tourPages.textSoapReport, ""));
			ExtentManager.logInfoDetails("Swipe right and verify <b>SOAP Report</b> screen is displayed as expected");

			tourPages.swipe("right");
			assertTrue(IsElementPresent(driver, tourPages.textRecording, ""));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Recording</b> screen is displayed as expected");

			tourPages.swipe("right");
			assertTrue(IsElementPresent(driver, tourPages.textPatientProfile, ""));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Paitent Profile</b> screen is displayed as expected");

			tourPages.swipe("right");
			assertTrue(IsElementPresent(driver, tourPages.textParentListView, ""));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Paitent List View</b> screen is displayed as expected");

			tourPages.swipe("right");
			assertTrue(IsElementPresent(driver, tourPages.textCalendarSchecule, ""));
			ExtentManager.logInfoDetails("Swipe right and verify <b>Calendar schedule</b> screen is displayed as expected");

			tourPages.swipe("right");
			Thread.sleep(3000);
			assertTrue(IsElementPresent(driver, tourPages.textCalendarSchecule, ""));
			ExtentManager.logInfoDetails("Swipe right, Still user is on <b>Calendar schedule view</b> screen as expected");

		} catch (Exception e) {
			e.getMessage();
			Assert.fail();
		}
	}

//	@Test (priority = 0)
//	public void verifyTourPagesUsingNextButtonClick() throws InterruptedException {
//		try 
//		{
//			// ______________verify home page_____________________
//			assertTrue(loginPage.validateLoginPageNote());
//			ExtentManager.logInfoDetails("Application launched successfully");
//			
//			// ______________verify login with valid credentials______________
//			Thread.sleep(10000);
//			assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
//	
//			// Click skip and verify tour page
//			assertTrue(loginPage.clickBiometricPopupButton("skip"));
//	
//			//______________validate otp and verify expected opened page______________
//			String getOtp = GetOtp.generateOTP(readData("testData", "countryCode"), readData("testData", "mobile"));
//			smsVerification.validateValidOTP(getOtp, "Tour Screen");
//
//			// _______________verify next button functionality____________________
//			tourPages.verifyTourPagesOnNextButton("no");
//		} catch (Exception e) {
//			e.getMessage();
//			Assert.fail();
//		}
//	}
//	
//	@Test (priority = 2)
//	public void verifyTourPagesUsingLeftSwipe() throws InterruptedException {
//		try 
//		{
//			// ________________________swipe left functionality__________________
//			tourPages.verifyTourPagesWithBackwardSwipe();
//		} catch (Exception e) {
//			e.getMessage();
//			Assert.fail();
//		}
//	}
//	
//	@Test (priority = 3)
//	public void verifyTourPagesUsingRightSwipe() throws InterruptedException {
//		try 
//		{		
//			// ________________________swipe right functionality___________________
//			tourPages.verifyTourPagesWithForwardSwipe();
//		} catch (Exception e) {
//			e.getMessage();
//			Assert.fail();
//		}
//	}
//	
//	@Test (priority = 4)
//	public void verifyTourPagesUsingBackButtonClick() throws InterruptedException {
//		try 
//		{
//			// _______________verify back button functionality____________________
//			tourPages.verifyTourPagesOnBackButton();
//			tourPages.verifyTourPagesOnNextButton("yes");
//		} catch (Exception e) {
//			e.getMessage();
//			Assert.fail();
//		}
//	}
}
