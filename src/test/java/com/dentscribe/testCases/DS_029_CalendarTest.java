package com.dentscribe.testCases;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Month;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.api.GenerateOtp;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;

public class DS_029_CalendarTest extends AndroidBase {

	@Test
	public void verifyCalendarFunctionality() throws IOException, InterruptedException {
		GenerateOtp getOtp = new GenerateOtp(driver);

		ExtentManager.logInfoDetails("Application launched successfully");
		assertTrue(loginPage.loginApplication(readData("userDetails", "username"), readData("userDetails", "password"), "valid"));
		ExtentManager.logInfoDetails("User is logged in successfully as expected");
		
		assertTrue(loginPage.clickBiometricPopupButton("skip"));
		
		getOtp.fillOtp();
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Continue </b> after entering the OTP");
		
		tourPages.skipTourPages();
		wait.until(ExpectedConditions.visibilityOfElementLocated(searchPage.textWelcome));

		int[] date = calendarPage.getDateMonthYear(readData("testData", "calendarTestDate"));
		Month month = Month.of(date[1]);

		String monthName = month.toString();
		click(calendarPage.dropdownCalendar, driver);
		ExtentManager.logInfoDetails("Clicked on Calendar dropdown");

		calendarPage.selectMonthYearCalendar(date[0], date[1], date[2]);
		ExtentManager.logInfoDetails("Day,Month and year is selected successfully");

		click(calendarPage.doneButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Done</b> button");
		String monthYear = getText(calendarPage.inputMonthYear);

		String[] data = monthYear.split(" ");
		assertEquals(monthName.toLowerCase(), data[0].toLowerCase());
		ExtentManager.logInfoDetails(readData("testData", "calendarTestDate") + " date is selected successfully");

	}

}
