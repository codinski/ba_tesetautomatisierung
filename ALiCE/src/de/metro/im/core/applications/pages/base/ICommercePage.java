package de.metro.im.core.applications.pages.base;

public interface ICommercePage {

	public IPage searchCustomer(String _store, String _customerIformation);
	
	public ICommercePage searchItem(String _itemInformation, String _quanitity);
	
}
