package de.metro.im.core.applications.pages.base;

import org.openqa.selenium.WebDriver;

public abstract class ABasePage implements IPage {
	
	protected WebDriver driver;

	public ABasePage(WebDriver driver) {
		this.driver = driver;
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	public String checkPageTitle() {
		return driver.getTitle();
	}
	
	@Override
	public abstract String getCurrentUserInfo();
}
