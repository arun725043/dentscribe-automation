package com.dentscribe.pages;

import org.openqa.selenium.By;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class SettingPage extends AndroidActions {

	AndroidDriver driver;

	public SettingPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public By buttonLogOut = By.xpath("//android.widget.TextView[@text='Log Out']");
	public By textFreeTrial = By.xpath("//android.widget.TextView[contains(@text,'30-Day Free Trial Selected')]");
	public By text699Selected = By.xpath("//android.widget.TextView[contains(@text,'$699/Month Selected')]");
	public By buttonCancel = By.xpath("//android.widget.TextView[@text='Cancel']");
	public By backButton = By.className("android.widget.ImageView");
	public By inputPhoneNumber = By.xpath("//android.widget.TextView[@text='Phone Number']" + CommonLocators.fixPath);
	public By inputCurrentPassword = By.xpath("//android.widget.TextView[@text='Current Password']" + CommonLocators.fixPath);
	public By inputNewPassword = By.xpath("//android.widget.TextView[@text='New Password']" + CommonLocators.fixPath);
	public By iconHelp = By.xpath("//android.widget.TextView[@text='Settings']//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	public By textFaqs = By.xpath("//android.widget.TextView[@text='FAQs']");
	public By buttonHelp = By.xpath("//android.widget.TextView[@text='Help']");
	public By buttonSave = By.xpath("//android.widget.TextView[@text='Save']");

	// practice info
	public By inputName = By.xpath("//android.widget.TextView[@text='Name']" + CommonLocators.fixPath);
	public By inputAddressLine1 = By.xpath("//android.widget.TextView[@text='Address Line 1']" + CommonLocators.fixPath);
	public By inputAddressLine2 = By.xpath("//android.widget.TextView[@text='Address Line 2']" + CommonLocators.fixPath);

	// Notification
	public By checkedPushNotification = By.xpath("//android.widget.TextView[contains(@text,'Push Notifications')]//following-sibling::android.widget.Switch[@checked='true']");
	public By unCheckedPushNotification = By.xpath("//android.widget.TextView[contains(@text,'Push Notifications')]//following-sibling::android.widget.Switch[@checked='false']");

	public By checkedSmsNotification = By.xpath("//android.widget.TextView[contains(@text,' SMS Notifications')]//following-sibling::android.widget.Switch[@checked='true']");
	public By unCheckedSmsNotification = By.xpath("//android.widget.TextView[contains(@text,' SMS Notifications')]//following-sibling::android.widget.Switch[@checked='false']");

	public By checkedEmailNotification = By.xpath("//android.widget.TextView[contains(@text,' Email Notifications')]//following-sibling::android.widget.Switch[@checked='true']");
	public By unCheckedEmailNotification = By.xpath("//android.widget.TextView[contains(@text,' Email Notifications')]//following-sibling::android.widget.Switch[@checked='false']");

	// click logout link
	public void clickOnLogout() {
		click(buttonLogOut, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Logout</b> button");
	}

	// ____________verify free trial text_____________
	public boolean verifyFreeTrialSelectedText() {
		return IsElementPresent(textFreeTrial, driver);
	}

	// _______generate new password_________
	public String generatePassword() {
		return new String("A1@" + CommonMethods.getAlphaNumericString(5));
	}

	// ___________update phone number____________
	public String phNumber;

	public void editPhoneNumber() {
		clear(inputPhoneNumber);
		phNumber = "91" + GenerateRandomNumber(8);
		sendKeys(inputPhoneNumber, phNumber, driver);
		ExtentManager.logInfoDetails("Enter value in Phone number input field " + getAttribute(inputPhoneNumber));
	}

	// __________Enable disable setting notification___
	public String enableDisableNotification(By checked, By unchecked) {
		String response;
		if (IsElementPresent(checked, driver)) {
			click(checked, driver);
			explicitWait(driver, unchecked, 20);
			response = "Unchecked";
		} else {
			click(unchecked, driver);
			explicitWait(driver, checked, 20);
			response = "Checked";
		}
		return response;
	}

	// _____________update practice info__________
	String name;
	String address1;
	String address2;
	public void updatePracticeInfo() {
		clear(inputName);
		name = genrateRandomFirstName();
		sendKeys(inputName, name, driver);
		ExtentManager.logInfoDetails("Entered value in name input field : " + name);

		clear(inputAddressLine1);
		address1 = genrateRandomCity();
		sendKeys(inputAddressLine1, address1, driver);
		ExtentManager.logInfoDetails("Entered value in Address Line 1 input field : " + address1);

		clear(inputAddressLine2);
		address2 = genrateRandomCity();
		sendKeys(inputAddressLine2, address2, driver);
		ExtentManager.logInfoDetails("Entered value in Address Line 2 input field : " + address2);

		scrollableClick("Continue");
		explicitWait(driver, CommonLocators.continueButton, 10);

	}

	// _____verify practice info after updation________
	public boolean verifyUpdatedPracticeInfo() {
		System.out.println(name + " " + getAttribute(inputName));
		System.out.println(address1 + " " + getAttribute(inputAddressLine1));
		System.out.println(address2 + " " + getAttribute(inputAddressLine2));

		if ((name.equals(getAttribute(inputName)) && (address1.equals(getAttribute(inputAddressLine1))) && (address2.equals(getAttribute(inputAddressLine2))))) {
			return true;
		} else {
			return false;
		}
	}

}
