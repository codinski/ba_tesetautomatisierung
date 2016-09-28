package de.metro.im.core.applications.pages.oo;


import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class OO_OrderDetails extends ABasePage {

	@FindBy(name="SaveHeaderAndNext") private WebElement confirmOrder;
	@FindBy(css="h3.tab.active") private WebElement currentTab;
	@FindBy(css="span.systemField") private WebElement userInfo;
	private WebDriver driver;
	
	public OO_OrderDetails(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}
	
	@Override
	public IPage logout() {
		throw new NotImplementedException("This page conceptually has no possibility to log you out.");
	}
	
	public OO_OrderSummary confimOrder(){
		DriverUtils.waitForWebelementAndGet(confirmOrder, 3, driver).click();
		return new OO_OrderSummary(driver);
	}
	
	public String currentTab(){
		return DriverUtils.waitForWebelementAndGet(currentTab, 3, driver).getText();
	}

	public OO_OrderDetails goTo() {
		// TODO Auto-generated method stub
		return new OO_OrderDetails(driver);
	}

	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(userInfo, 3, driver).getText();
	}



}
