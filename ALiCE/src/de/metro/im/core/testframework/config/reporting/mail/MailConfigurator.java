package de.metro.im.core.testframework.config.reporting.mail;

import de.metro.im.core.testframework.PropertyFileReader;

public class MailConfigurator extends PropertyFileReader {

	public MailConfigurator(String configFilePath) throws MailConfiguratorException {
		setConfig(configFilePath);
	}
	
	public String getSenderAddress(){
		return getProp("mail.sender");
	}
		
	public String getDomainRestriction(){
		return getProp("mail.domain.restriction");
	}
	
	public String getAdminAddress(){
		return getProp("mail.admin.address");
	}

	public String getImInbox() {
		return getProp("mail.iminbox");
	}

}
