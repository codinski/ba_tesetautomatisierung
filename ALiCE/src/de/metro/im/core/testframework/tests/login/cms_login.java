package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public class cms_login extends BaseTestConfig {
	
	
	public cms_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url", dataProviderClass = TestDataProvider.class, description="Calling the webpage url.")
	public void openWebApp(String url){
		assertThat(pp.cms_loginpg.open(url)).isNotNull();
	}
	
	@Test(description="Checking if the user is at the login page.")
	public void isLoginAppletAvailable(){
		assertThat(pp.cms_loginpg.isLoginFormAvailable()).isTrue();
		
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, description="Trying to log in the webapplikation.", dependsOnMethods={"isLoginAppletAvailable"})
	public void loginUser(String user, String pw){
		pp.cms_loginpg.loginAs(user, pw);
		assertThat(pp.cms_loginpg.getValidationErrorMessage()).isEmpty();
			
	}
	
	@Test(description="Verifying the landing page after login is the welcome page. verification is done by looking for the search form.", dependsOnMethods={"loginUser"})
	public void positionIsLandingPage(){
		assertThat(pp.cms_landinpg.getCurrentUserInfo()).isNotEmpty();
			
	}
	
//	@Test(description="Logs the user out after test. Returns logout message. ", dependsOnMethods={"positionIsLandingPage"})
//	public void logoutUser(){
//		assertThat(pp.cms_landinpg.logout()).isNotNull();
//		
//	}
	
	
}
