/**
 * 
 */
package de.metro.im.core.applications.pages.oo;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.utils.DriverUtils;

/**
 * @author christoph.zabinski
 *
 */
public class MyMetro_Login extends OO_Login {
	
	private WebElement username;
	private WebElement password;
	@FindBy(id="body_login")
	private WebElement body_login;
	@FindBy(css="div.AlrtErrTxt")
	private WebElement AlrtErrTxt;
	@FindBy(className="error-message-container")
	private WebElement loginValidationError;
	@FindBy(linkText="Jetzt anmelden")
	private WebElement loginNow;
	@FindBy(id="kc-form-login")
	private WebElement loginApplet;
	
	
	public MyMetro_Login(WebDriver driver) {
		super(driver);
		this.driver = driver;

//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
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

	public boolean isLoginAppletVisible() {
		DriverUtils.switchToNewWindow();
		return DriverUtils.isVisible(loginApplet, driver);
	}


}
