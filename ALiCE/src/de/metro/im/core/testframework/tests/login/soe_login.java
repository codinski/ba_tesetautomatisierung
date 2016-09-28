package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public final class soe_login extends BaseTestConfig {
	SoftAssertions sft = new SoftAssertions();	
	
	private soe_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the webpage url. ", priority=0, groups="soelogin")
	public void openWebApp(String url, String pageTitle){
		assertThat(pp.soe_loginpg.open(url)).containsIgnoringCase(pageTitle);
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp", groups="soelogin")
	public void isLoginAppletAvailable(){
		assertThat(pp.soe_loginpg.isLoginFormAvailable()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, description="Trying to log in the web application.", dependsOnMethods="isLoginAppletAvailable", groups="soelogin")
	public void loginUser(String user, String pw){
		pp.soe_loginpg.loginAs(user, pw);
		assertThat(pp.soe_loginpg.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(description="Verifies the landing page position by checking the visibility of the customer search box.", priority=3, dependsOnMethods="loginUser", groups="soelogin")
	public void positionIsLandingPage(){
		assertThat(pp.soe_landinpg.isSearchBoxAvailable()).isTrue();
	}
	
}
