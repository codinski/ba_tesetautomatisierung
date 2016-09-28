package de.metro.im.core.applications.pages.sitecore;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Sitecore_Landing extends ABasePage {
	@FindBy(css="#SystemMenu")
	private WebElement SystemMenu;
	@FindBy(xpath="//tr[@id='ctl46']/td[2]")
	private WebElement logoutBtn;
	private WebElement MainPanel;
	
	public Sitecore_Landing(WebDriver _driver) {
		super(_driver);
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(this.driver, 20);
		PageFactory.initElements(this.driver, this);
	}

//	@Override
//	public String logout() {
//		SystemMenu.click();
//		logoutBtn.click();
//		return driver.getCurrentUrl();
//	}
	
	@Override
	public Sitecore_Login logout() {
		SystemMenu.click();
		logoutBtn.click();
		return new Sitecore_Login(driver);
	}
	



	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implementes for this method!");
	}

	public boolean isMainPanelAvailable() {
		return DriverUtils.isVisible(MainPanel, driver);
	}

}
