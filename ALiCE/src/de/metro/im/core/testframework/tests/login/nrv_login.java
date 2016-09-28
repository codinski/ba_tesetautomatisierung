package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public class nrv_login extends BaseTestConfig {
	
	public nrv_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());			
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the web app url. ", priority=0, groups="nrvlogin")
	public void openWebApp(String url, String expectedPageTitle){
		assertThat(pp.nrv_Loginpg.open(url)).containsIgnoringCase(expectedPageTitle);
	}
	
	@Test(description="Checks if the login applet ios available. ", dependsOnMethods="openWebApp", groups="nrvlogin")
	public void isLoginAppletAvailable(){
		assertThat(pp.nrv_Loginpg.isLoginAppletVisible()).isTrue();
	}
	
	@Test(dataProvider = "user_pw_bkz", dataProviderClass = TestDataProvider.class, description="Trying to log in to the web application.", dependsOnMethods="isLoginAppletAvailable", groups="nrvlogin")
	public void loginUser(String user, String pw, String bkz){
		pp.nrv_Loginpg.loginAs(bkz, user, pw);
		assertThat(pp.nrv_Loginpg.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(description="Verifies position is landing page by checking the navigation bar visibility", priority=3, dependsOnMethods="loginUser", groups="nrvlogin")
	public void positionIsLandingPage(){
		assertThat(pp.nrv_Landing.isTopNavigationVisible()).isTrue();
	}
	
}
