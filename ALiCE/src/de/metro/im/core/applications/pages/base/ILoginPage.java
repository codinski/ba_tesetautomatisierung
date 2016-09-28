package de.metro.im.core.applications.pages.base;


public interface ILoginPage {

	IPage loginAs(String username, String password);

	String getValidationErrorMessage();

	IPage loginSubmit();

	void typePassword(String password);

	void typeUsername(String username);

	String open(String url);

}