package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
//@Listeners(TestListener.class)
import de.metro.im.core.testframework.tests.TestDataProvider;

public class kwk_login extends BaseTestConfig {
	
	public kwk_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the webpage url. ", priority=0)
	public void openWebApp(String url, String pageTitle){
		assertThat(pp.kwk_Loginpg.open(url)).contains(pageTitle);
		
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
		assertThat(pp.kwk_Loginpg.isLoginAppletAvailable()).isTrue();
		
	}
	
	@Test(dataProvider = "user_pw_bkz", dataProviderClass = TestDataProvider.class, description="Trying to log in the webapplication.", dependsOnMethods="isLoginAppletAvailable")
	public void loginUser(String user, String pw, String bkz){
		pp.kwk_Loginpg.loginAs(bkz, user, pw);
		assertThat(pp.kwk_Loginpg.getValidationErrorMessage()).isEmpty();
	
	}
	
	@Test(description="Verifying the landing page is displayed.", dependsOnMethods="loginUser")
	public void positionIsLandingPage(){
		assertThat(pp.kwk_Landing.isApplicationTypeDropdownMenuVisible()).isTrue();
	}
		
	
}
