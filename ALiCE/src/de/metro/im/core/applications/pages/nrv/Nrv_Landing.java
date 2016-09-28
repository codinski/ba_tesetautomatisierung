package de.metro.im.core.applications.pages.nrv;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.utils.DriverUtils;


public class Nrv_Landing extends ABasePage {
	
	
	private WebElement currentUser;
	@FindBy(css="u")
	private WebElement logoutButton;
	@FindBy(linkText="Administration")
	private WebElement startAdmin;
	@FindBy(css="div.header-tabnavigation")
	private WebElement topNavigation;
	
	public Nrv_Landing(WebDriver driver) {
		super(driver);
		this.driver = driver;
		
		PageFactory.initElements(this.driver, this);
	}


	public String getTitle(){
		return driver.getTitle();
	}
	
	@Override
	public Nrv_Login logout() {
	   logoutButton.click();
		return new Nrv_Login(driver);
	}


	@Override
	public String getCurrentUserInfo() {
		return DriverUtils.waitForWebelementAndGet(currentUser, 3, driver).getText();
	}
	
	public Nrv_Admin startAdministration(){
		startAdmin.click();
		return new Nrv_Admin(this.driver);
	}

	public boolean isTopNavigationVisible() {
		return DriverUtils.isVisible(topNavigation, driver);
	}

	
}
