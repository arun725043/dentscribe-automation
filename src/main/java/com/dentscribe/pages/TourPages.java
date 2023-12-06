package com.dentscribe.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
	public boolean verifyTourLandingPage()
	{
		if (IsElementPresent(driver, textCalendarSchecule, "Calendar Schedue View")) {
			ExtentManager.logInfoDetails("User is now on <b> Calendar Schedue View tour page <b> as expected");
			return true;
		} else {
			ExtentManager.logFailureDetails("Either expected Signup page verified element not found or not exists. please check");
			return false;
		}
	}
	
	// _________skip tour pages______
	public void skipTourPages() {
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(linkSkip));
		click(driver, linkSkip, "Skip link");
		ExtentManager.logInfoDetails("Clicked on <b>Skip</b> on Tour page");
	}

	// ________________verify tour pages on next button____________
	public boolean verifyTourPagesOnNextButton(String operation) {
		switch (operation) {
		case "calendar":
			click(driver, buttonNext, "Calendar Schedule View");
			return IsElementPresent(driver, textParentListView, "Patient List View");

		case "patient view":
			click(driver, buttonNext, "Patient List View");
			return IsElementPresent(driver, textPatientProfile, "Patient Profile");

		case "patient profile":
			click(driver, buttonNext, "Patient Profile");
			return IsElementPresent(driver, textRecording, "Recording");

		case "recording":
			click(driver, buttonNext, "Recording");
			return IsElementPresent(driver, textSoapReport, "SOAP Report");

		case "soap report":
			click(driver, buttonNext, "SOAP Report");
			return IsElementPresent(driver, textPatientDatabase, "Patient Database Integration");

		default:
			System.out.println("please enter valid name");
		}
		return false;

	}

	// __________________verify tour pages on back button_____________
	public boolean verifyTourPagesOnBackButton(String operation) {

		switch (operation) {
		case "patientDatabase":
			click(driver, buttonBack, "Patient Database Integration");
			return IsElementPresent(driver, textSoapReport, "SOAP Report");

		case "soap report":
			click(driver, buttonBack, "SOAP Report");
			return IsElementPresent(driver, textRecording, "Recording");

		case "recording":
			click(driver, buttonBack, "Recording");
			return IsElementPresent(driver, textPatientProfile, "Patient Profile");

		case "patient profile":
			click(driver, buttonBack, "Patient profile");
			return IsElementPresent(driver, textParentListView, "Patient List View");

		case "patient view":
			click(driver, buttonBack, "Patient List View");
			return IsElementPresent(driver, textCalendarSchecule, "Calendar Schedule View");

		default:
			System.out.println("please enter valid name");
		}
		return false;

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
