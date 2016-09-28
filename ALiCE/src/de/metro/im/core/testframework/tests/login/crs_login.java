package de.metro.im.core.testframework.tests.login;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;

public class crs_login extends BaseTestConfig {
	
	public crs_login() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());			
	}
	
	@Test(dataProvider = "url_title", dataProviderClass = TestDataProvider.class, description="Calling the webpage url. ", priority=0)
	public void openWebApp(String url, String expectedPageTitle){
		assertThat(pp.crs_loginpg.open(url)).containsIgnoringCase(expectedPageTitle);
		
	}
	
	@Test(description="Checking if the user is at the login page.", dependsOnMethods="openWebApp")
	public void isLoginAppletAvailable(){
		assertThat(pp.crs_loginpg.isLoginFormAvailable()).isTrue();
	}
	
		
}
