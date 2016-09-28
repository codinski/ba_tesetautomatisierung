package de.metro.im.core.testframework.tests.login;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

/**
 * Shaper wird in kürze abgestellt. Logintesten werden hierfür nicht durchgeführt. 
 * @author christoph.zabinski
 *
 */
@Deprecated()
public class shaper_login extends BaseTestConfig {
	
	public shaper_login() {
//		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the website url. ", priority=0)
	public void openWebApp(String url, String expectedPageTitle){
//		assertThat(pp.sh_login.open(url)).containsIgnoringCase(expectedPageTitle);
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
//		assertThat(pp.sh_login.openLoginApplet().isLoginAppletVisible()).isTrue();
	}
	
	@Test(dataProvider = "user_pw", dataProviderClass = TestDataProvider.class, description="Performs a successfull login.", dependsOnMethods="isLoginAppletAvailable")
	public void loginUser(String user, String pw){
//		pp.sh_login.loginAs(user, pw);
//		assertThat(pp.sh_login.getValidationErrorMessage()).isEmpty();
	}
	
	@Test(dataProvider = "landingPageVerificationArgument", dataProviderClass = TestDataProvider.class, description="Verifying that the landing page is the mymetro customer page. ", dependsOnMethods="loginUser")
	public void positionIsLandingPage(String landingPageVerificationArgument){
//		assertThat(pp.sh_login.getGreeting()).contains(landingPageVerificationArgument);
			
	}
			
}
