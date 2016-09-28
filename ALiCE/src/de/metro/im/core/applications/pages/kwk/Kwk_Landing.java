package de.metro.im.core.applications.pages.kwk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Kwk_Landing extends ABasePage {
	
	@FindBy(xpath="//div[@id='userinfo']/a") private WebElement logout;
	private WebElement userinfo;
	@FindBy(css="p") private WebElement logoutConfirmation;
	private WebElement switchGsType;
	
	public Kwk_Landing(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public Kwk_Login logout() {
		logout.click();
		return new Kwk_Login(driver);
	}
	
	public String getLogoutMsg(){
		return DriverUtils.waitForWebelementAndGet(logoutConfirmation, 10, driver).getText();
	}

	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(userinfo, 3, driver).getText();
	}

	public boolean isApplicationTypeDropdownMenuVisible() {
		return DriverUtils.isVisible(switchGsType, driver);
	}

}
