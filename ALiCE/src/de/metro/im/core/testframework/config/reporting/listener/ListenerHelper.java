package de.metro.im.core.testframework.config.reporting.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestResult;

public abstract class ListenerHelper {

	/**
	 * Cheks if the thrown exception of the passed ITestResult contains a specific reason. 
	 * @param result ITestResult of the test you want to check. 
	 * @param exception the exception reason as string.  
	 * @return True if the (sub)string was found in the string representation of the throwable.
	 */
	public static boolean isFailReason(ITestResult result, String exception) {
		try {
			if (result.getThrowable().toString().contains(exception))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	//TODO aus isFailReason prädikat machen
	
	public static Map<String, String> getDriverErrorsFromResults(ITestResult result) {
		Map<String, String> programErrors = new HashMap<String, String>();
		
		if (isFailReason(result, "UnreachableBrowserException")
				|| isFailReason(result, "NoSuchWindowException")
				|| isFailReason(result, "NullPointerException")
				|| isFailReason(result, "WebDriverException")) {
	
			String failMethodNameStr = getCurrentMethodName(result);
			String failErrReasonStr = getCurrentFailureMessage(result);
			String failSuitNameStr = result.getTestContext().getCurrentXmlTest().getSuite().getName();
	
			programErrors.put("( " + failSuitNameStr + " ) " + failMethodNameStr, failErrReasonStr);
		}	
		return programErrors;
	}

	public static Map<String, String> getFailedMethodReasonFromContext(ITestContext context){
		Map<String, String> failedMethodReason = new HashMap<String, String>();
		Set<ITestResult> failedTests = getAllFailedTestsResults(context);
		
		for (ITestResult result : failedTests){
			
				if (result.getParameters().toString().contains("NullPointer")) {
					System.out.println("Nullpointer gefunden. Testresultat wird gelöscht.");
					context.getFailedTests().removeResult(result);
				}
			
				String methodNameStr = getCurrentMethodName(result);			
				String errReasonStr = getCurrentFailureMessage(result);
				
				failedMethodReason.put(methodNameStr, errReasonStr);
		}
		return failedMethodReason;
	}
	
	public static Map<String, String> getAllFailedMethodsAndDescriptionFromContext(ITestContext context){
		Map<String, String> failedMethodAndReason = new HashMap<String, String>();
		Map<String, String> failedMethodDescription = new HashMap<String, String>();
		Set<ITestResult> failedTests = getAllFailedTestsResults(context);
		
		for (ITestResult result : failedTests){
			
//				if (result.getThrowable().toString().contains("NullPointer")) {
//					System.out.println("Nullpointer gefunden. Testresultat wird gelöscht.");
//					context.getFailedTests().removeResult(result);
//				}
			
				String methodNameStr = getCurrentMethodName(result);			
				String errReasonStr = getCurrentFailureMessage(result);
				
				failedMethodAndReason.put(methodNameStr, errReasonStr);
				
				String methodDescrStr = getCurrentMethodDescription(result);
//				int testPriority = getCurrentMethodPriority(result);
				
//				failedMethodDescription.put("("+ testPriority + ") " + methodNameStr, methodDescrStr);	
				failedMethodDescription.put(methodNameStr, methodDescrStr);	
		}
		return failedMethodDescription;
	}
	
	public static String getCurrentMethodDescription(ITestResult result) {
		return result.getMethod().getDescription();
	}
	
	public static int getCurrentMethodPriority(ITestResult result) {
		return result.getMethod().getPriority();
	}
	
	public static Set<ITestResult> getAllFailedTestsResults(ITestContext context) {
		return context.getFailedTests().getAllResults();
	}
	
	public static Map<String, String> getSkippsFromContext(ITestContext context){
		Set<ITestResult> skippedTests = context.getSkippedTests().getAllResults();
		Map<String, String> skippedMethodsAndDescriptions = new HashMap<String, String>();
		
		for (ITestResult skippedresult : skippedTests){
			
			String skippedMethNameStr = getCurrentMethodName(skippedresult);
			String skippedMethodDescrStr = getCurrentMethodDescription(skippedresult);
//			int skippedTestPriority = getCurrentMethodPriority(skippedresult);
			
//			skippedMethodsAndDescriptions.put("("+ skippedTestPriority + ") " + skippedMethNameStr, skippedMethodDescrStr);
			skippedMethodsAndDescriptions.put(skippedMethNameStr, skippedMethodDescrStr);
		}
		return skippedMethodsAndDescriptions;
	}
	
	public static Map<String, String> getSuccessesFromContext(ITestContext context){
		//create mailable collection with successful de.metro.im.core.tests
		Map<String, String> passedMethodsAndDescriptions = new HashMap<String, String>();
		Set<ITestResult> contextSuccessfullTests = getAllPassedTestsResults(context);
		
		for (ITestResult successfullTest : contextSuccessfullTests){
			
			String successMethodNameStr = getCurrentMethodName(successfullTest);
			String successMethodDescrStr = getCurrentMethodDescription(successfullTest); 
//			int successTestPriority = getCurrentMethodPriority(successfullTest);
			
//			passedMethodsAndDescriptions.put("("+ successTestPriority + ") " + successMethodNameStr, successMethodDescrStr);
			passedMethodsAndDescriptions.put(successMethodNameStr, successMethodDescrStr);
	
		}
		return passedMethodsAndDescriptions;
	}
	
	public static Set<ITestResult> getAllPassedTestsResults(ITestContext context) {
		return context.getPassedTests().getAllResults();
	}

		public static String getCurrentMethodName(ITestResult result) {
			return result.getMethod().getMethodName();
		}
		
		public static String getCurrentTestName(ITestResult result) {
			return result.getTestContext().getCurrentXmlTest().getName();
		}
		
		public static String getCurrentTestName(ITestContext context) {
			return context.getCurrentXmlTest().getName();
		}

		public static String getCurrentFailureMessage(ITestResult result) {
			return result.getThrowable().getMessage();
		}
		
		public static boolean hasFailedTests(ITestContext context) {
			return context.getFailedTests().size() != 0;
		}
		
}
