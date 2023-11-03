package com.dentscribe.pages;

import org.openqa.selenium.By;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class PatientSearchPage extends AndroidActions {

	AndroidDriver driver;

	public PatientSearchPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	//________locators___________________
	public By inputSearch = AppiumBy.className("android.widget.EditText");
	public By listOfPatient = By.xpath("//android.widget.TextView[@index=0]");
	public By textWelcome = By.xpath("//*[contains(@text,'Welcome,')]");
	public By iconSetting=By.xpath("(//android.widget.TextView[contains(@text,'Welcome,')]//following-sibling::android.view.ViewGroup/android.view.ViewGroup)[2]/android.widget.ImageView");

	// ______________verify user is able to search patient or not______________

	//________searching the patient___________________
	public void searchPatient(String name) {
		clear(inputSearch);
		sendKeys(inputSearch, name, driver);
		ExtentManager.logInfoDetails("Entered value into Search Input : <b>" + name + "</b>");
	}

	// ______________verify patient is searched or not_________
	public boolean verifySearchedPatient(String... info) {
		return IsElementPresent(By.xpath("//android.widget.TextView[@text='" + info[0] + "']"), driver);
	}

}
