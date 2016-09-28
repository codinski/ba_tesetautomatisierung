package de.metro.im.core.applications.pages.shaper;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Shaper_Login extends ABaseLoginPage {
	
	private WebElement loginName;
	private WebElement passwordByLoginName;
	private WebElement doLogin2;
	@FindBy(css="div.tabContainer > span")
	private WebElement verificationErr;
	@SuppressWarnings("unused")
	private WebElement tabCard;
	@FindBy(id="header-metanavigation")
	private WebElement loginApplet;
	@FindBy(css="a.login_lightwindow")
	private WebElement loginButton;
	
	
	
	public Shaper_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(verificationErr, driver)) valErrMsg = verificationErr.getText();
		return valErrMsg;
	}

	@Override
	public IPage loginSubmit() {
		doLogin2.click();
		return new Shaper_Landing(driver);
	}
	
	public Shaper_Landing loginAs(String username, String password) {
		typeUsername(username);
		typePassword(password);
		return (Shaper_Landing) loginSubmit();
	}

	@Override
	public void typePassword(String password) {
		passwordByLoginName.clear();
		passwordByLoginName.sendKeys(password);

	}


	@Override
	public void typeUsername(String username) {
		loginName.clear();
		loginName.sendKeys(username);
		
	}

	public boolean isLoginAppletVisible(){
		return DriverUtils.isVisible(loginApplet, driver);
	}
	
	public Shaper_Login openLoginApplet(){
		if(isLoginAppletVisible()){
			loginButton.click();
		}
		return this;
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implemented for this page");
	}


}
