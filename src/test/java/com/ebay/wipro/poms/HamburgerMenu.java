package com.ebay.wipro.poms;

import org.openqa.selenium.support.PageFactory;

import com.ebay.wipro.utilities.SeleniumUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class HamburgerMenu extends SeleniumUtils {
	MobileElement iconSignIn;
	AppiumDriver driver;
	
	public HamburgerMenu(AppiumDriver dri) {
		this.driver= dri;
	}
	
	public MobileElement getIconSignIn() {
		return findEleById("textview_sign_out_status");
	}


	public void clickSignIn() {
		getIconSignIn().click();
	}
	
	
}
