package de.metro.im.core.applications.pages.nrv;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;


public class Nrv_Login extends ABaseLoginPage {
	

	@FindBy(css="h1")
	private WebElement headerTitle;
	private WebElement bkzList;
	private WebElement j_password;
	private WebElement username;
	private WebElement submitButton;
	@FindBy(id="login-error")
	private WebElement AlrtErrTxt;
	@FindBy(id="login-error")
	private WebElement loginValidationError;
	private WebElement loginForm;
	private WebElement newPassword;
	
	public Nrv_Login(WebDriver driver) {
		super(driver);
//		this.driver = driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(this.driver, this);
	}

	public String getTitle(){
		return driver.getTitle();
	}
	
	public String getHeader(){
		return headerTitle.getText();
	}

	public String getValidationErrorMessage() {
		
		if(isPasswordExpired()) return headerTitle.getText();
		
		String valErrMsg = "";
		if(DriverUtils.isVisible(loginValidationError, driver)) valErrMsg = loginValidationError.getText();
		return valErrMsg;
	}

	private boolean isPasswordExpired() {
		return DriverUtils.isVisible(newPassword, driver);
		
	}

	@Override
	public ABasePage loginSubmit() {
		submitButton.click();
		return null;
	}


	@Override
	public void typePassword(String _password) {
		j_password.clear();
		j_password.sendKeys(_password);
	}


	@Override
	public void typeUsername(String _username) {
		username.clear();
		username.sendKeys(_username);
	}


	public boolean hasHeaderWithTitle(String _titleText) throws TimeoutException {
		System.out.println(driver.getTitle());
		if (getHeader().equals(_titleText)) {
			return true;
		} else {
			
			return false;
		}
	}
	
	public void loginAs(String storeNo, String userName, String password){
		new Select(bkzList).selectByVisibleText(storeNo);
		super.loginAs(userName, password);
	}


	@Override
	public IPage logout() {
		throw new NotImplementedException("Not implemented for this page");
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("Not implemented for this page");
	}

	public boolean isLoginAppletVisible() {
		return DriverUtils.isVisible(loginForm, driver);
	}

}
