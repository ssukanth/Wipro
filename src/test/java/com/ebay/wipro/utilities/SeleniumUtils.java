package com.ebay.wipro.utilities;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class SeleniumUtils extends FileUtilities {
	public static AppiumDriver<MobileElement> driver;
	public static Logger lg;
	public static EventFiringWebDriver eWebd;
	public static WebDriverWait initWait;
	public static StackTraceElement[] stackTraceElements;
	public static String resLoc;
	
	/*
	* Method Name: launchApp
	* Objective :Launches the Appium server programatically on a specified ip and port 
	* Param: 
	* return: Appiumdriver started on a port and ip
	*/	
	public static AppiumDriver  launchApp() {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		FileUtilities fUtils= new FileUtilities();
		Map<String, String >emuProps=null;
		Map<String, String> emuCaps=null;
		try {
			emuProps=fUtils.readEmulatorProp();
			emuCaps = fUtils.readEmulatorCaps();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lg.debug("Starting emulator for '" + emuCaps.get("avd") + "' ...on ip:"+emuProps.get("ip")+"with Port:"+emuProps.get("aPort"));
	     String appiumServiceUrl;
		lg.debug("Starting The application ");
		 AppiumDriverLocalService appiumService = AppiumDriverLocalService
   			.buildService(new AppiumServiceBuilder()
   					.withAppiumJS(new File("C:\\Users\\Businessman\\AppData\\Local\\appium-desktop\\app-1.5.0\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"))
   					.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
   					.withIPAddress(emuProps.get("ip"))
   					.usingPort(Integer.parseInt(emuProps.get("aPort")))
   					.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
   					.withArgument(GeneralServerFlag.LOG_LEVEL, "debug"));
   	appiumService.start();
       appiumServiceUrl = appiumService.getUrl().toString();
       lg.debug("Appium Service Address : - "+ appiumServiceUrl);
       for(Map.Entry<String, String> m:emuCaps.entrySet()) {
    	   lg.debug(m.getKey()+" : " +m.getValue());
       }
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("avd",emuCaps.get("avd"));
		caps.setCapability("deviceName", emuCaps.get("deviceName"));						
		caps.setCapability("platformName", emuCaps.get("platformName"));
		caps.setCapability("platformVersion", emuCaps.get("platformVersion"));
		caps.setCapability("clearSystemFiles", emuCaps.get("clearSystemFiles"));
		//caps.setCapability("browserName", emuCaps.get("browserName"));
		caps.setCapability("app", new File("").getAbsolutePath()+"\\src\\test\\resources\\AppInstallationFiles\\eBay.apk");
		caps.setCapability("appPackage", emuCaps.get("appPackage"));
		caps.setCapability("appActivity", emuCaps.get("appActivity"));
		caps.setCapability("AUTO_GRANT_PERMISSIONS", emuCaps.get("AUTO_GRANT_PERMISSIONS")); 
		caps.setCapability("noReset", emuCaps.get("noReset"));
		
		System.setProperty("webdriver.chrome.driver",new File("").getAbsolutePath()+"\\src\\test\\resources\\Drivers\\chromedriver.exe");
		try {
			driver = new AndroidDriver<>(new URL(appiumServiceUrl), caps);
			
		} catch (MalformedURLException e) {
			lg.debug(e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		initWait= new WebDriverWait(driver,45);
		lg.debug("Completed The Method :"+stackTraceElements[2].getMethodName());

		return driver;
	}
	
	/*
	* Method Name: findEleById
	* Objective :find element by id
	* Param: id string 
	* return:Mobile element
	*/	
	public static MobileElement findEleById(final String id) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		lg.debug("Finding the element for id"+id);
		return initWait.until(new ExpectedCondition<MobileElement>() {

			@Override
			public MobileElement apply(WebDriver input) {
				// TODO Auto-generated method stub
				return (MobileElement) driver.findElementById(id);
			}			
			
		});
		
	}
	
	/*
	* Method Name: findEleByAccessibilityId
	* Objective :find element by  content-desc of the element
	* Param: content- desc string 
	* return:MobileElement
	*/		
	public static MobileElement findEleByAccessibilityId(final String accessibility_Id) {
		lg.debug("Finding the element for accessibility_Id"+accessibility_Id);
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		return initWait.until(new ExpectedCondition<MobileElement>() {

			@Override
			public MobileElement apply(WebDriver input) {
				// TODO Auto-generated method stub
				return (MobileElement) driver.findElementByAccessibilityId(accessibility_Id);
			}			
			
		});
	}
	
	/*
	* Method Name: findEleByClassName
	* Objective :find element by  class of the element
	* Param: class string 
	* return:MobileElement
	*/
	public static MobileElement findEleByClassName(final String className) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		lg.debug("Finding the element for className"+className);
		
		return initWait.until(new ExpectedCondition<MobileElement>() {

			@Override
			public MobileElement apply(WebDriver input) {
				// TODO Auto-generated method stub
				return (MobileElement) driver.findElementByClassName(className);
			}			
			
		});
	}
	
	/*
	* Method Name: findEleByXPath
	* Objective :find element by  xpath of the element
	* Param: xpath string 
	* return: Mobile Element
	*/
	public static MobileElement findEleByXPath(final String xpath) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		lg.debug("Finding the element for xpath"+xpath);
		return initWait.until(new ExpectedCondition<MobileElement>() {
			@Override
			public MobileElement apply(WebDriver input) {
				// TODO Auto-generated method stub
				return (MobileElement) driver.findElementByXPath(xpath);
			}			
			
		});	}
	
	/*
	* Method Name: findElesByAccessibilityId
	* Objective :find the list of elements with an accessibility id
	* Param: accessibility string
	* return:List of elements with the same accessibility id
	*/
	
	public static  List<MobileElement>findElesByAccessibilityId(String accId){
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		return driver.findElementsByAccessibilityId(accId);
	}
	
	/*
	* Method Name: takeScreenshot
	* Objective : take the screen shot on failure of a test case
	* Param: destination location string
	* return:
	*/
	
	public static void takeScreenshot(String destLoc) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File(destLoc));
		} catch (IOException e) {
			lg.debug("The copying the picture failed");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		lg.debug("Completed The Method :"+stackTraceElements[2].getMethodName());
	}
	
	
	/*
	* Method Name: sleepTimer
	* Objective : static wait for pausing the execution for some time
	* Param: time in milli seconds
	* return:
	*/
	public static void sleepTimer(int mSecs) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		try {
			Thread.sleep(mSecs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	* Method Name: clickEle
	* Objective : click on an element after waiting for element to be clicking 
	* Param: ele- Mobile element ot click , timeOut- time to wait before throwing exception if element not clickable
	* return:
	*/
	public static void clickEle(MobileElement ele, int timeOut) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		WebDriverWait wait= new WebDriverWait(driver,timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(ele));
		try{
			sleepTimer(2);
			ele.click();
		}catch(WebDriverException we) {
			lg.debug("The element is not clickable for the first time");
			if(we.getMessage().indexOf("is not clickable at point")>0||we.getMessage().indexOf("stale element reference")>0) {
				lg.debug("Element is either stale or not clickable at the place");
				sleepTimer(2);
				ele.click();
			}else {
				throw(we);
			}
		}
	}
	
	/*
	* Method Name: findElementsByXpaths
	* Objective : get the list of all elements having mentioned xpath
	* Param: the xpath of the elements
	* return: list of Mobile elements
	*/
	public static List<MobileElement> findElementsByXpaths(String xpath){
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		List<MobileElement> itemList=null;
			for(int i=0;i<5;i++) {
			itemList=driver.findElementsByXPath(xpath);
			if(itemList.size()>=2) {
				lg.debug("List loaded ");
				break;
			}else {
				lg.debug("List not loaded for the :"+i+" time");
				sleepTimer(3000);
			}
		}
		return itemList;
	}
	
	/*
	* Method Name: scrollUpAndDown
	* Objective : scrolls up and down for loading the elements
	* Param: 
	* return: 
	*/
	public static void scrollUpAndDown() {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		Dimension size;
		String conx=driver.getContext();
		lg.debug("The Default contect is :"+conx);
		if(!(conx.equalsIgnoreCase("NATIVE_APP"))) {
			driver.context("NATIVE_APP");
		}

		size = driver.manage().window().getSize();
		int startx = size.width / 3;
		int dStarty = (int) (size.height * 0.85);
		int dEndy = (int) (size.height * 0.15);
		lg.debug("starty = " + dStarty + " ,endy = " + dEndy + " , startx = " + startx);
		driver.swipe(startx, dStarty, startx, dEndy, 2000);
		sleepTimer(2);
		int uStarty = (int) (size.height * 0.15);
		int uEndy = (int) (size.height * 0.85);
		lg.debug("starty = " + uStarty + " ,endy = " + uEndy + " , startx = " + startx);
		driver.swipe(startx, uStarty, startx, uEndy, 500);
		driver.context(conx);
	}
	
	/*
	* Method Name: scroll up
	* Objective : scrolls up for loading the elements
	* Param: 
	* return: 
	*/
	
	public static void scrollUp() {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		Dimension size;
		String conx=driver.getContext();
		lg.debug("The Default contect is :"+conx);
		if(!(conx.equalsIgnoreCase("NATIVE_APP"))) {
			driver.context("NATIVE_APP");
		}

		size = driver.manage().window().getSize();
		int startx = size.width / 3;
		int uStarty = (int) (size.height * 0.30);
		int uEndy = (int) (size.height * 0.15);
		lg.debug("starty = " + uStarty + " ,endy = " + uEndy + " , startx = " + startx);
		driver.swipe(startx, uStarty, startx, uEndy, 500);
		driver.context(conx);
	}
	
	/*
	* Method Name: moveToAndAction
	* Objective : Move to the element and perform the action using Actions class
	* Param:  action- the action to be perform
	* 			"" - for mave to 
	* 			tap- for tapping an element
	* 			press - for pressing the element and release
	* 		ele - element ot work on 	
	* return: 
	*/
	
	public static void moveToAndAction(String action,MobileElement ele) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		TouchAction a= new TouchAction(driver);
		switch(action.toLowerCase()) {
		case "":a.moveTo(ele).perform();break;
		case "tap":a.tap(ele).perform();break;
		case "press" : a.press(ele).release().perform();
		}
	}
	
	
	/*
	* Method Name: moveToAndActionByOffset
	* Objective : Move to the element and perform the action using Actions class
	* Param:  action- the action to be perform
	* 			"" - for mave to 
	* 			tap- for tapping an element
	* 			press - for pressing the element and release
	* 		x,y - x and y coordinates to move to 	
	* return: 
	*/
	public static void moveToAndActionByOffset(String action,int x, int y) {
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		TouchAction a= new TouchAction(driver);
		switch(action.toLowerCase()) {
		case "":a.moveTo(x, y);break;
		case "tap":a.tap(x, y).perform();break;
		case "press":a.press(x, y).release();break;
		}
	}
	
	 public static void jsMovetoele(AppiumDriver driver, MobileElement ele)	 {	
		stackTraceElements = Thread.currentThread().getStackTrace();
		lg.debug("Running The Method :"+stackTraceElements[2].getMethodName());
		 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
	 }
	

}
