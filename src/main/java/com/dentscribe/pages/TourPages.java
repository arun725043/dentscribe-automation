package com.dentscribe.pages;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.utils.AndroidActions;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class TourPages extends AndroidActions {
	AndroidDriver driver;

	// _________Objects_________
	LoginPage login = new LoginPage(driver);
	public boolean flag;
	public By textCalendarSchecule = By.xpath("//android.widget.TextView[@text='Calendar Schedule View']");
	public By textParentListView = By.xpath("//android.widget.TextView[@text='Patient List View']");
	public By textPatientProfile = By.xpath("//android.widget.TextView[@text='Patient Profile']");
	public By textRecording = By.xpath("//android.widget.TextView[@text='Recording']");
	public By textSoapReport = By.xpath("//android.widget.TextView[@text='SOAP Report']");
	public By textPatientDatabase = By.xpath("//android.widget.TextView[@text='Patient Database Integration']");
	public By op = By.xpath("//*[contains(@text='OP')]");
	public By buttonNext = By.xpath("//android.widget.TextView[@text='Next']");
	public By buttonBack = By.xpath("//android.widget.TextView[@text='Back']");
	public By linkSkip = By.xpath("//android.widget.TextView[@text='Skip']");

	public TourPages(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// _________validate whether user is on tour page or not_______
	public void validateTourPageCalendarScheduleView()
	{
		explicitWait(driver, textCalendarSchecule, 60);
		if (IsElementPresent(driver, textCalendarSchecule, "Calendar Schedue View")) {
			ExtentManager.logInfoDetails("<b>User is now on Calendar Schedue View tour page as expected");
		} else {
			ExtentManager.logFailureDetails("Either expected Signup page verified element not found or not exists. please check");
			Assert.fail();
		}
	}
	
	// _________skip tour pages______
	public void skipTourPages() {
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(linkSkip));
		click(driver, linkSkip, "Skip link");
		ExtentManager.logInfoDetails("Clicked on <b>Skip</b> link on Tour page");
	}
	
	// ________________verify tour pages on next button____________
	public void verifyTourPagesOnNextButton(String operation) 
	{
		click(driver, buttonNext, "Next button on Calendar Schedule View slide");
		IsElementPresent(driver, textParentListView, "Patient List View slide");

		click(driver, buttonNext, "Next button on Patient List View slide");
		IsElementPresent(driver, textPatientProfile, "Patient Profile slide");
		
		click(driver, buttonNext, "Next button on Patient Profile slide");
		IsElementPresent(driver, textRecording, "Recording slide");
		
		click(driver, buttonNext, "Next button on Recording slide");
		IsElementPresent(driver, textSoapReport, "SOAP Report slide");
		
		click(driver, buttonNext, "Next button on SOAP Report slide");
		IsElementPresent(driver, textPatientDatabase, "Patient Database Integration slide");
		if(operation.equalsIgnoreCase("yes"))
		{
			click(driver, buttonNext, "Next button on Patient Database Integration slide");
		}
	}

	// __________________verify tour pages on back button_____________
	public void verifyTourPagesOnBackButton() {

		click(driver, buttonBack, "Back button on Patient Database Integration slide");
		IsElementPresent(driver, textSoapReport, "SOAP Report slide");

		click(driver, buttonBack, "Back button on SOAP Report slide");
		IsElementPresent(driver, textRecording, "Recording slide");

		click(driver, buttonBack, "Back button on Recording slide");
		IsElementPresent(driver, textPatientProfile, "Patient Profile slide");

		click(driver, buttonBack, "Back button on Patient profile slide");
		IsElementPresent(driver, textParentListView, "Patient List View slide");

		click(driver, buttonBack, "Patient List View slide");
		IsElementPresent(driver, textCalendarSchecule, "Calendar Schedule View slide");
	}
	
	//____________________verify left right swipe______________
	public void swipeTourPages() throws InterruptedException
	{
		// ________________________swipe left functionality___________________________

//		swipeTourScreen(driver);
		swipe("left");
		assertTrue(IsElementPresent(driver, textParentListView, ""));
		ExtentManager.logInfoDetails("Swipe left and verify <b>Paitent List View</b> screen is displayed as expected");

//		swipeTourScreen(driver);
		swipe("left");
		assertTrue(IsElementPresent(driver, textPatientProfile, ""));
		ExtentManager.logInfoDetails("Swipe left and verify <b>Paitent Profile</b> screen is displayed as expected");

//		swipeTourScreen(driver);
		swipe("left");
		assertTrue(IsElementPresent(driver, textRecording, ""));
		ExtentManager.logInfoDetails("Swipe left and verify <b>Recording</b> screen is displayed as expected");

//		swipeTourScreen(driver);
		swipe("left");
		assertTrue(IsElementPresent(driver, textSoapReport, ""));
		ExtentManager.logInfoDetails("Swipe left and verify <b>SOAP Report</b> screen is displayed as expected");

//		swipeTourScreen(driver);
		swipe("left");
		assertTrue(IsElementPresent(driver, textPatientDatabase, ""));
		ExtentManager.logInfoDetails("Swipe left and verify <b>Patient Database Integration</b> screen is displayed as expected");

//		swipeTourScreen(driver);
		swipe("left");
		Thread.sleep(3000);
		assertTrue(IsElementPresent(driver, textPatientDatabase, ""));
		ExtentManager.logInfoDetails("Swipe left, Still user is on <b>Patient Database Integration</b> screen as expected");

		// ________________________swipe right functionality___________________________

		swipe("right");
		assertTrue(IsElementPresent(driver, textSoapReport, ""));
		ExtentManager.logInfoDetails("Swipe right and verify <b>SOAP Report</b> screen is displayed as expected");

		swipe("right");
		assertTrue(IsElementPresent(driver, textRecording, ""));
		ExtentManager.logInfoDetails("Swipe right and verify <b>Recording</b> screen is displayed as expected");

		swipe("right");
		assertTrue(IsElementPresent(driver, textPatientProfile, ""));
		ExtentManager.logInfoDetails("Swipe right and verify <b>Paitent Profile</b> screen is displayed as expected");

		swipe("right");
		assertTrue(IsElementPresent(driver, textParentListView, ""));
		ExtentManager.logInfoDetails("Swipe right and verify <b>Paitent List View</b> screen is displayed as expected");

		swipe("right");
		assertTrue(IsElementPresent(driver, textCalendarSchecule, ""));
		ExtentManager.logInfoDetails("Swipe right and verify <b>Calendar schedule</b> screen is displayed as expected");

		swipe("right");
		Thread.sleep(3000);
		assertTrue(IsElementPresent(driver, textCalendarSchecule, ""));
		ExtentManager.logInfoDetails("Swipe right, Still user is on <b>Calendar schedule view</b> screen as expected");
	}

	public void swipe(String direction) {
		// Java
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("left", 100, "top", 1000,
				"width", 800, "height", 200, "direction", direction, "percent", 0.75));
	}

	@SuppressWarnings("deprecation")
	public void swipeTourScreen(AndroidDriver driver) {
		new TouchAction(driver).press(PointOption.point(926, 1400)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for visibility
				.moveTo(PointOption.point(200, 1386)).release().perform();
	}
	@SuppressWarnings("deprecation")
	public void swipeRightTourScreen(AndroidDriver driver) {
		new TouchAction(driver).press(PointOption.point(200, 1386)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for visibility
				.moveTo(PointOption.point(926, 1400)).release().perform();
	}

}
