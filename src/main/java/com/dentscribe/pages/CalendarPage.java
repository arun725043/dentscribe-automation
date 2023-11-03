package com.dentscribe.pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Month;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.AppiumBy;
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
	public static String fetchingPatients = "//*[contains(@text,'Fetching Patient')]";
	public static String startStatusPatients = "//android.widget.TextView[@text='Start']//parent::android.view.ViewGroup//parent::android.view.ViewGroup//preceding-sibling::android.view.ViewGroup//android.widget.TextView";
	
	public static String calendarHeaderTitle = "calendar-display.header.title";
	public static String calendarRightArrow = "calendar-display.header.rightArrow";
	public static String calendarLeftArrow = "calendar-display.header.leftArrow";
	
	// _________locators______
	public By iconSearch = By.xpath("(//*[contains(@text,'Welcome,')]//following-sibling::android.view.ViewGroup/android.widget.ImageView)[1]");
	public By iconSetting = By.xpath("(//*[contains(@text,'Welcome,')]//following-sibling::android.view.ViewGroup/android.widget.ImageView)[2]");
	public By inputSearch = AppiumBy.className("android.widget.EditText");
	public By inputMonthYear = By.xpath("//android.view.ViewGroup[@content-desc='calendar-strip-view']//android.widget.TextView");
	public By textFirstDate = By.xpath("//android.view.ViewGroup[@index=5]//android.view.ViewGroup[@index=1]/android.widget.TextView");
	public By textLastDate = By.xpath("//android.view.ViewGroup[@index=10]//android.view.ViewGroup[@index=1]/android.widget.TextView");
	public By buttonStartRecording = By.xpath("//android.widget.TextView[@text='Start Recording']");
	public By buttonContinueRecording = By.xpath("//android.widget.TextView[@text='Continue Recording']");
	public By buttonWhileUsingThisApp = By.xpath("//android.widget.Button[@text='While using the app']");
	public By buttonAllowMediaAccess = By.xpath("//android.widget.Button[@text='Allow']");
	public By pauseButton = By.xpath("//android.widget.TextView[@text='Pause']");
	public By stopButton = By.xpath("//android.widget.TextView[@text='Stop']");
	public By doneButton = By.xpath("//android.widget.TextView[@text='Done']");
	public By dropdownCalendar = By.xpath("//android.view.ViewGroup[@content-desc='downarrow-icon-button']");
	public By pauseRecordingText = By.xpath("//android.widget.TextView[@text=''Recording paused due to the app going into the background. Would you like to resume recording?'']");			
	public By pausePopupButton = By.xpath("//android.widget.TextView[@text='OK']");
	public By backButton = By.className("android.widget.ImageView");

	// Soap report locators
	public By buttonAdoptSignature = By.xpath("//android.widget.TextView[@text='Adopt Signature']");
	public By buttonDraw = By.xpath("//android.widget.TextView[contains(@text,'Draw')]");
	public By buttonSubmit = By.xpath("//android.widget.TextView[@text='Submit']");
	public By textCreateYourSignature = By.xpath("//android.widget.TextView[@text='Create Your Signature']");
	public By textInitials = By.xpath("//android.widget.TextView[@text='Initials']");

	public By textSoapReport = By.xpath("//android.widget.TextView[@text='SOAP Report']");
	public By signatureArea = AppiumBy.className("android.widget.Image");
	public By buttonRedraw = By.xpath("//android.widget.TextView[contains(@text,'Redraw')]");
	

	// ______________click start recording button___________
	public boolean clickVerifyStartRecording(String operation, String buttonName) {
		if (operation == "click") {
			if (buttonName == "Start") {
				click(buttonStartRecording, driver);
				ExtentManager.logInfoDetails("Clicked on <b> Start Recording </b> Button");
			}
			else if (buttonName == "Continue") {
				click(buttonContinueRecording, driver);
				ExtentManager.logInfoDetails("Clicked on <b> Continue Recording </b> Button");
			}
				
		} else if (operation == "verify") {
			flag = IsElementPresent(buttonStartRecording, driver);
		}
		return flag;
	}

	// ____________click while using app button_________
	public void clickWhileUsingAppButton() {
		try {
			click(buttonWhileUsingThisApp, driver);
			ExtentManager.logInfoDetails("Clicked on <b> While using the app </b> Button");

		} catch (Exception e) {
			ExtentManager.logInfoDetails("No alert found for allow microphone");
		}
	}
	
	// ____________click Allow button on allow access photo/media popup if visible_________
	public void allowAccessMedia() {
		try {
			click(buttonAllowMediaAccess, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Allow </b> Button");

		} catch (Exception e) {
			ExtentManager.logInfoDetails("No alert found for allow photos and media");
		}
	}
	
	// ____________close recording pause popup if appear_________
	public void closeRecordingPausePopup() {
		try {
			flag = IsElementPresent(pausePopupButton, driver);
			System.out.println("FLAG VALUE == " + String.valueOf(flag));
			click(pausePopupButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> Ok </b> button and closed the recording pause popup");

		} catch (Exception e) {
			ExtentManager.logInfoDetails("No alert found for recording paused due to app going into backgroupd");
		}
	}

	// __________verify stop pause button__________
	public boolean verifyStopPauseButton() {
		if (IsElementPresent(pauseButton, driver) && IsElementPresent(stopButton, driver)) {
			return true;
		} else {
			return false;
		}
	}

	// ____________click pause stoop button_____________
	public void clickPauseStopButton(String buttonName) {
		if (buttonName == "pause") {
			click(pauseButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> pause </b> Button");

		} else if (buttonName == "stop") {
			click(stopButton, driver);
			ExtentManager.logInfoDetails("Clicked on <b> stop </b> Button");

		}
	}

	// _______________verify user is landing on search page or not______________
	public boolean verifySearchLandingPage() {
		click(iconSearch, driver);
		ExtentManager.logInfoDetails("Clicked on <b> Search Icon </b> Button");
		fetchingPatientLoader();
		return IsElementPresent(inputSearch, driver);
	}

	// ___________fetch patient loader_______
	public void fetchingPatientLoader() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fetchingPatients)));
			new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(fetchingPatients)));
		} catch (Exception e) {
			System.out.println("Loaded");
		}
	}

	// ___________select month and year_____________
	public void selectMonthYear(int day, int month, int year) throws InterruptedException {
		clickUsingResourceId("rightarrow-icon-button");
		Thread.sleep(3000);
		String monthYear = getText(inputMonthYear);
		String[] data = monthYear.split(" ");
//		System.out.println(Integer.parseInt(year) + " " + Integer.parseInt(data[1]));
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
//		System.out.println(Integer.parseInt(year) + " " + Integer.parseInt(data[1]));
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

//		int expectedMonth = Month.valueOf(month.toUpperCase()).getValue() - 1;
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
			if (IsElementPresent(By.xpath(datePath), driver)) {
				click(By.xpath(datePath), driver);
				break;
			}
			if (day < Integer.parseInt(getText(textFirstDate))) {
				touchAction.press(PointOption.point(280, 550)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for visibility
						.moveTo(PointOption.point(829, 512)).release().perform();
			} else {
				touchAction.press(PointOption.point(829, 550)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))) // Optional: Add a short delay for visibility
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

	// ___________click patient______________
	public void clickPatient() {
		List<WebElement> listOfPatient = driver.findElements(By.xpath(startStatusPatients));
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

	//_____________To start and stop the audio_____________
	public void runAudio(String fileName, long time) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException 
	{
		ExtentManager.logInfoDetails("Recording start for " + CommonMethods.getSecondsMinutes(time, "minutes")+ " mints.");
		if(fileName !="")
		{
			File file = new File(CommonVariables.audioFilePath + fileName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			String response = "P";
			while (!response.equals("Q")) {
				response = "p";
				response = response.toUpperCase();
				switch (response) {
				case ("P"):
					clip.start();
					Thread.sleep(time);
					clip.close();
					response = "Q";
					break;
				case ("S"):
					clip.stop();
					break;
				case ("R"):
					clip.setMicrosecondPosition(0);
					break;
				case ("Q"):
					clip.close();
					break;
				default:
					System.out.println("Not a valid response");
				}
			}
		}
		else {
			ExtentManager.logInfoDetails("Recording blank audio for " + CommonMethods.getSecondsMinutes(time, "minutes")+ " mints because no audio file name provided.");
			Thread.sleep(time);
		}
	}

	// verify patient button name
	public boolean verifyPatientButton(String patient, String buttonName) {
		String patientAppointmentStatus = "//android.widget.TextView[@text='" + patient + "']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.widget.TextView[@text='" + buttonName + "']";
		explicitWait(driver, By.xpath(patientAppointmentStatus), 120);
		System.out.println(getText(By.xpath(patientAppointmentStatus)));
		return IsElementPresent(By.xpath(patientAppointmentStatus), driver);
	}

	// click adopt signature
	public void clickAdoptSignature() throws InterruptedException {
		explicitWait(driver, textSoapReport, 20);
		Thread.sleep(4000);
		scrollableClick("Adopt Signature");
		explicitWait(driver, textCreateYourSignature, 10);
	}

	// Draw signature
	public void drawSignature() throws InterruptedException {
		Thread.sleep(3000);
		click(buttonSubmit, driver);
		explicitWait(driver, textSoapReport, 10);
		scrollToText("Submit");
		click(buttonSubmit, driver);
	}

}
