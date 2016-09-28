/**
 * 
 */
package de.metro.im.core.applications.pages.oo;


import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

/**
 * @author christoph.zabinski
 *
 */
public class OO_Login extends ABaseLoginPage {
	
	private WebElement username;
	private WebElement password;
	@FindBy(id="body_login")
	private WebElement body_login;
	@FindBy(css="div.AlrtErrTxt")
	private WebElement AlrtErrTxt;
	@FindBy(className="error-message-container")
	private WebElement loginValidationError;
	@FindBy(id="kc-form-login")
	private WebElement loginApplet;
	
	
	public OO_Login(WebDriver driver) {
		super(driver);
		this.driver = driver;

		PageFactory.initElements(this.driver, this);
	}
	
	@Override
	public String open(String url){
		driver.get(url);
		return driver.getTitle();
	}

	@Override
	public OO_Landing loginSubmit() {
		body_login.click();
		return new OO_Landing(driver);
	}

	@Override
	public void typePassword(String passwordStr) {
		password.clear();
		password.sendKeys(passwordStr);
	}

	@Override
	public void typeUsername(String usernameStr) {
		username.clear();
		username.sendKeys(usernameStr);
	}

	public String getValidationErrorMessage() {
		return DriverUtils.isVisible(loginValidationError, driver)? loginValidationError.getText() : "";
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page");
	}

	@Override
	public String getCurrentUserInfo() {
		return "Not implementen for this page";
	}

	public boolean isLoginAppletVisible() {
		return DriverUtils.isVisible(loginApplet, driver);
	}


}
