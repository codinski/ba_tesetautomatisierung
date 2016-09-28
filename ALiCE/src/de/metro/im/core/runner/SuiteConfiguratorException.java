package de.metro.im.core.runner;

@SuppressWarnings("serial")
public class SuiteConfiguratorException extends Exception {

	public SuiteConfiguratorException() {
	}

	public SuiteConfiguratorException(String message) {
		super(message);
	}

	public SuiteConfiguratorException(Throwable cause) {
		super(cause);
	}

	public SuiteConfiguratorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SuiteConfiguratorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
