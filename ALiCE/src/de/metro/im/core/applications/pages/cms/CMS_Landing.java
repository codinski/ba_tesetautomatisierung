package de.metro.im.core.applications.pages.cms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;

public class CMS_Landing extends ABasePage {
	
	private WebDriver driver;
	
	@FindBy(css="td.header") private WebElement header; 
	@FindBy(xpath="//a[contains(@href, '/cms/modules/logoff')]") private WebElement logoutButton;
	@FindBy(css="h3") private WebElement currentUserInfo;
	public CMS_Landing(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	public CMS_Login logout() {
		logoutButton.click();
		return new CMS_Login(driver);
	}

	public String getHeaderText() {
		return DriverUtils.waitForWebelementAndGet(header, 10, driver).getText();
	}

	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(currentUserInfo, 3, driver).getText();
	}
}
