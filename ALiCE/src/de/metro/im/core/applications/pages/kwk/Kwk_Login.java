package de.metro.im.core.applications.pages.kwk;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;


public class Kwk_Login extends ABaseLoginPage {
	
	private WebElement usernummer;
	private WebElement password;
	private WebElement anmelden;
	private WebElement bkz;
	private WebElement userinfo;
	private WebElement FORM_LOGIN;
	
	public Kwk_Login(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}


	@Override
	public ABasePage loginSubmit() {
		anmelden.click();
		return new Kwk_Landing(driver);
	}

	@Override
	public void typePassword(String _password) {
		password.clear();
		password.sendKeys(_password);

	}

	@Override
	public void typeUsername(String _username) {
			usernummer.clear();
			usernummer.sendKeys(_username);
	}

	
	public boolean isLoginAppletAvailable() {
		return DriverUtils.isVisible(FORM_LOGIN, driver);
	}

	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(FORM_LOGIN, driver)) valErrMsg = FORM_LOGIN.getText();
		return valErrMsg;
	}
	
	
	public void loginAs(String storeno, String username, String password) {
		new Select(bkz).selectByVisibleText(storeno);
		bkz.sendKeys(storeno);
		super.loginAs(username, password);
		
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public String getCurrentUserInfo() {
		return userinfo.getText();
	}


}
