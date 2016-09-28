package de.metro.im.core.testframework.tests;

import java.util.Arrays;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import de.metro.im.core.testframework.PropertyFileReader;

public class TestDataProvider {
	static PropertyFileReader pfr = new PropertyFileReader("config/testdata.properties");
	
	@DataProvider(name="orderItem")
	public static Object[][] orderItem(ITestContext context){
			return new Object[][]{
				{
					pfr.getProp("onlineordering_submitOrder_extension.orderItem"), 
				}
			};
	}
	//http://www.mkyong.com/unittest/testng-tutorial-6-parameterized-test/
	@DataProvider(name="user_pw")
	public static Object[][] loginInformation(ITestContext context){
			String appName = getSimpleTestClassNameFromContext(context);
			return new Object[][]{
				{
					pfr.getProp(appName + ".user"), 
					pfr.getProp(appName + ".password"),
				}
			};
	}
	
	@DataProvider(name="url_title")
	public static Object[][] applicationUrlAndTitle(ITestContext context){
		String appName = getSimpleTestClassNameFromContext(context);
		return new Object[][]{
				{
					pfr.getProp(appName + ".url"),
					pfr.getProp(appName + ".pagetitle")
				}
		};
	}
	
	
	@DataProvider(name="url")
	public static Object[][] applicationUrl(ITestContext context){
		String appName = getSimpleTestClassNameFromContext(context);
		return new Object[][]{
				{
					pfr.getProp(appName + ".url")
				}
		};
	}
	
	
	@DataProvider(name="user_pw_bkz")
	public static Object[][] loginInformationBkz(ITestContext context){
		String appName = getSimpleTestClassNameFromContext(context);
		return new Object[][]{
				{
					pfr.getProp(appName + ".user"), 
					pfr.getProp(appName + ".password"), 
					pfr.getProp(appName + ".bkz")
				}
		};
	}
	
	@DataProvider(name="landingPageVerificationArgument")
	public static Object[][] verificationArgument(ITestContext context){
		String appName = getSimpleTestClassNameFromContext(context);
		return new Object[][]{
				{
					pfr.getProp(appName + ".landingpage.verification.argument")
				}
		};
	}
		
	private static String getSimpleTestClassNameFromContext(ITestContext context){
		String fullClassName = context.getCurrentXmlTest().getClasses().get(0).getName();
		return (fullClassName.substring(fullClassName.lastIndexOf(".")+1));
	}

	public static List<String> getLoginInfo(ITestContext context) {
		String appName = getSimpleTestClassNameFromContext(context);
		List<String> info = Arrays.asList(pfr.getProp(appName + ".user"), pfr.getProp(appName + ".password"));
		return info;
	}

}
