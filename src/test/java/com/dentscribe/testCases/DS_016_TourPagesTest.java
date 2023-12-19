package com.dentscribe.testCases;

import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.apis.GetOtp;
import com.dentscribe.base.AndroidBase;

public class DS_016_TourPagesTest extends AndroidBase 
{
	@Test (priority = 0)
	public void verifyTourPagesUsingNextButtonClick() throws InterruptedException {
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

			// _______________verify next button functionality____________________
			tourPages.verifyTourPagesOnNextButton("no");
		} catch (Exception e) {
			e.getMessage();
			Assert.fail();
		}
	}
	
	@Test (priority = 1)
	public void verifyTourPagesUsingBackButtonClick() throws InterruptedException {
		try 
		{
			// _______________verify back button functionality____________________
			tourPages.verifyTourPagesOnBackButton();
			tourPages.verifyTourPagesOnNextButton("yes");
		} catch (Exception e) {
			e.getMessage();
			Assert.fail();
		}
	}
	
	@Test (priority = 2)
	public void verifySkipLinkTourPages() throws InterruptedException {
		try 
		{
			// _______________verify skip link functionality____________________
			tourPages.skipTourPages();
			calendarPage.validateCalendarPage();
		} catch (Exception e) {
			e.getMessage();
			Assert.fail();
		}
	}
	
	
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
}
