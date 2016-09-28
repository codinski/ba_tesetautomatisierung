package de.metro.im.core.testframework.tests.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.Reporter;
import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.reporting.ReportingUtility;
import de.metro.im.core.testframework.config.testng.BaseTestConfig;
import de.metro.im.core.testframework.tests.TestDataProvider;


public class onlineordering_submitOrder_extension extends BaseTestConfig {

	public onlineordering_submitOrder_extension() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(dataProvider = "orderItem", dataProviderClass = TestDataProvider.class, description="Proceeds order through all stages and submits it. ")
	public void submitOrder(String orderItem){
		
		assertThat(
				pp.oo_newOrderPage.goTo()
					.addProductToCart(orderItem, 1)
					.cart()
					.finalizeOrder()
					.confimOrder()
					.confirmAllTerms()
					.submitOrder()
					.getSuccessMessage()).isNotEmpty();
		
		sendEmailWithSuccessConfirmation();
	}
	
	private void sendEmailWithSuccessConfirmation() {
		String orderNo = pp.oo_orderSuccess.getOrderNumber();
		Reporter.log("Order with order number " + orderNo + " was created! ");
		testlog.info("Order with order number " + orderNo + " was created! ");
//		ReportingUtility.notifyIMCustomer("AliCE Report: Daily order was created.", "Order Number: " + orderNo);
	}
	


	
}
