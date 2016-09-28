package de.metro.im.core.applications.pages.betty;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABaseLoginPage;
import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.base.IPage;
import de.metro.im.core.applications.utils.DriverUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Betty_Login extends ABaseLoginPage {

	@FindBy(id="ex-login-submit")
	private WebElement loginSubmitButton;
	@FindBy(id="ex-login-name")
	private WebElement usernameInputForm;
	@FindBy(id="ex-login-pass")
	private WebElement userPasswordInputForm;
//	@FindBy(xpath="//span[contains(., 'Als Kunde anmelden')]")
//	private WebElement loginAsCustomerButton;
	@FindBy(linkText="logout")
	private WebElement logoutButton;
	
	@FindBy(css= "p.form-group.login-toggle")
	private WebElement loginToggleButton;
	
	@FindBy(className="login-center-form")
	private WebElement loginForm;
	@FindBy(css="div.alert.alert-danger > span")
	private WebElement loginValidationError;
	
	public Betty_Login(WebDriver driver) {
		super(driver);
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(this.driver, 20);
		PageFactory.initElements(this.driver, this);
	}
	
	@Override
	public String open(String url){
		driver.get(url);
		return driver.getTitle();
	}
	
	public String getValidationErrorMessage() {
		String valErrMsg = "";
		if(DriverUtils.isVisible(loginValidationError, driver)) valErrMsg = loginValidationError.getText();
		return valErrMsg;
	}
	
	public void loginAsCustomer(String username, String password){ //oder neue methode loginassemployee
		useCustomerLogin();
		typeUsername(username);
		typePassword(password);
		loginSubmit();
	}
	
	public ABasePage loginAsEmployee(String username, String password){ //oder neue methode loginassemployee
		useEmployeeLogin();
		typeUsername(username);
		typePassword(password);
		return loginSubmit();
	}

	private void useEmployeeLogin() {
		if(loginSubmitButton.getText().contains("Kunde")){
			toggleLoginType();
		}
	}
	
	private void useCustomerLogin() {
		if(loginSubmitButton.getText().contains("Mitarbeiter")){
			toggleLoginType();
		}
	}

	private void toggleLoginType() {
		loginToggleButton.click();
	}

	@Override
	public ABasePage loginSubmit() {
		loginSubmitButton.click();
		return new Betty_Landing(driver);
	}

	@Override
	public void typePassword(String _password) {
		userPasswordInputForm.clear();
		userPasswordInputForm.sendKeys(_password);

	}

	@Override
	public void typeUsername(String _username) {
		usernameInputForm.clear();
		usernameInputForm.sendKeys(_username);

	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException();
	}

	public boolean isLoginFormAvailable() {
		return loginForm != null;
	}

	@Override
	public IPage logout() {
		throw new NotImplementedException();
	}

}
