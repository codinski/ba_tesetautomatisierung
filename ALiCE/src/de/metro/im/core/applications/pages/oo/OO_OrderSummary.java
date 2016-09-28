package de.metro.im.core.applications.pages.oo;


import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class OO_OrderSummary extends ABasePage {

	@FindBy(css="input.btn.btn-success.pull-right.submitOrderButton") 
	private WebElement submitOrder;
	@FindBy(id="AgreeTerms") 
	private WebElement confirmBusinessTerms;
	@FindBy(id="AgreePrivacy") 
	private WebElement confirmPrivacyTerms;
	@FindBy(css="h3.tab.active") private WebElement currentTab;
	
	private WebDriver driver;
	
	public OO_OrderSummary(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
		
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("This page conceptually has no possibility to log you out.");
				
	}
	
	public OO_OrderSuccess submitOrder(){
		DriverUtils.waitForWebelementAndGet(submitOrder, 3, driver).click();
		return new OO_OrderSuccess(driver);
	}
	
	
	public OO_OrderSummary confirmAllTerms(){
		confirmBusinessTerms.click();
		confirmPrivacyTerms.click();
		return this;
	}

	public String currentTabName(){
		return DriverUtils.waitForWebelementAndGet(currentTab, 3, driver).getText();
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("this page does not provide this information. Please proceed to the landing page.");
	}


}
