package de.metro.im.core.testframework.config.reporting.mail;

@SuppressWarnings("serial")
public class MailConfiguratorException extends Exception {

	public MailConfiguratorException() {
	}

	public MailConfiguratorException(String message) {
		super(message);
	}

	public MailConfiguratorException(Throwable cause) {
		super(cause);
	}

	public MailConfiguratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailConfiguratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
