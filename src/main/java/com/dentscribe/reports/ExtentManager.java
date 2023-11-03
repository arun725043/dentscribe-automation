package com.dentscribe.reports;

import java.io.File;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.dentscribe.common.CommonVariables;


public class ExtentManager {

	private static ExtentReports extent;
	public static String path;

	//To setup different configurations (path, format, name) of report	
	public static ExtentReports createInstance() 
	{   
		//To set the path of the report
		String fileName = getReportName();
		new File(CommonVariables.reportsPath).mkdir();
		path = CommonVariables.reportsPath + fileName;
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);

		//To set the look of the report
		reporter.config().setEncoding("utf-8");
		reporter.config().setDocumentTitle("Title : Auomation Testing"); // Title of Report
		reporter.config().setReportName("Report: Automation Test Results"); // Name of the report
		reporter.config().setTheme(Theme.STANDARD); 

		extent = new ExtentReports(); 		
		extent.attachReporter(reporter);
		extent.setSystemInfo("Organisation", "ThinkSys"); // General information related to application		
		return extent;
	}

	//To check whether report creation has already been started or not  
	public static ExtentReports getInstance() 
	{
		if(extent == null) 
		{
			createInstance();
		}
		return extent;
	}	

	//To get the latest date and time for setting file name 
	public static String getReportName() 
	{
		Date d = new Date();
		String fileName = "AutomationReport_" + d.toString().replace(":", "_").replace(" ", "_")+".html";
		return fileName;		
	}
}