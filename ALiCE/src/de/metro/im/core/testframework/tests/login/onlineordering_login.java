package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;


public class onlineordering_login extends BaseTestConfig {
	public onlineordering_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}

	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the Online Ordering webpage. ", priority=0, groups="oologin")	
	public void openWebApp(String url, String pageTitle){
		assertThat(pp.oo_login.open(url)).contains(pageTitle);
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp", groups="oologin")
	public void isLoginAppletAvailable(){
		assertThat(pp.oo_login.isLoginAppletVisible()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, description="Performs a successfull login.", dependsOnMethods="isLoginAppletAvailable", groups="oologin")
	public void loginUser(String user, String pw){
		pp.oo_login.loginAs(user, pw);
		assertThat(pp.oo_login.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(description="Verifying the landing page after login is the welcome page.", dependsOnMethods="loginUser", groups="oologin")
	public void positionIsLandingPage(){
		assertThat(pp.oo_landing.getGreetingMessage()).isNotEmpty();
	}
	
}
