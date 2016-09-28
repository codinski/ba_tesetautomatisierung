package de.metro.im.core.applications.pages.soe.nav;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.soe.Soe_DeliveryDetailsPage;
import de.metro.im.core.applications.pages.soe.Soe_LandingPage;
import de.metro.im.core.applications.pages.soe.Soe_OrderPage;
import de.metro.im.core.applications.utils.DriverUtils;

/**
 * Diese Klasse repräsentiert die Settings-Leiste. 
 * @author christoph.zabinski
 *
 */

public class MainNavigation {

	private static WebDriver driver;

	@FindBy(linkText="Line Items")
	private static WebElement orders;
	@FindBy(linkText="Order Header")
	private static WebElement details;
	@FindBy(linkText="Catalogs")
	private WebElement catalog;
	@FindBy(linkText="Recent Orders")
	private WebElement recentOrder;
	@FindBy(linkText="Submit Order")
	private WebElement orderOverview;
	
	
	public MainNavigation(WebDriver driver) {
		MainNavigation.driver = driver;
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 20);
		PageFactory.initElements(driver, this);
	}
	
	public SettingsBar settings(){
		return new SettingsBar(driver);
	}
	
	public SubNavigation subNavi(){
		return new SubNavigation();	
	}

	public static Soe_DeliveryDetailsPage detailsPage(){
		details.click();
		return new Soe_DeliveryDetailsPage(driver);
	}
	
	public static Soe_OrderPage orderPage(){
		orders.click();
		return new Soe_OrderPage(driver);
	}
	
//	public CatalogPage catalogPage(){
//		catalog.click();
//		return new CatalogPage(driver, pageBaseUrl);
//	}
	
//	public RecentOrdersPage recentOrdersPage(){
//		recentOrder.click();
//		return new RecentOrdersPage(driver, pageBaseUrl);
//	}
	
//	public OrderOverviewPage orderOverviewPage(){
//		orderOverview.click();
//		return new OrderOverviewPage(driver, pageBaseUrl);
//	}
	
	/*INNER CLASS representing the sub navigation menu available on every page that also has as main nav menu*/
	public class SubNavigation extends MainNavigation{
				
		private WebElement lnkDeleteOrder;
		private WebElement lnkNewOrder;
		private WebElement lnkSaveOrder;
		@SuppressWarnings("unused")
		private WebElement lnkSubmitOrderToMcrm;
		private WebElement lnkEditOrderInMcrm;
		@SuppressWarnings("unused")
		private boolean acceptNextAlert = true;
		private WebElement divOpenOrders;
		
		
		public SubNavigation() {
			super(driver);
		}
					
		public Soe_LandingPage delete(){
			lnkDeleteOrder.click();
			DriverUtils.closeAlertAndGetMessage(true, driver);
			DriverUtils.waitForWebelementAndGet(divOpenOrders, 10, driver); //sometimes waiting for the order to be deleted takes some time. by using a fluent wait for the open ordeers tab we can delay the test-procedure
			return new Soe_LandingPage(driver);
		}
		
		public Soe_LandingPage newORder(){
			lnkNewOrder.click();
			return new Soe_LandingPage(driver);
		}
		
		public Soe_LandingPage saveForLater(){
			lnkSaveOrder.click();
			return new Soe_LandingPage(driver);
		}
		
		public Soe_LandingPage submitEditinMCRM(){
			lnkEditOrderInMcrm.click();
			return new Soe_LandingPage(driver);
		}
		
	}
	
	
}
