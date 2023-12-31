package com.dentscribe.utils;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonMethods;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class AndroidActions extends CommonMethods {

	AndroidDriver driver;

	public AndroidActions(AndroidDriver driver) {

		this.driver = driver;
	}

	// long press
	public void longPress(By locator, int duration) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) driver.findElement(locator)).getId(), "duration", duration));
	}

	// _________Scroll to element
	public void scrollToText(String text) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));"));
	}

	// _________Scroll to partial text
	public void scrollToPartialText(String partialText) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + partialText + "\"));"));

	}

	// _________Scroll to element
	public void scrollableClick(String text) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));")).click();
//		ExtentManager.logInfoDetails("Clicked on button <b>" + text + "</b>");
	}

	public void scrollByElement(By locator) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(" + "new UiSelector().description(\"" + locator + "\"))"));
	}

	// drag and drop
	public void dragAndDrop(By source, int x, int y) {
		((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of("elementId", ((RemoteWebElement) driver.findElement(source)).getId(), "endX", x, "endY", y));
	}
	
	public void ScrollToElementText(String option, String targetText) {
	    WebElement element = null;
	    int numberOfTimes = 10;
	    Dimension size = driver.manage().window().getSize();
	    int anchor = (int) (size.width / 2);
	    // Swipe up to scroll down
	    int startPoint = (int) (size.height - 10);
	    int endPoint = 0;
	    if (option == "account")
	    {
	    	endPoint = 10;
	    }
	    if (option == "practice")
	    {
	    	endPoint = (int) (size.height * 0.25);
	    }

	    for (int i = 0; i < numberOfTimes; i++) {
	        try {
	            new TouchAction(driver)
	                .longPress(PointOption.point(anchor, startPoint))
	                .moveTo(PointOption.point(anchor, endPoint))
	                .release().perform();
	            element = driver.findElement(By.xpath("//android.widget.TextView[@text='" + targetText + "']"));
	            i = numberOfTimes;
	        } catch (NoSuchElementException ex) {
	            Assert.fail(String.format("Element not available. Scrolling (%s) times...", i + 1));
	        }
	    }
//	    return element;
	}
	
	// scroll using text
	public void scrollUntilElementIsVisible(String targetText) throws InterruptedException 
	{
		try {
			while (true) {
				WebElement element = null;
				try {
					ExtentManager.logInfoDetails("Scrolling page to find the target element - <b>" + targetText);
					element = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + "new UiSelector().textContains(\"" + targetText + "\"));"));
					Thread.sleep(1000);
				} catch (Exception e) {
					// Element not found, continue scrolling
					break;
				}
	
				if (element != null) {
					ExtentManager.logInfoDetails("Successfully reached to element - <b>" + targetText);
					break;
				} else {
					driver.pressKey(new KeyEvent(AndroidKey.PAGE_DOWN));
					Thread.sleep(1000);
				}
			}
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Failed to scroll to element - " + targetText);
			Assert.fail();
		}
	}

	public void sendKeysUsingResourceId(String resourceId, String text) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));")).sendKeys(text);
	}

	public String getAttributeUsingResourceId(String resourceId) {

		return driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));")).getAttribute("name");

	}
	public String getTextUsingResourceId(String resourceId) {
		return driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));")).getText();
	}

	public void clickUsingResourceId(String resourceId) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"" + resourceId + "\"));")).click();
	}

	@SuppressWarnings("deprecation")
	public void scrollByCordinate(int startX, int startY, int endX, int endY, int noOfTimes) {

		for (int i = 0; i < noOfTimes; i++) {
			new TouchAction(driver).press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))) // Optional: Add a short delay for visibility
					.moveTo(PointOption.point(endX, endY)).release().perform();
		}
	}

	// __________swipe action___________
	public void swipeAction(String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of("left", 548, "top", 690, "width", 0, "height", 1000, "direction", direction, "percent", 0.75, "duration", 5000));
	}
	
	// __________this is to refresh screen__________
	public void pullToRefreshPage(){

	    int deviceWidth = driver.manage().window().getSize().getWidth();
	    int deviceHeight = driver.manage().window().getSize().getHeight();
	    int midX = (deviceWidth / 2);
	    int midY = (deviceHeight / 2);
	    int bottomEdge = (int) (deviceHeight * 0.85f);
	    new TouchAction(driver)
	            .press(PointOption.point(midX,midY))
	            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(5000)))
	            .moveTo(PointOption.point(midX, bottomEdge))
	            .release().perform();
	}
}
