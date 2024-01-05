package com.dentscribe.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.utils.AndroidActions;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SikkaWebviewPage extends AndroidActions {
	AndroidDriver driver;
	public boolean flag;
	
	public SikkaWebviewPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	// _______________________________sikka________________________
	public By buttonRegister = By.xpath("//*[@text='Register']");
	public By buttonProceed = By.xpath("//*[@text='Proceed']");
	public By buttonNext = By.xpath("//*[@text='Next']");
	public By RadioNo = By.xpath("//android.widget.TextView[@text='No']");
	public By RadioYes = By.xpath("//android.widget.TextView[@text='Yes']");
	public By textYourOrder = By.xpath("//*[@text='Your Order']");
	public By textTermsOfService = By.xpath("//*[contains(@text,'Terms of Service')]");
	public By textConfirmation = By.xpath("//*[@text='Confirmation']");
	public By buttonAgree = By.xpath("//android.widget.Button[@resource-id='btnTOSagree'][@text='Agree'][@enabled='true']");

	
	// _______________verify whether signup page exists or not_______________
	public void validateSikkaWebViewPage()
	{
		scrollableClick("Proceed");
		if (IsElementPresent(driver, buttonRegister, "Register button on webview")) {
			ExtentManager.logInfoDetails("<b>User is now on Sikka Webview page as expected");
		} else {
			ExtentManager.logFailureDetails("Either expected Sikka Webview page verified element not found or page not exists. please check");
			Assert.fail();
		}
	}
	
	// _______________Enter existing sikka detail_________________
	public void enterExistingSikkaCredentials(String username, String pwd) throws InterruptedException {
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

		click(driver, By.xpath("//*[@text='Next']"), "Next button");
	}

	// _________________Scroll terms of service page________
	public void termsOfServiceScroll() throws InterruptedException {
		
		if (readData("Config", "emmulator").equalsIgnoreCase("yes")) {
			scrollToActiveTheAgreeButton();
		} else {
//			maxDurationInSeconds = 70;
			scrollToActiveTheAgreeButton();
		}
		ExtentManager.logInfoDetails("<b>Scrolling Terms of Service page");
	}
	
	public void scrollToActiveTheAgreeButton() throws InterruptedException {
		// Java	
		for(int i=0;i<60;i++)
		{
			((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("left", 200, "top", 1000,
					"width", 800, "height", 900, "direction", "up", "percent", 0.75));
			Thread.sleep(3000);
			if(IsAgreeButtonPresent(driver, buttonAgree, "Agree button"))
			{
				break;
			}
			else {
				continue;
			}
		}
	}



	@SuppressWarnings("deprecation")
	public static void scrollDown(AndroidDriver driver) {
		TouchAction touchAction = new TouchAction(driver);
		touchAction.press(PointOption.point(583, 1762)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for visibility
				.moveTo(PointOption.point(643, 1041)).release().perform();
	}
}
