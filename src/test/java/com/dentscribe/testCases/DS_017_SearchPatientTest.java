package com.dentscribe.testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_017_SearchPatientTest extends AndroidBase {

	@Test
	public void searchPatient() {
		GenerateOtp getOtp = new GenerateOtp(driver);

		try {
			ExtentManager.logInfoDetails("Application launched successfully");
			assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
			ExtentManager.logInfoDetails("User is logged in successfully as expected");

			assertTrue(loginPage.clickBiometricPopupButton("skip"));
			getOtp.fillOtp();
			click(CommonLocators.continueButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
			
			// skip tour pages
			tourPages.skipTourPages();
			wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

			// search result should be display after searching
			assertTrue(calendarPage.verifySearchLandingPage());
			ExtentManager.logInfoDetails("Patient search page opened successfully");

			// Search By patientName and verify
			ExtentManager.logInfoDetails("Searching By name : <b> " + readData("testData", "patientName") + "</b>");
			searchPage.searchPatient(readData("testData", "patientName"));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + readData("testData", "patientName") + "']")));
			assertTrue(searchPage.verifySearchedPatient(readData("testData", "patientName")));
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

			// Search By patientMobile and verify
			ExtentManager.logInfoDetails("Searching By mobile : <b> " + readData("testData", "patientPhone") + "</b>");
			searchPage.searchPatient(readData("testData", "patientPhone"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'" + readData("testData", "patientPhone") + "')]")));

			String no = driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + readData("testData", "patientPhone") + "')]")).get(1).getText();
			assertEquals(no.trim(), readData("testData", "patientPhone"));
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

			// Search By dob and verify
			ExtentManager.logInfoDetails("Searching By DOB : <b> " + readData("testData", "dob") + "</b>");
			searchPage.searchPatient(readData("testData", "dob"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + readData("testData", "patientName") + "']")));

			assertTrue(searchPage.verifySearchedPatient(readData("testData", "dob")));
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

			// Search By insurance and verify
			ExtentManager.logInfoDetails("Searching By insurance : <b> " + readData("testData", "insurance") + "</b>");
			searchPage.searchPatient(readData("testData", "insurance"));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[contains(@text,'" + readData("testData", "insurance") + "')]")));
			assertTrue(IsElementPresent(By.xpath("//android.widget.TextView[contains(@text,'" + readData("testData", "insurance") + "')]"), driver));
			ExtentManager.logInfoDetails("Search Result is displayed as per the given input");

			
			searchPage.searchPatient(readData("testData", "patientName"));
			Thread.sleep(5000);
			driver.findElements(By.xpath("//android.widget.TextView[contains(@text,'" + readData("testData", "patientName") + "')]")).get(1).click();
			Thread.sleep(5000);

			assertTrue(contains(driver.getPageSource(), "Past SOAP Report"));
			
			// click on setting->Logout
			click(calendarPage.backButton, driver);
		
			click(searchPage.iconSetting, driver);
			ExtentManager.logInfoDetails("Clicked on <b>Setting</b> icon");

			settingPage.clickOnLogout();
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.txtUsername));

			// Verify login page
			assertTrue(IsElementPresent(CommonLocators.txtUsername, driver));
			ExtentManager.logInfoDetails("Login page is displayed as expected");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
