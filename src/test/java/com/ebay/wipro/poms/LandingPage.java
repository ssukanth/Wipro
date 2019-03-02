package com.ebay.wipro.poms;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.ebay.wipro.utilities.SeleniumUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class LandingPage extends SeleniumUtils {
	
	public LandingPage() {
		PageFactory.initElements(driver, this);
	}

	public MobileElement getIconHamburger() {
		findEleById("logo");
		System.out.println("Found the loggo");
		return findEleByAccessibilityId("Main navigation, open");
	}

	public MobileElement getEdit_Search() {
		return findEleById("search_box");
	}


	public MobileElement getXyz() {
		return findEleByClassName("android.widget.Button");
	}



	public void clickIconHamburger() {
		getIconHamburger().click();
		System.out.println("Pass: Hamburger Clicked");
	}
	public  void enterSearch() {
		getIconHamburger().sendKeys("Please Ignmomr");
		getEdit_Search().click();
		  try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  getXyz().click();
	}
	
}
