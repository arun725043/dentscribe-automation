package com.dentscribe.base;

import java.io.FileInputStream;

import java.time.Duration;
import java.util.Random;

import org.testng.annotations.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dentscribe.common.CommonMethods;
import com.dentscribe.pages.AddPaymentMethodPage;
import com.dentscribe.pages.CalendarPage;
import com.dentscribe.pages.EulaAgreementPage;
import com.dentscribe.pages.FeedbackPage;
import com.dentscribe.pages.ForgotPasswordPage;
import com.dentscribe.pages.HelpPage;
import com.dentscribe.pages.LoginPage;
import com.dentscribe.pages.ManageSubscriptionPage;
import com.dentscribe.pages.PatientProfilePage;
import com.dentscribe.pages.PatientSearchPage;
import com.dentscribe.pages.PracticeInfoPage;
import com.dentscribe.pages.RecordingPage;
import com.dentscribe.pages.SettingsPage;
import com.dentscribe.pages.SignUpPage;
import com.dentscribe.pages.SikkaWebviewPage;
import com.dentscribe.pages.SmsVerification;
import com.dentscribe.pages.SoapReportPage;
import com.dentscribe.pages.TourPages;
import com.dentscribe.utils.AndroidActions;

import java.net.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AndroidBase extends CommonMethods {
	public AndroidDriver driver;
	public static WebDriverWait wait;
	public static FileInputStream inputstream;
	public static Random randomGenerator;
	public static String workingDir;
	static AppiumDriverLocalService service;

	public AndroidActions actions;
	public SignUpPage signUpPage;
	public LoginPage loginPage;
	public PracticeInfoPage practiceInfoPage;
	public TourPages tourPages;
	public PatientSearchPage searchPage;
	public SettingsPage settingPage;
	public ManageSubscriptionPage manageSubscriptionPage;
	public CalendarPage calendarPage;
	public ForgotPasswordPage forgotPasswordPage;
	public SmsVerification smsVerificationPage;
	public PatientProfilePage profilePage;
	public RecordingPage recordingPage;
	public SoapReportPage soapReportPage;
	public SikkaWebviewPage sikkaWebviewPage;
	public EulaAgreementPage eulaPage;
	public AddPaymentMethodPage addPaymentMethodPage;
	public HelpPage helpPage;
	public FeedbackPage feedbackPage;

//	@BeforeTest
	public void startServer() {
		service = startAppiumServer(readData("Config", "ipAddress"), Integer.parseInt(readData("Config", "port")));
	}

	@BeforeClass
	public void launchApplication() {
		try {
			service = startAppiumServer(readData("Config", "ipAddress"), Integer.parseInt(readData("Config", "port")));
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(readData("Config", "deviceName")); // emulator
			options.setPlatformName(readData("Config", "deviceType"));
			options.setAppPackage(readData("Config", "packageName")).setAppActivity(readData("Config", "activityName"));
			options.setNewCommandTimeout(Duration.ofMinutes(90));
//			options.setNoReset(true);
//			options.setApp(System.getProperty("user.dir") + "//apk_files//app-release.apk");
//			driver = new AndroidDriver(service.getUrl(), options);
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			initializeObjects();
			System.out.println("Welcome");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
            driver.quit();
            stopService();
        }
	}

//	@AfterTest
	public void stopService() {
		service.stop();
	}

	public void initializeObjects() {
		actions = new AndroidActions(driver);
		signUpPage = new SignUpPage(driver);
		practiceInfoPage = new PracticeInfoPage(driver);
		forgotPasswordPage = new ForgotPasswordPage(driver);
		loginPage = new LoginPage(driver);
		smsVerificationPage = new SmsVerification(driver);
		manageSubscriptionPage = new ManageSubscriptionPage(driver);
		tourPages = new TourPages(driver);
		calendarPage = new CalendarPage(driver);
		recordingPage = new RecordingPage(driver);
		soapReportPage = new SoapReportPage(driver);
		searchPage = new PatientSearchPage(driver);
		profilePage = new PatientProfilePage(driver);
		settingPage = new SettingsPage(driver);
		sikkaWebviewPage = new SikkaWebviewPage(driver);
		eulaPage = new EulaAgreementPage(driver);
		addPaymentMethodPage = new AddPaymentMethodPage(driver);
		helpPage = new HelpPage(driver);
		feedbackPage = new FeedbackPage(driver);
	}
}
