package com.dentscribe.api;


import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonVariables;
import com.dentscribe.pages.SignUpPage;
import com.dentscribe.utils.AndroidActions;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

import io.appium.java_client.android.AndroidDriver;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GenerateOtp extends AndroidActions {
	AndroidDriver driver;
	SignUpPage signUpPage = new SignUpPage(driver);

	public GenerateOtp(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void fillOtp() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("phoneNumber", "+91" + readData("Config", "mobile"));
			JSONObject json = new JSONObject(map);
			String result = null;
			baseURI = CommonVariables.generateSMS_OtpApi;
			Response response = given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toJSONString()).when().post(baseURI);
			System.out.println("OTP: " + response.jsonPath().getString("data.otp"));
			result = response.jsonPath().getString("data.otp");
			System.out.println("otp " + result);
			sendKeys(signUpPage.inputSmsOtp, result, driver);
			ExtentManager.logInfoDetails("SMS OTP Entered successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
