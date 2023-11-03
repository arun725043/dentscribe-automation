package com.dentscribe.base;

import java.io.FileInputStream;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.dentscribe.common.CommonMethods;
import com.dentscribe.pages.CalendarPage;
import com.dentscribe.pages.ForgotPasswordPage;
import com.dentscribe.pages.LoginPage;
import com.dentscribe.pages.ManageSubscriptionPage;
import com.dentscribe.pages.PatientSearchPage;
import com.dentscribe.pages.PracticeInfoPage;
import com.dentscribe.pages.SettingPage;
import com.dentscribe.pages.SignUpPage;
import com.dentscribe.pages.TourPages;
import com.dentscribe.utils.AndroidActions;

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
	public SettingPage settingPage;
	public ManageSubscriptionPage manageSubscription;
	public CalendarPage calendarPage;
	public ForgotPasswordPage forgotPasswordPage;

	@BeforeTest
	public void startServer() {
		service = startAppiumServer(readData("Config", "ipAddress"), Integer.parseInt(readData("Config", "port")));
	}

	@BeforeMethod
	public void signIn() {
		try {
			UiAutomator2Options options = new UiAutomator2Options();
			options.setDeviceName(readData("Config", "deviceName")); // emulator
			options.setPlatformName(readData("Config", "deviceType"));
			options.setAppPackage(readData("Config", "packageName")).setAppActivity(readData("Config", "activityName"));
//			options.setNoReset(true);
//			options.setApp(System.getProperty("user.dir") + "//apk_files//app-release.apk");
			driver = new AndroidDriver(service.getUrl(), options);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			initializeObjects();
			System.out.println("Welcome");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();

	}

	@AfterTest
	public void stopService() {
		service.stop();
	}

	public void initializeObjects() {
		actions = new AndroidActions(driver);
		signUpPage = new SignUpPage(driver);
		loginPage = new LoginPage(driver);
		practiceInfoPage = new PracticeInfoPage(driver);
		tourPages = new TourPages(driver);
		searchPage = new PatientSearchPage(driver);
		settingPage = new SettingPage(driver);
		manageSubscription = new ManageSubscriptionPage(driver);
		calendarPage = new CalendarPage(driver);
		forgotPasswordPage = new ForgotPasswordPage(driver);
	}

}
