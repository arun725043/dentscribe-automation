package com.dentscribe.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.utils.AndroidActions;

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

	// _________skip tour pages______
	public void skipTourPages() {
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(linkSkip));
		click(linkSkip, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Skip</b> on Tour page");
	}

	// ________________verify tour pages on next button____________
	public boolean verifyTourPagesOnNextButton(String operation) {
		switch (operation) {
		case "calendar":
			click(buttonNext, driver);
			return IsElementPresent(textParentListView, driver);

		case "patient view":
			click(buttonNext, driver);
			return IsElementPresent(textPatientProfile, driver);

		case "patient profile":
			click(buttonNext, driver);
			return IsElementPresent(textRecording, driver);

		case "recording":
			click(buttonNext, driver);
			return IsElementPresent(textSoapReport, driver);

		case "soap report":
			click(buttonNext, driver);
			return IsElementPresent(textPatientDatabase, driver);

		default:
			System.out.println("please enter valid name");
		}
		return false;

	}

	// __________________verify tour pages on back button_____________
	public boolean verifyTourPagesOnBackButton(String operation) {

		switch (operation) {
		case "patientDatabase":
			click(buttonBack, driver);
			return IsElementPresent(textSoapReport, driver);

		case "soap report":
			click(buttonBack, driver);
			return IsElementPresent(textRecording, driver);

		case "recording":
			click(buttonBack, driver);
			return IsElementPresent(textPatientProfile, driver);

		case "patient profile":
			click(buttonBack, driver);
			return IsElementPresent(textParentListView, driver);

		case "patient view":
			click(buttonBack, driver);
			return IsElementPresent(textCalendarSchecule, driver);

		default:
			System.out.println("please enter valid name");
		}
		return false;

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
