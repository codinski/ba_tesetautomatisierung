package de.metro.im.core.testframework.config.reporting.mail;

import java.util.Map;

import org.testng.ITestContext;

import de.metro.im.core.testframework.config.reporting.listener.ListenerHelper;

public class MailableReport {
	private ITestContext context;
	private String mailSubject;
	
	public MailableReport(String mailSubject, ITestContext context) {
		this.context = context;
		this.mailSubject = mailSubject;
	}
	
//	private void setTestContext(ITestContext context){
//		this.context = context;
//	}
	
	String getSuitName(){
		return context.getCurrentXmlTest().getSuite().getName();
	}
	
	String getTestName(){
		return context.getCurrentXmlTest().getName();
	}
	
	Map<String, String> getFailedMethodsAndReasons(){
		return ListenerHelper.getFailedMethodReasonFromContext(context);
	}
	
//	private Map<String, String> getFailedMethodAndDescription(){
//		return ListenerHelper.getAllFailedMethodsAndDescriptionFromContext(context);
//	}
	
	Map<String, String> getSkippedMethodsAndDescriptions(){
		return ListenerHelper.getSkippsFromContext(context);
	}
	
	Map<String, String> getSuccessfullMethodsAndDescriptions(){
		return ListenerHelper.getSuccessesFromContext(context);
	}
	
	public String getHtmlBody(){
		String htmlMethodsAndDescription = "";
//		String htmlMethodsAndThrows = "";
		String htmlSuccessfullMethodsAndDescription = "";
		String htmlSkippedMethidsAndDescription = "";
		
		for (Map.Entry<String, String> entry : getSuccessfullMethodsAndDescriptions().entrySet()){
			htmlSuccessfullMethodsAndDescription += "<p><li><strong>" + entry.getKey() + "</strong>" + ": " + entry.getValue() + "</p>" +"\n";
		}
		
		for (Map.Entry<String, String> entry : getFailedMethodsAndReasons().entrySet()){
			htmlMethodsAndDescription += "<p><li><strong>" + entry.getKey() + "</strong>" + ": " + entry.getValue() + "</p>" +"\n";
		}
		
//		for (Map.Entry<String, String> entry : getFailedMethodsAndReasons().entrySet()){
//			htmlMethodsAndThrows += "<p><li><strong>" + entry.getKey() + "</strong>" + ": " + entry.getValue() + "</p>" + "\n";
//				if(entry.getValue().contains("password")){
//					this.mailSubject = this.mailSubject + " (user/password error)";
//			}
//		}
		
		for (Map.Entry<String, String> entry : getSkippedMethodsAndDescriptions().entrySet()){
			htmlSkippedMethidsAndDescription += "<p><li><strong>" + entry.getKey() + "</strong>" + ": " + entry.getValue() + "</p>" + "\n";
		}
		
		String htmlBodyMessage = ""
		+"<h1><strong>Test Suite :::: " +  getSuitName() + " ::::</strong></h1>"+
		
		"<p><strong>Failed Application Name(s):</strong></p>"+
		"<p>" + getTestName() + "</p>"+
		
		"<p><br /><strong><font color=\"red\"><h2>Failed STEP(s) / Condition:</font></h2></strong></p><ul>"+
		"<p>" + htmlMethodsAndDescription + "</p></ul>"+
		
//		"<p><br /><strong>Thrown Error(s):</strong></p><ul>"+
//		"<p>" + htmlMethodsAndThrows + "</p></ul>"+

		"<p><br /><strong><font color=\"orange\">Skipped STEP(s) / Description:</font></strong></p><ul>"+
		"<p>" + htmlSkippedMethidsAndDescription + "</p></ul>" +
		
		
		"<p><br /><strong><font color=\"green\">SUCCESSFUL STEP(s) / Description:</font></strong></p><ul>"+
		"<p>" + htmlSuccessfullMethodsAndDescription + "</p></ul>";

		
		String htmlMainMailTitleAndImage = "<span style=\"font-family: arial;\"><img src=\"cid:image\"><h2>AliCE ALIVE CHECK REPORT</h2></span>";
		
		return htmlMainMailTitleAndImage + htmlBodyMessage;
		
	}

	public String getMailSubject() {
		return mailSubject;
	}

}
