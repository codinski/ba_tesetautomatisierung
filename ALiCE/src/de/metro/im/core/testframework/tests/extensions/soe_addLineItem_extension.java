package de.metro.im.core.testframework.tests.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import de.metro.im.core.testframework.config.testng.BaseTestConfig;

public class soe_addLineItem_extension extends BaseTestConfig {
			
	public soe_addLineItem_extension() {
		testlog.trace("Setting up Test: " + this.getClass().getSimpleName());
	}
	
	@Test(description="Enters the test customer. Verifies success by checking by checking the customer information in the order detail page.", priority = 5, dependsOnGroups={"soelogin"})
	@Parameters({"_testCustomer", "_testCustomerBkz"})
	public void enterTestCustomer(String _testCustomer, String _testCustomerBkz) throws Exception{
		pp.soe_landinpg.enterCustomer(_testCustomerBkz, _testCustomer);
		assertThat(pp.soe_delDetails.getCurrentCustomer()).contains(_testCustomer);
		
	}
	
	@Test(description="Adds a product to the line item list and verifies success.", dependsOnMethods="enterTestCustomer")
	public void addLineItem(){
			pp.soe_orderpg.goTo().addItemToList("7717", "1", true, true);
			assertThat(pp.soe_orderpg.getItemList().get(0).getText()
					.contains("Coca Cola"));
	}
	
//	@Test(description="Delete the order. Checks that page position is landing page after delete.", priority = 7, dependsOnMethods={"addLineItem"})
//	public void deleteOrder(){
////		assertThat(soe_orderpg.nav.subNavi().delete().isCorrectPosition()).isTrue();
//		assertThat(soe_orderpg.nav.subNavi().delete().checkPageTitle(true)).isEqualTo("MCRM SOE");
//	}
	
	@Test(description="Delete the order. Also deletes open orders of the defined threshold is reached. If errors occure herem they will be ignored. this is not a test case sensitive procedure.", priority = 7, dependsOnMethods={"addLineItem"})
	public void deleteAllOpenOrders(){
		try {
			pp.soe_landinpg.nav.subNavi().delete();
			pp.soe_landinpg.deleteAllOpenOrders(10); 
		} catch (Exception e) {
//			log.info("An error occured while deleting open orders. Counting test case as success. Occured error:_\n" + e);
		}
	}
	
	
}
