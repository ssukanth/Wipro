package com.ebay.wipro.poms;

import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import com.ebay.wipro.utilities.SeleniumUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class ItemSearchPage extends SeleniumUtils {
	private AppiumDriver dri;
	private LandingPage lnp;
	private MobileElement edit_SearchField;
	private List<MobileElement> cards_ItemTitleSearchResults;
	private String search_ItemPrice;
	public ItemSearchPage(AppiumDriver driver) {
		this.dri=driver;
		lnp= new LandingPage();
	}
	public MobileElement getEdit_SearchField() {
		edit_SearchField= findEleById("search_src_text");
		return edit_SearchField;
	}
	public List<MobileElement> getCards_ItemTitleSearchResults() {
		sleepTimer(2000);
		cards_ItemTitleSearchResults=dri.findElementsById("cell_collection_item");
		return cards_ItemTitleSearchResults;
	}
	
	public String getSearch_ItemPrice(int itemIndex) {
		int loadedItemSize=0;		
		for(int i=0;i<5;i++ ) {
			loadedItemSize=findElementsByXpaths(".//*[contains(@resource-id,'textview_item_price')]").size();
			lg.debug("The size of the result pre scroll"+loadedItemSize);

			if(loadedItemSize>itemIndex) {
				break;
			}else {
				scrollUp();
				sleepTimer(2000);
			}
		}
		search_ItemPrice=findElementsByXpaths(".//*[contains(@resource-id,'textview_item_price')]").get(itemIndex).getText();
		return search_ItemPrice;
	}
	public MobileElement searchCards_FindItemByTitle(String itemTitle) {
		String effectiveXpath=".//*[@id='' and contains(@text,'"+itemTitle+")]";
		System.out.println("The effective xpat for item is :"+effectiveXpath);
		return findEleByXPath(effectiveXpath);
	}
	
	public String searchAndSelectRandomItem(String sItem, int itemIndex) {
		lg.debug("Item to be selected is :"+itemIndex+1);
		TouchAction a= new TouchAction(dri);
		lnp.getEdit_Search().click();
		getEdit_SearchField().sendKeys(sItem);
		dri.hideKeyboard();
		sleepTimer(3000);// static wait for autocomplete to be visible
		System.out.println("Printing the element data");
		List<MobileElement>x1=findElementsByXpaths(".//*[contains(@text,'65')]");
		for(MobileElement m1:x1) {
			System.out.println("The Autocomplete si :"+m1.getText());
		}	
		
		x1.get(1).click();
		int loadedItemSize=0;
		for(int i=0;i<5;i++ ) {
			loadedItemSize=getCards_ItemTitleSearchResults().size();
			lg.debug("The size of the result pre scroll"+loadedItemSize);

			if(loadedItemSize>itemIndex) {
				break;
			}else {
				scrollUp();
				sleepTimer(3000);
			}
		}
		if(loadedItemSize<=itemIndex) Assert.assertTrue(false);
		lg.debug("The size of the result pre scroll"+getCards_ItemTitleSearchResults().size());
		sleepTimer(3000);
		for(MobileElement xs:getCards_ItemTitleSearchResults()) {
			lg.debug(xs.getText());
		}
		return getSearch_ItemPrice(itemIndex).substring(getSearch_ItemPrice(itemIndex).indexOf("$")+1).replace(",", "");
		
		
	}
	
}
