package com.dentscribe.apis;

import org.json.simple.JSONObject;

import com.dentscribe.ExtentReport.ExtentManager;
import com.dentscribe.common.CommonVariables;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetOtp {

	public static String generateOTP(String countryCode, String mobileNumber) 
	{
		String result = null;
		try {
		
			Map<String, Object> map = new HashMap<String, Object>();
	//		map.put("phoneNumber", "+91" + readData("Config", "mobile"));
			map.put("phoneNumber", countryCode + mobileNumber);
			JSONObject json = new JSONObject(map);
			baseURI = CommonVariables.generateSMS_OtpApi;
			Response response = given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toJSONString()).when().post(baseURI);
			System.out.println(response.body().asString());
	//		ExtentManager.logInfoDetails("Response ::- " + response.toString());
			result = response.jsonPath().getString("data.otp");
			ExtentManager.logInfoDetails("Received OTP ::- " + result);
			return result;
		}
		catch (Exception e) {
			ExtentManager.logFailureDetails("Something went wrong in Generate OTP method");
			return result;
		}
	}
	
	// Handling SPU installation and refresh data    
	public static void updateOfficeId(String emailId, String officeId)
	{
		JSONObject request = new JSONObject();
		request.put("email", emailId);
		request.put("officeId", officeId);
		
		Response response = 
				given().
					header("Content-type", "application/json").
					contentType(ContentType.JSON).
					accept(ContentType.JSON).
					body(request.toString()).
				when().
					patch(CommonVariables.updateOfficeIdApi).
				then().
					extract().response();
		
		System.out.println(response.getStatusCode());
		System.out.println(response.asString());
	}

}
