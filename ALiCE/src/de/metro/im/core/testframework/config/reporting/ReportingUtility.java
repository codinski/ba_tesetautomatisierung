package de.metro.im.core.testframework.config.reporting;

import java.io.File;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.ITestContext;

import de.metro.im.core.applications.webdriver.DriverSession;
import de.metro.im.core.testframework.config.reporting.database.DBReporter;
import de.metro.im.core.testframework.config.reporting.mail.Mail;
import de.metro.im.core.testframework.config.reporting.mail.MailConfiguratorException;
import de.metro.im.core.testframework.config.reporting.mail.MailableReport;
import de.metro.im.core.util.Time;

public class ReportingUtility {

	private static Logger log = LogManager.getLogger();
	private static DBReporter db = new DBReporter();
	
	public static void reportToDB(ITestContext context, String comment, int logstatus){
		boolean doDBReporting;
		try {
			doDBReporting = new ReportUtilConfigurator("config/suites.properties").doDBReporting();
			if(doDBReporting){
				log.info("DB reporting set to: " + doDBReporting);
				db.reportToDatabase(context, comment, logstatus);
			}
			
		} catch (Exception e) {
			log.error("Problem during reporting results to database. " + e.getMessage());
		} 
	}
	
	public static void sendMailReportWithFailures(ITestContext context) {
		try { 
			String mailSubjectText = "AliCE Test Report: " + context.getCurrentXmlTest().getName() + " failed!";
			
			new Mail().sendHTMLReport(new MailableReport(mailSubjectText, context));
			
		} catch (MailConfiguratorException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
	

	public static String captureScreenshot(String screenshotName) {
		
		File screener = null;
		try {
			TakesScreenshot ts = (TakesScreenshot) DriverSession.getDriver();
			File source = ts.getScreenshotAs(OutputType.FILE);
			screener = new File(new ReportUtilConfigurator("config/suites.properties").getReportOutputPath() + "/screenshots/" + screenshotName.replaceAll(" ", "_") +  "_" + Time.getTime() + ".png");
			FileUtils.copyFile(source, screener);
		} catch (Exception e) {
			try {
				throw new ScreenshotException("Exception while taking screenshot: " + e.getMessage());
			} catch (ScreenshotException se) {
				log.error(se.getMessage());
			}
		}
		return screener.getPath();
	}

	public static void notifyIMCustomer(String subject, String message) {
				
		try {
			new Mail().notifyIMCustomer(subject, message);
		} catch (MessagingException | MailConfiguratorException e) {
			log.catching(e);
			e.printStackTrace();
		}
		
	}

	public static boolean notifyAdmin(String mailSubject, String mailBodyText) {
		try {
			new Mail().notifyAdmin(mailSubject, mailBodyText);
			return true;
		} catch (MessagingException | MailConfiguratorException e) {
			log.error("Wasn't able to notify admin. Error occured during sending message:" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}

	
}
