package de.metro.im.core.applications.pages.sitecore;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;

public class Sitecore_Login extends ABaseLoginPage {

	private WebElement Login_Login;
	private WebElement Login_Password;
	private WebElement Login_UserName;
	private WebElement FailureText2;
	private WebElement LoginPanelBox;
	
	public Sitecore_Login(WebDriver driver) {
		super(driver);
//		this.driver = driver;
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(this.driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implementes for this page!");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implementes for this page!");
	}

	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(FailureText2, driver)) valErrMsg = FailureText2.getText();
		return valErrMsg;
	}

	@Override
	public IPage loginSubmit() {
		Login_Login.click();
		return new Sitecore_Landing(driver);
	}

	@Override
	public void typePassword(String _password) {
		Login_Password.clear();
		Login_Password.sendKeys(_password);
	}

	@Override
	public void typeUsername(String _username) {
		Login_UserName.clear();
		Login_UserName.sendKeys(_username);
	}

	public boolean loginPanelAvailable(){
		return DriverUtils.isVisible(LoginPanelBox, driver);	
	}

}
