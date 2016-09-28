package de.metro.im.core.applications.pages.m24;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Metro_Login extends ABaseLoginPage {
	
	@FindBy(name="pageph_0$UIHeader$GenericUserDataControl$ctl00$UILoginUserName")
	private WebElement usernameInput;
	@FindBy(name="pageph_0$UIHeader$GenericUserDataControl$ctl00$UILoginPassword")
	private WebElement passwordInput;
	@FindBy(id="pageph_0_UIHeader_GenericUserDataControl_ctl00_UILoginUser")
	private WebElement loginSubmitButton;
	@FindBy(id="pageph_0_UIHeader_GenericUserDataControl_ctl00_UIErrorMessageLabel")
	private WebElement loginValidationError;
	@FindBy(id="pageph_0_UIHeader_GenericUserDataControl_ctl00_UINotLoggedUserPanel")
	private WebElement loginApplet;		
	@FindBy(id="pageph_0_UIHeader_GenericUserDataControl_MyMetroUserDataControl_UILoginText")
	private WebElement myDataButton;
	@FindBy(id="pageph_0_UIHeader_GenericUserDataControl_ctl00_UILoginText")
	private WebElement loginAppletOpenButton;
	@FindBy(id="pageph_0_UIHeader_GenericUserDataControl_ctl00_UILoginUser")
	private WebElement confirmLogin;
	
	public Metro_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}
	
	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(loginValidationError, driver)) valErrMsg = loginValidationError.getText();
		return valErrMsg;
	}

	@Override
	public Metro_Landing loginSubmit() {
		confirmLogin.click();
		return new Metro_Landing(driver);
	}
	
	
	public Metro_Landing loginAs(String username, String password) {
		typeUsername(username);
		typePassword(password);
		return loginSubmit();
	}

	@Override
	public void typePassword(String _password) {
		passwordInput.clear();
		passwordInput.sendKeys(_password);
	}

	@Override
	public void typeUsername(String username) {
		usernameInput.clear();
		usernameInput.sendKeys(username);
	}
	
	public boolean isLoginAppletVisible(){
		boolean visible = DriverUtils.isVisible(loginApplet, driver);
		return visible;
	}
	
	public Metro_Login openLoginApplet(){
		if(isLoginAppletVisible()){
			loginAppletOpenButton.click();
		}
		return this;
	}
	
	public Metro_Landing clickLoginIn() {
		loginSubmitButton.click();
		return new Metro_Landing(driver);
	}


	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page.");
	}

	@Override
	public String getCurrentUserInfo() {
		return myDataButton.getText();
	}


}
