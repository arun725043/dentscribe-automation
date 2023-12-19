package com.dentscribe.pages;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.base.AndroidBase;
import com.dentscribe.common.CommonMethods;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.utils.AndroidActions;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

public class RecordingPage extends AndroidActions {

	AndroidDriver driver;

	public RecordingPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// _________calendar page common variables______
	public String nameValue;
	public String titleValue;
	public String licenseValue;

	// _________locators______
	public By recordingPageHeader = By.xpath("//android.widget.TextView[@text='Record']");
	public By recordingRunningText = By.xpath("//android.widget.TextView[@text='• Recording']");
	public By iconBackRecordingPage = By.xpath("//android.widget.TextView[@text='Record']//preceding-sibling::android.view.ViewGroup//android.widget.ImageView[@index=0]");
	public By textPatientName = By.xpath("//android.widget.TextView[@text='Record']//parent::android.view.ViewGroup//following-sibling::android.view.ViewGroup//android.view.ViewGroup[@index=1]//android.widget.TextView");
	
	public By pauseButton = By.xpath("//android.widget.TextView[@text='Pause']");
	public By stopButton = By.xpath("//android.widget.TextView[@text='Stop']");
	public By pauseRecordingText = By.xpath("//android.widget.TextView[@text=''Recording paused due to the app going into the background. Would you like to resume recording?'']");
	public By backIconPopupMessage = By.xpath("//android.widget.TextView[@text='Do you want to pause the recording ?']");
	public By backIconPopupButtonOK = By.xpath("//android.widget.TextView[@text='OK']");
	public By backIconPopupButtonCancel = By.xpath("//android.widget.TextView[@text='Cancel']");

	// _________verify recording page exists or not_______
	public void validateRecordingPage()
	{
		AndroidBase.wait.until(ExpectedConditions.visibilityOfElementLocated(recordingRunningText));
		if(IsElementPresent(driver, recordingPageHeader, "Record header") && IsElementPresent(driver, recordingRunningText, "Recording text"))
		{
			ExtentManager.logInfoDetails("User is now on <b> Recording <b> page as expected and Recording started as well");
		}
		else {
			ExtentManager.logFailureDetails("Either expected recording page or recording text for recording running not exists or not found. please check");
			Assert.fail();
		}
	}
	
	// _________verify recording page for patient name, start, pause buttons_______
	public void verifyRecordingPageFields(String expectedPatientName) 
	{
//		String nameXPath = "//android.widget.TextView[@text='" + CommonVariables.patientDeatilsPopupPatientName.trim() + "']";
		//read and verify patient name
		String actualPatientName = getText(textPatientName);
		System.out.println(actualPatientName + "::" + expectedPatientName);
		assertEquals(actualPatientName.trim(), expectedPatientName.trim());
		ExtentManager.logInfoDetails("Recording page opened for patient - " + actualPatientName);
		verifyStopPauseButtons();
	}
	
	//
	public void clickVerifyBackIconRecordingPage(String cickButton) throws InterruptedException
	{
		if(IsElementPresent(driver, iconBackRecordingPage, "Back icon on recording page"))
		{
			click(driver, iconBackRecordingPage, "Back icon on recording page");
			if(IsElementPresent(driver, backIconPopupMessage, "Recording pause popup"))
			{
				ExtentManager.logInfoDetails("Message found on back icon click popup is - <b>" + getText(backIconPopupMessage));
				if (cickButton.equalsIgnoreCase("CANCEL"))
				{
					clickVerifyPausePopupCancelButton();
				}
				else if (cickButton.equalsIgnoreCase("OK"))
				{
					click(driver, backIconPopupButtonOK, "OK button recording pause popup");
					Thread.sleep(10000);
				}
			}
			else {
				ExtentManager.logFailureDetails("Expected message <b>Do you want to pause the recording ?<b> not matched with actual message found - <b>" + getText(backIconPopupMessage));
			}
		}
		else {
			ExtentManager.logFailureDetails("Expected Back icon not exists on Recording page or not found. please check");
		}
	}
	
	// ____________close recording pause popup if appear_________
	public void clickVerifyPausePopupCancelButton()
	{
		click(driver, backIconPopupButtonCancel, "Cancel button recording pause popup");
		if(IsElementPresent(driver, recordingRunningText, "Recording text"))
		{
			ExtentManager.logInfoDetails("<b>On cancel button click recording is continued as expected<b>");
		}
		else {
			ExtentManager.logFailureDetails("Either Pause popup not closed or Recording page/text not found. please check");
			Assert.fail();
		}
	}

	// __________verify stop pause button__________
	public boolean verifyStopPauseButtons() {
		if (IsElementPresent(driver, pauseButton, "Pause button recording screen") && IsElementPresent(driver, stopButton, "Stop button recording screen")) {
			return true;
		} else {
			return false;
		}
	}

	// ____________click pause stop button_____________
	public void clickPauseStopButton(String buttonName) {
		if (buttonName == "pause") {
			click(driver, pauseButton, "Pause button recording screen");
		} else if (buttonName == "stop") {
			click(driver, stopButton, "Stop button recording screen");

		}
	}

	// _____________To start and stop the audio_____________
	public void runAudio(String fileName, long time)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		ExtentManager
				.logInfoDetails("Recording start for " + CommonMethods.getSecondsMinutes(time, "minutes") + " mints.");
		if (fileName != "") {
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
		} else {
			ExtentManager.logInfoDetails("Recording blank audio for " + CommonMethods.getSecondsMinutes(time, "minutes")
					+ " mints because no audio file name provided.");
			Thread.sleep(time);
		}
	}
}
