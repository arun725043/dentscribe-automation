package com.dentscribe.pages;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonLocators;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SignUpPage extends AndroidActions {
	public boolean flag;
	AndroidDriver driver;

	public By signupButton = By.xpath("//android.widget.TextView[@text='Sign Up']");
	public By textPhone = By.xpath("//android.widget.TextView[@text='Phone Number']");
	public By inputFirstName = By.xpath("//android.widget.TextView[@text='First Name']" + CommonLocators.fixPath);
	public By inputTitle = By.xpath("//android.widget.TextView[@text='Title']" + CommonLocators.fixPath);
	public By inputLastName = By.xpath("//android.widget.TextView[@text='Last Name']" + CommonLocators.fixPath);
	public By inputEmail = By.xpath("//android.widget.TextView[@text='Email']" + CommonLocators.fixPath);
	public By countryCode = By.xpath("//android.widget.TextView[@text='Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.ImageView");
	public By dentistDropdown = By.xpath("//android.widget.TextView[@text='Your Role']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView");
	public By valueDentistDropdown = By.xpath("//android.widget.TextView[@text='Your Role']//parent::android.view.ViewGroup//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView");
	public By licenseNumber = By.xpath("//android.widget.TextView[@text='License Number']" + CommonLocators.fixPath);
	public By inputPhoneNumber = By.xpath("//android.widget.TextView[@text='Phone Number']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.EditText");
	public By textPasswordIsRequired = By.xpath("//android.widget.TextView[@text='Password is required.']");
	public By textCpasswordIsRequired = By.xpath("//android.widget.TextView[@text='Confirm password is required.']");
	public By textAskForContinue = By.xpath("//android.widget.TextView[@text='Do you wish to continue?']");
	public By buttonBack = By.xpath("//android.widget.TextView[@text='Back']");
	public By buttonContinue = By.xpath("//android.widget.TextView[@text='Continue']");
	public By textSmsVerification = By.xpath("//android.widget.TextView[@text='SMS Verification']");
	public By inputTelephone = By.xpath("android.widget.EditText[@content-desc='Telephone input']");
	public By textIndia = By.xpath("//android.widget.TextView[contains(@text,'India')]");
	public By pmsDrpdwn = By.xpath("//android.widget.TextView[@text='Select Your Practice Management Software']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup/android.widget.TextView");
	public By txtSorry = By.xpath("//android.widget.TextView[contains(@text,'Sorry!')]");
	public By buttonOkay = By.xpath("//android.widget.TextView[@text='Okay']");
	public By textEmailAlreadyExist = By.xpath("//android.widget.TextView[@text='User with email id already exists.']");
	public By textAccountSuccessfullyCreated = By.xpath("//android.widget.TextView[@text='Your account has been successfully verified!']");

	// _______________call/sms otp________________
	public By textEnterValidCode = By.xpath("//android.widget.TextView[@text='Please enter the valid code.']");
	public By linkResendCode = By.xpath("//android.widget.TextView[@text='Resend Code']");
	public By inputFirstLetter = By.xpath("//android.widget.EditText[@content-desc='otp-one-input']");
	public By inputSecondLetter = By.xpath("//android.widget.EditText[@content-desc='otp-two-input']");
	public By inputThirdLetter = By.xpath("//android.widget.EditText[@content-desc='otp-three-input']");
	public By inputFourthLetter = By.xpath("//android.widget.EditText[@content-desc='otp-four-input']");
	public By inputFifthLetter = By.xpath("//android.widget.EditText[@content-desc='otp-five-input']");
	public By inputSixthLetter = By.xpath("//android.widget.EditText[@content-desc='otp-six-input']");
	public By textTriggerAnotherCall = By.xpath("//android.widget.TextView[@text='Trigger Another Call']");
	public By inputSmsOtp = By.className("android.widget.EditText");
	public By iconBack = By.className("android.widget.ImageView");

	public SignUpPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// __________________fill signup form_____________________
	public void fillSignupForm(String[] userDetail, String email, String pms) throws InterruptedException {
		sendKeys(inputFirstName, userDetail[0], driver);
		ExtentManager.logInfoDetails("Entered value into first Name input field : <b>" + userDetail[0] + "</b>");
		sendKeys(inputLastName, userDetail[1], driver);
		ExtentManager.logInfoDetails("Entered value into last Name input field : <b>" + userDetail[1] + "</b>");
		click(countryCode, driver);
		scrollToPartialText(userDetail[4]);
		click(By.xpath("//android.widget.TextView[contains(@text,'" + userDetail[4] + "')]"), driver);
		Thread.sleep(2000);
		sendKeys(inputPhoneNumber, readData("Config", "mobile"), driver);
		ExtentManager.logInfoDetails("Entered value into Mobile Number input field : <b>" + readData("Config", "mobile") + "</b>");
		sendKeys(inputEmail, email, driver);
		CommonVariables.email = getAttribute(inputEmail);
		ExtentManager.logInfoDetails("Entered value into Email input field : <b>" + email + "</b>");
		sendKeys(inputTitle, "title"+GenerateRandomNumber(3), driver);
		ExtentManager.logInfoDetails("Entered value into title input field : <b>" + getAttribute(inputTitle) + "</b>");
		scrollUntilElementIsVisible("License Number");
		sendKeys(licenseNumber, userDetail[2], driver);
		ExtentManager.logInfoDetails("Entered value into License Number input field : <b>" + userDetail[2] + "</b>");
		scrollUntilElementIsVisible("Password");
		sendKeys(CommonLocators.txtPassword, userDetail[3], driver);
		ExtentManager.logInfoDetails("Entered value into password input field : <b>" + userDetail[3] + "</b>");
		CommonVariables.actualPass = userDetail[3];
		scrollUntilElementIsVisible("Confirm Password");
		sendKeys(CommonLocators.txtConfirmPassword, userDetail[3], driver);
		ExtentManager.logInfoDetails("Entered value into confirm password input field : <b>" + userDetail[3] + "</b>");
		Thread.sleep(3000);
		scrollToText("Continue");
		click(pmsDrpdwn, driver);
		ExtentManager.logInfoDetails("Clicked on PMS dropdown");
		scrollableClick(pms);
		click(CommonLocators.continueButton, driver);
		ExtentManager.logInfoDetails("Clicked on <b>Continue</b> button");
	}

	// _________getting random fname,lname,lno,countrycode,countryname_____________
	public String[] getSignupDetail() {
		String fname = genrateRandomFirstName();
		String lname = genrateRandomLastName();
		String licenseNo = String.valueOf(GenerateRandomNumber(6));
		String pass = "#A1a" + CommonMethods.getAlphaNumericString(6);
		String countryCode = readData("testData", "countryCode");
		String countryName = readData("testData", "countryName");
		return new String[] { fname, lname, licenseNo, pass, countryCode, countryName };

	}

	// ________________verify sorry popup for dentrix__________
	public boolean verifySorryPopup() {
		return IsElementPresent(txtSorry, driver);
	}

	// _________verify signup landing page________
	public boolean verifySignupLandingPage() {
		return IsElementPresent(textPhone, driver);
	}

	// ____________verify all mandatory fields________
	public boolean verifySignupMandatoryFields() {
		if (IsElementPresent(textPasswordIsRequired, driver) && IsElementPresent(textCpasswordIsRequired, driver)) {
			return true;
		} else {
			return false;
		}
	}

	// _____________verify confirmation popup button____________
	public boolean verifyConfirmationPopupButton() {
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(buttonBack));
		if (IsElementPresent(buttonBack, driver) && IsElementPresent(buttonContinue, driver)) {
			return true;
		} else {
			return false;
		}
	}

	// ___________click skip button and veridy calender schedule screen______
	public boolean clickConfirmationPopupButton(String buttonName) {
		switch (buttonName) {
		case "back":
			click(buttonBack, driver);
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(CommonLocators.textWelcome));
			flag = IsElementPresent(CommonLocators.textWelcome, driver);
			break;
		case "continue":
			click(buttonContinue, driver);
			AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(textSmsVerification));
			flag = IsElementPresent(textSmsVerification, driver);
			break;
		default:
			break;
		}
		return flag;
	}

	// _____________Fill confirmation page______________
	public void fillConfirmationPage() throws InterruptedException {
		String resourceId = "signature";
		sendKeysUsingResourceId("txtPracticeZip", CommonMethods.GenerateRandomNumber(5));
		ExtentManager.logInfoDetails("Entered value into zip input field : <b>" + getAttributeUsingResourceId("txtPracticeZip") + "</b>");
		sendKeysUsingResourceId("txtPracticePhone", readData("Config", "mobile"));
		ExtentManager.logInfoDetails("Entered value into Practice Phone input field: <b>" + readData("Config", "mobile") + "</b>");
		sendKeysUsingResourceId("txtPracticeName", "Test dental practice");
		ExtentManager.logInfoDetails("Entered value into Practice Name input field : <b>Test dental practice</b>");
		sendKeysUsingResourceId("txtPracticeAddress", "Address" + CommonMethods.GenerateRandomNumber(5));
		ExtentManager.logInfoDetails("Entered value into Address input field : <b>" + getAttributeUsingResourceId("txtPracticeAddress") + "</b>");
		sendKeysUsingResourceId("txtPracticeCity", genrateRandomCity());
		ExtentManager.logInfoDetails("Entered value into City input field : <b>" + getAttributeUsingResourceId("txtPracticeCity") + "</b>");
		sendKeysUsingResourceId("txtPracticeState", genrateRandomState());
		ExtentManager.logInfoDetails("Entered value into State input field : <b>" + getAttributeUsingResourceId("txtPracticeState") + "</b>");
		sendKeysUsingResourceId("txtContactName", CommonMethods.genrateRandomFirstName());
		ExtentManager.logInfoDetails("Entered value into Contact name input field : <b>" + getAttributeUsingResourceId("txtContactName") + "</b>");
		sendKeysUsingResourceId("txtSignUpEmailId", CommonVariables.email);
		ExtentManager.logInfoDetails("Entered value into Email input field : <b>" + getAttributeUsingResourceId("txtSignUpEmailId") + "</b>");
		sendKeysUsingResourceId("txtFirstName", CommonMethods.genrateRandomFirstName());
		ExtentManager.logInfoDetails("Entered value into First name input field : <b>" + getAttributeUsingResourceId("txtFirstName") + "</b>");
		sendKeysUsingResourceId("txtLastName", CommonMethods.genrateRandomLastName());
		ExtentManager.logInfoDetails("Entered value into Last name input field : <b>" + getAttributeUsingResourceId("txtLastName") + "</b>");
		sendKeysUsingResourceId("txtUserName", CommonMethods.genrateRandomFirstName() + CommonMethods.GenerateRandomNumber(5));
		ExtentManager.logInfoDetails("Entered value into Username input field : <b>" + getAttributeUsingResourceId("txtUserName") + "</b>");
		String pass = "A!1a" + CommonMethods.getAlphaNumericString(5);
		sendKeysUsingResourceId("txtPassword", pass);
		ExtentManager.logInfoDetails("Entered value into password input field : <b>" + pass + "</b>");
		sendKeysUsingResourceId("txtConfirmPassword", pass);
		ExtentManager.logInfoDetails("Entered value into confirm password input field: <b>" + pass + "</b>");
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));"));
		dragAndDrop(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));"), 500, 1587);
		scrollableClick("I'm not a robot");
	}

	// _______________Enter existing sikka detail_________________
	public void enterExisistingSikkaCredentials(String username, String pwd) throws InterruptedException {
		String resourceId = "signatureL";
		sendKeysUsingResourceId("txtLoginUserName", username);
		ExtentManager.logInfoDetails("Entered value into username input field : <b>" + username + "</b>");
		sendKeysUsingResourceId("txtLoginPassword", pwd);
		ExtentManager.logInfoDetails("Entered value into password input field : <b>" + pwd + "</b>");
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));"));
		dragAndDrop(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));"), 500, 1587);
		if (readData("Config", "emmulator").equalsIgnoreCase("yes")) {
			scrollByCordinate(542, 2097, 400, 425, 1);
			Thread.sleep(2000);
		} else {
			scrollUntilElementIsVisible("Next");
			scrollableClick("Next");
		}

		click(By.xpath("//*[@text='Next']"), driver);
	}

	// _________________Scroll terms of service page________
	public void termsOfServiceScroll() {
		long maxDurationInSeconds;
		if (readData("Config", "emmulator").equalsIgnoreCase("yes")) {

			maxDurationInSeconds = 170;
		} else {
			maxDurationInSeconds = 70;
		}
		long startTime = System.currentTimeMillis();
		while (isTimeElapsed(startTime, maxDurationInSeconds)) {
			scrollDown(driver);
		}
	}

	public static boolean isTimeElapsed(long startTime, long maxDurationInSeconds) {
		long currentTime = System.currentTimeMillis();
		long elapsedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime - startTime);
		return elapsedTimeInSeconds < maxDurationInSeconds;
	}

	@SuppressWarnings("deprecation")
	public static void scrollDown(AndroidDriver driver) {
		TouchAction touchAction = new TouchAction(driver);
		touchAction.press(PointOption.point(583, 1762)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for visibility
				.moveTo(PointOption.point(643, 1041)).release().perform();
	}
}
