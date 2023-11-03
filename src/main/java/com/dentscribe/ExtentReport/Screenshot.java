package com.dentscribe.ExtentReport;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.dentscribe.base.AndroidBase;
public class Screenshot extends AndroidBase {

	public static String getBase64(WebDriver driver) {
		return ((TakesScreenshot)driver) .getScreenshotAs(OutputType.BASE64);
	}
}