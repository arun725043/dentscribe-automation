package com.dentscribe.pages;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.time.Month;
import java.util.List;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class CalendarPage extends AndroidActions {

	AndroidDriver driver;

	public CalendarPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// _________calendar page common variables______
	public boolean flag = false;
	public String patientName = null;
	public String nameValue;
	public String titleValue;
	public String licenseValue;

	public static String startStatusPatients = "//android.widget.ScrollView[@resource-id='calendar-schedule-view']//android.widget.TextView[@text='Start']//parent::android.view.ViewGroup//parent::android.view.ViewGroup//preceding-sibling::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@index=1]/android.widget.TextView";

	public static String calendarHeaderTitle = "calendar-display.header.title";
	public static String calendarRightArrow = "calendar-display.header.rightArrow";
	public static String calendarLeftArrow = "calendar-display.header.leftArrow";
	public String currentMMYY = CommonMethods.currentMonthYear();

	// _________locators______
	public By iconSearch = By.xpath("//android.view.ViewGroup[@resource-id='calendar-strip-view']//preceding-sibling::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@resource-id='search-icon-button']/android.widget.ImageView");
	public By iconSetting = By.xpath("//android.view.ViewGroup[@resource-id='calendar-strip-view']//preceding-sibling::android.view.ViewGroup//following-sibling::android.view.ViewGroup[@resource-id='setting-icon-button']/android.widget.ImageView");
	
	public By calendarMonthYearDDLText = By.xpath("//android.widget.TextView[@text='" + currentMMYY + "']");
	public By calendarPopupMonthYearText = By.xpath("//android.widget.TextView[@resouce-id='calendar-display.header.title'][@text='" + currentMMYY + "']");
	public By inputMonthYear = By
			.xpath("//android.view.ViewGroup[@content-desc='calendar-strip-view']//android.widget.TextView");
	public By textFirstDate = By
			.xpath("//android.view.ViewGroup[@index=5]//android.view.ViewGroup[@index=1]/android.widget.TextView");
	public By doneButtonCalendarPopup = By.xpath("//android.widget.TextView[@text='Done']");
	public By iconDropdownCalendar = By.xpath("//android.view.ViewGroup[@resource-id='calendar-strip-view']//android.view.ViewGroup[@index=0]//android.widget.ImageView[1]");
	public By buttonStartRecording = By.xpath("//android.widget.TextView[@text='Start Recording']");
	public By buttonContinueRecording = By.xpath("//android.widget.TextView[@text='Continue Recording']");
	public By buttonWhileUsingThisApp = By.xpath("//android.widget.Button[@text='While using the app']");
	public By buttonAllowMediaAccess = By.xpath("//android.widget.Button[@text='Allow']");
	public By popupPatientDetails = By.xpath("//android.widget.TextView[@text='Patient Details']");
	public By popupPatientDetailsPatientName = By.xpath("//android.widget.TextView[@text='Name:']//following-sibling::android.widget.TextView[@index=1]");
	public By textVisibleInCalendarStrip = By.xpath("//android.view.ViewGroup[@resource-id='calendar-strip-view']//android.widget.TextView");
	
	public By twelvePM = By.xpath("//android.widget.ScrollView[@resource-id='calendar-schedule-view']//android.widget.TextView[@text=' 12:00']"); 

	// _________verify calendar page exists or not_______
	public boolean validateCalendarPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(iconDropdownCalendar));
		ExtentManager.logInfoDetails(getText(CommonLocators.textWelcomeUser) + " on calendar view page");
		getText(textVisibleInCalendarStrip);
		if(IsElementPresent(driver, iconDropdownCalendar, "Calendar dropdown icon"))
		{
			ExtentManager.logInfoDetails("User is now on <b> Calendar page <b> as expected");
			return true;
		}
		else {
			ExtentManager.logFailureDetails("Expected current Month Year on calendar page is - " + currentMMYY + " but either expected Month Year not available on Calendar page or not found. please check");
			return false;
		}
	}
	
	// _______________verify calendar page search icon and click on it______________
	public boolean clickCalendarPageSearchIcon() {
		if (IsElementPresent(driver, iconSearch, "Search icon calendar page"))
		{
			click(driver, iconSearch, "Search icon");
		}
		return true;
	}
	
	// _______________verify calendar page search icon and click on it______________
	public boolean clickCalendarPageSettingsIcon() {
		if (IsElementPresent(driver, iconSetting, "Settings icon calendar page"))
		{
			click(driver, iconSetting, "Settings icon");
		}
		return true;
	}
	
	public void selectAppointmentsDate(String appointmentDate) throws InterruptedException
	{
		click(driver, iconDropdownCalendar, "Calendar dropdown icon");
		
		int[] date = getDateMonthYear(appointmentDate);
		Month month = Month.of(date[1]);
		String monthName = month.toString();

		selectMonthYearCalendar(date[0], date[1], date[2]);
		ExtentManager.logInfoDetails("Day, Month and year is selected successfully");

		click(driver, doneButtonCalendarPopup, "Done button");
		String monthYear = getText(inputMonthYear);
		
		String[] data = monthYear.split(" ");
		assertEquals(monthName.toLowerCase(), data[0].toLowerCase());
		ExtentManager.logInfoDetails("Selected date for appointments is <b>" + appointmentDate);
	}
	
	// ___________select month and year_____________
	public void selectMonthYear(int day, int month, int year) throws InterruptedException {
		clickUsingResourceId("rightarrow-icon-button");
		Thread.sleep(3000);
		String monthYear = getText(inputMonthYear);
		String[] data = monthYear.split(" ");
//			System.out.println(Integer.parseInt(year) + " " + Integer.parseInt(data[1]));
		while (year != Integer.parseInt(data[1])) {
			if (year > Integer.parseInt(data[1])) {
				clickUsingResourceId("rightarrow-icon-button");
			} else {
				clickUsingResourceId("leftarrow-icon-button");
			}
			data[1] = getText(inputMonthYear).split(" ")[1];
		}

//		int expectedMonth = Month.valueOf(month.toUpperCase()).getValue() - 1;
		String monthName = getText(inputMonthYear).split(" ")[0];
		int actualMonth = Month.valueOf(monthName.toUpperCase()).getValue();
		while (actualMonth != month - 1) {
			if (actualMonth < month - 1) {
				clickUsingResourceId("rightarrow-icon-button");
				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFirstDate));
			} else {
				clickUsingResourceId("leftarrow-icon-button");
				AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textFirstDate));
			}
			monthName = getText(inputMonthYear).split(" ")[0];
			actualMonth = Month.valueOf(monthName.toUpperCase()).getValue() - 1;
		}
		selectDate(day);
	}

	public void selectMonthYearCalendar(int day, int month, int year) throws InterruptedException {
		Thread.sleep(2000);
		clickUsingResourceId(calendarRightArrow);
		Thread.sleep(3000);
		String monthYear = getTextUsingResourceId(calendarHeaderTitle);
		String[] data = monthYear.split(" ");
//			System.out.println(Integer.parseInt(year) + " " + Integer.parseInt(data[1]));
		while (year != Integer.parseInt(data[1])) {
			if (year > Integer.parseInt(data[1])) {
				clickUsingResourceId(calendarRightArrow);
				Thread.sleep(1500);
			} else {
				clickUsingResourceId(calendarLeftArrow);
				Thread.sleep(1500);
			}
			data[1] = getTextUsingResourceId(calendarHeaderTitle).split(" ")[1];

		}

		String monthName = getTextUsingResourceId(calendarHeaderTitle).split(" ")[0];
		int actualMonth = Month.valueOf(monthName.toUpperCase()).getValue();
		while (actualMonth != month - 1) {
			if (actualMonth < month - 1) {
				clickUsingResourceId(calendarRightArrow);
				Thread.sleep(1500);
			} else {
				clickUsingResourceId(calendarLeftArrow);
				Thread.sleep(1500);
			}
			monthName = getTextUsingResourceId(calendarHeaderTitle).split(" ")[0];
			actualMonth = Month.valueOf(monthName.toUpperCase()).getValue() - 1;
		}
		selectDate(day);
	}

	@SuppressWarnings("deprecation")
	public void selectDate(int day) {
		TouchAction touchAction = new TouchAction(driver);
		String d = String.valueOf(day);
		while (true) {
			String datePath = "//android.widget.TextView[@text='" + d + "']";
			if (IsElementPresent(driver, By.xpath(datePath), "Days")) {
				click(driver, By.xpath(datePath), "day");
				break;
			}
			if (day < Integer.parseInt(getText(textFirstDate))) {
				touchAction.press(PointOption.point(280, 550))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for
																						// visibility
						.moveTo(PointOption.point(829, 512)).release().perform();
			} else {
				touchAction.press(PointOption.point(829, 550))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for
																						// visibility
						.moveTo(PointOption.point(280, 512)).release().perform();
			}
		}
	}

	public int[] getDateMonthYear(String fullDate) {
		String[] data = fullDate.split("-");
		int day = Integer.parseInt(data[0]);
		int month = Integer.parseInt(data[1]);
		int year = Integer.parseInt(data[2]);
		return new int[] { day, month, year };

	}

	// ___________click patient name and verify whether Patient Details popup opened or not______________
	public void clickAppointmentHasStartButton() throws InterruptedException 
	{
		try {
			List<WebElement> listOfPatient = driver.findElements(By.xpath("//android.widget.TextView[@text='Start']//parent::android.view.ViewGroup//parent::android.view.ViewGroup//preceding-sibling::android.view.ViewGroup//android.widget.TextView"));
			for (int i = 0; i < listOfPatient.size(); i++) {
				patientName = listOfPatient.get(i).getText();
				if (patientName.equals("Start")) {
					continue;
				} else {
					listOfPatient.get(i).click();
					ExtentManager.logInfoDetails("Clicked on patient name : <b> " + patientName + " </b>");
					break;
				}
			}
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Either no appointments avalaible in calendar view or given locator is no more valid. please check");
			Assert.fail();
		}
	}
	
	public void clickVerifyPatientAppointmentButton(String pName, String buttonName)
	{
		click(driver, By.xpath("//android.widget.TextView[@text='" + pName + "']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='" + buttonName + "']"), buttonName + " button of patient - " + patientName);
//		ExtentManager.logInfoDetails(buttonName + " button click for patient name - " + pName);
		if (buttonName == "Continue")
		{
			if (IsElementPresent(driver, popupPatientDetails, "Patient Details popup"))
			{
				ExtentManager.logInfoDetails("Expected <b>Patient Deatils<b> popup opened for patient name - <b>" + patientName);
			}
			else {
				ExtentManager.logFailureDetails("Either <b>Patient Deatils<b> popup not opened/found or verifying value not matched. please check");
				Assert.fail();
			}
		}
		else if (buttonName == "Review" || buttonName == "Reviewed")
		{
			ExtentManager.logInfoDetails("Button name is Continue for patient <b>" + patientName + " as expected on Calendar View");
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.soapReportSubjectiveHeader));
			if (IsElementPresent(driver, CommonLocators.soapReportHeader, "SOAP Report") && IsElementPresent(driver, CommonLocators.soapReportSubjectiveHeader, "Subjective"))
			{
				ExtentManager.logInfoDetails("Expected <b>SOAP Report<b> page opened for patient name - <b>" + pName);
			}
			else {
				ExtentManager.logFailureDetails("Either <b>SOAP Report<b> page not opened/found or Subjective header is missing. please check");
				Assert.fail();
			}
		}
	}
	
	// verify patient button name
	public void verifyPatientButton(String patient, String buttonName) {
		String patientAppointmentStatus = "//android.widget.TextView[@text='" + patient
				+ "']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='"
				+ buttonName + "']";
		explicitWait(driver, By.xpath(patientAppointmentStatus), 300);
//		System.out.println(getText(By.xpath(patientAppointmentStatus)));
		if(IsElementPresent(driver, By.xpath(patientAppointmentStatus), buttonName + " button"))
		{
			ExtentManager.logInfoDetails("Appointment status changed to <b>" + buttonName + " as expected for patient name - <b>" + patient);
		}
	}
	
	// _________verify patient details popup opened or not_______
	public void verifyIsPatientDetailsPopupOpenedForExpectedPatient(String expectedPatientName)
	{
		if(IsElementPresent(driver, popupPatientDetails, "Patient Details popup"))
		{
			String actualPatient = getText(popupPatientDetailsPatientName).trim();
			assertEquals(actualPatient, expectedPatientName);
			ExtentManager.logInfoDetails("<b>Patient Details popup opened as expected for patient - " + actualPatient);
		}
		else {
			ExtentManager.logFailureDetails("Either patient details popup not exisst or opened or verifying element not found. please check");
			Assert.fail();
		}
	}
		
	// ______________click start/continue recording button on Patient Details popup___________
	public void clickVerifyPatientDetailsPopupButton(String operation, String buttonName) {
		if (operation == "click") {
			if (buttonName == "Start") {
				click(driver, buttonStartRecording, "Start Recording button");
			} else if (buttonName == "Continue") {
				click(driver, buttonContinueRecording, "Continue Recording button");
			}
		} else if (operation == "verify") {
			if (buttonName == "Start") {
				if(IsElementPresent(driver, buttonStartRecording, "Start Recording button"))
				{
					ExtentManager.logInfoDetails("<b>Start Recording</b> button is displayed as expected on Patient Details popup");
				}
				else {
					ExtentManager.logFailureDetails(buttonName + " either not available or not found. please check");
					Assert.fail();
				}
			}
			else if (buttonName == "Continue") {
				if(IsElementPresent(driver, buttonContinueRecording, "Continue Recording button"))
				{
					ExtentManager.logInfoDetails("<b>Continue Recording</b> button is displayed as expected on Patient Details popup");
				}
				else {
					ExtentManager.logFailureDetails(buttonName + " Recording button either not available on Patient Details popup or not found. please check");
					Assert.fail();
				}
			}
			else {
				ExtentManager.logFailureDetails("Button name could be Start/Continue only for Patient Details popup");
				Assert.fail();
			}
		}
	}

	// ____________click while using app button_________
	public void clickWhileUsingAppButton() {
		try {
			click(driver, buttonWhileUsingThisApp, "While using the app option on notification");
			ExtentManager.logInfoDetails("Clicked on <b> While using the app </b> option selected on alert for allow microphone");

		} catch (Exception e) {
			ExtentManager.logInfoDetails("No alert found for allow microphone access");
		}
	}

	// ____________click Allow button on allow access photo/media popup if visible_________
	public void allowAccessMedia() {
		try {
			click(driver, buttonAllowMediaAccess, "Allow");
			ExtentManager.logInfoDetails("Clicked on <b> Allow </b> option on alert for access photos and media");

		} catch (Exception e) {
			ExtentManager.logInfoDetails("No alert found for allow photos and media access");
		}
	}
	
	// ____________click Allow button on allow access record audio popup if visible_________
	public void allowAccessRecordAudio() {
		try {
			click(driver, buttonAllowMediaAccess, "Allow");
			ExtentManager.logInfoDetails("Clicked on <b> Allow </b> option on alert for allow Dentscribe to record audio");

		} catch (Exception e) {
			ExtentManager.logInfoDetails("No alert found for allow Dentscribe to record audio");
		}
	}

}
