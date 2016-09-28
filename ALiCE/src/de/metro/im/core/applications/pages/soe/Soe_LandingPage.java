package de.metro.im.core.applications.pages.soe;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import de.metro.im.core.applications.pages.base.ABasePage;
import de.metro.im.core.applications.pages.soe.nav.MainNavigation;
import de.metro.im.core.applications.utils.DriverUtils;

public class Soe_LandingPage extends ABasePage {
	
	private WebElement lnkLogout;
	@FindBy(css="p.logout")
	private WebElement logoutText;
	private WebElement txtStoreNo;
	private WebElement txtCSN;
	@FindBy(className="itemDiscard")
	private WebElement itemDiscard;
	@FindBy(css="#div_gaGrid_txtCSN > div.slick-viewport > div.grid-canvas")
	private WebElement searchGrid;
	@FindBy(css="div.itemRow.openOrder")
	private WebElement firstOpenOrder;
	@FindAll({@FindBy(css="#div.itemRow.recentCustomer")})
	private WebElement customerFullItem;
	//TODO ob das funktioniert? ? ? 
	@FindAll({ @FindBy (className="ui-widget-content slick-row even active"), @FindBy(className="ui-widget-content slick-row even"), @FindBy(className="ui-widget-content slick-row odd")})
	List<WebElement> customerNamesFromSearchResult;
	@FindBy(css="div.itemCustomer")
	private WebElement firstCustomer;
	@FindBy(css="div.itemRow.openOrder") 
	private List<WebElement> openOrders;
	@FindBy(css="div.itemRow.closedOrder") 
	private List<WebElement> inProgressOrders;
	@FindBy(css="div.itemRow") 
	private List<WebElement> errorOrders;
	//TODO wie oben. sollte funktionieren. 
	@FindBy(css="div.itemRow.recentCustomer") 
	private List<WebElement> recentCustomers;
	private WebElement lnkSaveOrder;
	private WebElement divSearchBoxes;
	
	/*TAG NAMES USED FOR METHODS*/
	private String custNoStr = "data-csn";
	private String orderid = "data-orderid";
	
	public MainNavigation nav;
	
	
	public Soe_LandingPage(WebDriver _driver) {
		super(_driver);
		this.driver = _driver;
		nav = new MainNavigation(driver);
		
		PageFactory.initElements(this.driver, this);
	}
	
	public void typeStoreNo(String storeNo){
		txtStoreNo.click();
		txtStoreNo.clear();
		txtStoreNo.sendKeys(storeNo);	
	}
	
	public void typeCustomerNo(String custNo){
		txtStoreNo.sendKeys(Keys.TAB); //sometimes the form is inactive but can be accessed by switching to it using tabulator 
		if(!txtCSN.equals((driver.switchTo().activeElement()))){throw new StaleElementReferenceException(txtCSN.getClass().getName() + "can't be accessed");}
		txtCSN.clear();
		txtCSN.sendKeys(custNo);
	}
	
	/**
	 * Commits the customer search.
	 * @return Returns the order details page for this customer/order.
	 */
	public Soe_DeliveryDetailsPage submitCustomerSearch(){
		txtCSN.sendKeys(Keys.RETURN);
		return new Soe_DeliveryDetailsPage(driver);
	}

	@Override
	public Soe_LogoutPage logout() {
			lnkLogout.click();
		return new Soe_LogoutPage(driver);
	}
	
	public String logout2() {
		DriverUtils.clickAndRetry(By.id("lnkLogout"), driver);
		return logoutConfirmation();
	}
	
	private String logoutConfirmation(){
		return DriverUtils.waitForWebelementAndGet(logoutText, 5, driver).getText();
	}

	public Soe_LandingPage goTo() {
		lnkSaveOrder.click();
		return new Soe_LandingPage(driver);
	}
	
	public Soe_DeliveryDetailsPage enterMostRecentCustomer() {
		firstCustomer.click();
		return new Soe_DeliveryDetailsPage(driver);
	}
	public List<String> getCustomerList(){ //TODO
		List <String> names = Arrays.asList();
		for (WebElement line : customerNamesFromSearchResult){
			names.add(line.getText());
		}
		return names;

		
	}
	/**
	 * Enter the specified customer. 
	 * @param customerStoreNo The store you want to search the customer in.
	 * @param customerNo The customer information you want to search by.
	 * @return Commits the search and returns the according order details page. 
	 */
	public Soe_DeliveryDetailsPage enterCustomer(String customerStoreNo, String customerNo) {
		typeStoreNo(customerStoreNo);
		typeCustomerNo(customerNo);
		txtCSN.sendKeys(Keys.TAB);
		return new Soe_DeliveryDetailsPage(driver);

	}
	
	public int deleteAllOpenOrders(Integer maxOpenOrdersUntilDelete){
		int orderCount = getAllOpenOrders().size();
		
		if (orderCount > maxOpenOrdersUntilDelete){
				while (orderCount > 1) {
					deleteOpenOrder();
					orderCount--;
			
				}
		}

			
		return getAllOpenOrders().size();
	}
	
	/**
	 * Deletes the order matching the specified orderp-number
	 * @param orderNumber The Ordernumber you want to search for i.E. <b>SOE-6ff5ada70f736e</b><br>
	 * Please make sure to start with the orderp number prefix  <b>"SOE-" </b>.
	 * @return Returns the LandingPage object.
	 */
	public Soe_LandingPage deleteOpenOrder(String ordernumber){
		WebElement discardBtn;
		discardBtn = getOrder(ordernumber).findElement(By.className("itemDiscard"));
		discardBtn.click();
		DriverUtils.closeAlertAndGetMessage(true, driver);
		DriverUtils.refreshPage(driver);
		
		return this;

	}
	
	/**
	 * This method will delete the first open order in the open order list.
	 * @return
	 */
	public Soe_LandingPage deleteOpenOrder(){
		if(DriverUtils.isVisible(itemDiscard, driver)){
			itemDiscard.click();
			DriverUtils.closeAlertAndGetMessage(true, driver);
			DriverUtils.refreshPage(driver);
		}
		
		return new Soe_LandingPage(driver);

	}
	
	/**
	 * Enters the first open orderp in the open orders panel.
	 * @return Returns the Soe_OrderPage object. 
	 */
	public Soe_OrderPage enterMostRecentOrder(){
		firstOpenOrder.click();
		return new Soe_OrderPage(driver);

	}
	
	/**
	 * Creates a list of all open orders. You cann fully access all elements attributes.
	 * @return Return a list of all open orders represented by their webelements.
	 */
	public List<WebElement> getAllOpenOrders(){
		return openOrders;
	}
	
	public List<WebElement> getAllOrdersInProgress(){
		return inProgressOrders;
	}
	
	public List<WebElement> getAllOrdersInErrors(){
		return errorOrders;
	}
	
	
	/**
	 * Checks if a specific order exists. 
	 * @param ordernumber The ordernumber by it's ordernumber you want to look at. 
	 * @return Returns ture or false. 
	 */
	public boolean checkIfOrderExists(String ordernumber){
		DriverUtils.refreshPage(driver);
		int i = 0;
		for (WebElement order : openOrders){
			i++;
			if (order.getAttribute(orderid).equals(ordernumber)){
				System.out.println("Order EXISTS in the open order list at position " + "["+i+"].");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tries to access the order specified in the parameter. 
	 * @param ordernumber The order you want to access by its order number. 
	 * @return Returns the order item with all it's attributes. 
	 */
	public WebElement getOrder(String ordernumber){
		for (WebElement order : getAllOpenOrders()){
			if (order.getAttribute(orderid).equals(ordernumber)){
				System.out.println("There order you searched for was found.");
				return order;
			}
		}
		return null;
	}
		
	public List<WebElement> getAllRecentCustomers(){
		return recentCustomers;
	}
	
	/**
	 * Checks if a specific customer exists. 
	 * @param customerNumber The customerNumber you want to look for. 
	 * @return Returns ture or false. 
	 */
	public boolean checkIfCustomerExists(String customerNumber){
		for (WebElement customer : recentCustomers){
			if (customer.getAttribute(custNoStr).equals(customerNumber)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tries to access the customer specified in the parameter. 
	 * @param customerNumber The customer you want to access by his customernumber. 
	 * @return Returns the customer item with all it's attributes. 
	 */
	public WebElement getCustomer(String customerNumber){
		for (WebElement customer : getAllRecentCustomers()){
			if (customer.getAttribute(custNoStr).equals(customerNumber)){
				return customer;
			}
		}
		return null;
	}
	
	public Soe_DeliveryDetailsPage enterCustomerViaRecent(String customerNumber){
		getCustomer(customerNumber).click();
		return new Soe_DeliveryDetailsPage(driver);
	}

	@Override
	public String getCurrentUserInfo() {
		throw new NotImplementedException("This function is not supposed to be used here. You have to proceed to the ddelivery details page first.");
	}
	
	public String checkOrderStatus(String ordernumber){
		if (isOrderInProgress(ordernumber)){
			return "success";
		} else if (isOrderInError(ordernumber)){
			return "failure";
		} else {
			return "Could not find order at all. Please check the test case.";
		}
	}
	
	public boolean isOrderInProgress(String ordernumber){
		for (WebElement order : getAllOrdersInProgress()){
			if (order.getAttribute(orderid).equals(ordernumber)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isOrderInError(String ordernumber){
		for (WebElement order : getAllOrdersInErrors()){
			if (order.getAttribute(orderid).equals(ordernumber)){
				return true;
			}
		}
		return false;
	}

	public boolean isSearchBoxAvailable() {
		return DriverUtils.isVisible(divSearchBoxes, driver);
	}


}
