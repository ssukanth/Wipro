package com.ebay.wipro.testcases;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.PatternLayout;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.ebay.wipro.utilities.SeleniumUtils;
//import com.ebay.wipro.utilities.reports.ExtentManager;
import com.ebay.wipro.utilities.reports.ExtentUtils;
import com.ebay.wipro.utilities.reports.TestngListener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TestBase extends SeleniumUtils {
	public static String datStr;
	public  static File fl;
	public  static FileAppender fa;
	public  static PatternLayout pl;
	public   static AppiumDriver<MobileElement> driver;
	public static ExtentReports extent;
	  
	  public TestBase() {
		 super();
		 }
	  
	  public AppiumDriver getDriver() {
	        return this.driver;
	    }
	  
		@BeforeSuite
		//@Parameters("aPort")
		public void setup(){
			System.out.println("In the setup");
			Date d= new Date();
			SimpleDateFormat sd= new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
			datStr=sd.format(d).replaceAll("-", "").replaceAll(":", "").replace(" ", "_");
			resLoc=new File(".").getAbsolutePath()+"/Results/"+datStr;
			System.out.println("Effective result locations is :"+resLoc);
			fl= new File (resLoc);
			fl.mkdir();	
			lg= LogManager.getLogger(this.getClass());
			pl=new PatternLayout(" %L %d{ISO8601}- %m%n");	
			extent = ExtentUtils.createInstance();
			
		}
	@BeforeMethod
	public void launchServer() {
		driver=launchApp();
		System.out.println("LAUNCHED THE APPLICATOIN ....TESTING STARTS");
	}
	
	@AfterMethod
	public void testStatus(ITestResult t) {
		if(t.getStatus()==ITestResult.FAILURE) {
			takeScreenshot(resLoc+"/"+t.getName());
		}
		
		try {
			driver.removeApp("com.ebay.mobile");
			lg.debug("Removed the App");
			Runtime.getRuntime().exec("adb kill-server");
			lg.debug("Killed the server");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
