/**
 * 
 */
package com.utility;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @author denis.putnam.ctr
 *
 */

public class TestReporter {

	private static Logger Log = Logger.getLogger(Log.class.getName()); // Default logger.
	private static String myStatus = "PASS";

	public static void setLogger(Logger log) {
		TestReporter.Log = log;
	}

	public static void fail(String error) {
		ITestResult test = Reporter.getCurrentTestResult();
		test.setStatus(ITestResult.FAILURE);
		TestReporter.myStatus = "FAIL";
		Reporter.log("FAIL!!!: " + error);
		Log.error("FAIL!!!: " + error);
	}

	public static void fail(String error, ExcelTestReporter excel, Integer step, String what) {
		ITestResult test = Reporter.getCurrentTestResult();
		test.setStatus(ITestResult.FAILURE);
		TestReporter.myStatus = "FAIL";
		Reporter.log("FAIL!!!: " + error);
		Log.error("FAIL!!!: " + error);
		excel.addRowData(new String[] {step.toString(), what, "FAIL:" + error});
	}
	
	// This is to print log for the beginning of the test case, as we usually run so
	// many test cases as a test suite

	public static void startTestCase() {
		ITestResult test = Reporter.getCurrentTestResult();
		String sTestCaseName = test.getInstanceName();
		test.setStatus(ITestResult.SUCCESS);
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("XXXXXXXXXXXXXXXXXXXXX             " + "-S---T---A---R---T-" + "       XXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("$$$$$$$$$$$$$$$$$$$$$        " + sTestCaseName +              "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}

	// This is to print log for the ending of the test case

	public static void endTestCase() {
		ITestResult test = Reporter.getCurrentTestResult();
		String sTestCaseName = test.getInstanceName();
		int status = test.getStatus();
		String sStatus = "";
		switch (status) {
		case ITestResult.FAILURE:
			sStatus = "FAIL";
			break;
		case ITestResult.SUCCESS:
			sStatus = "PASS";
			break;
		case ITestResult.CREATED:
			sStatus = "CREATED";
			break;
		case ITestResult.SKIP:
			sStatus = "SKIP";
			break;
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
			sStatus = "FAIL";
		default:
			break;
		}
		sStatus = TestReporter.myStatus;
		Log.info("****************************************************************************************");
		Log.info("$$$$$$$$$$$$$$$$$$$$$          " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("$$$$$$$$$$$$$$$$$$$$$          " + sStatus +       "       $$$$$$$$$$$$$$$$$$$$$$$$$");
		Log.info("XXXXXXXXXXXXXXXXXXXXX          " + "-E---N---D-" + "       XXXXXXXXXXXXXXXXXXXXXXXXX");
		Log.info("X");
		Log.info("X");
		Log.info("X");
		Log.info("X");
	}

	// Need to create these methods, so that they can be called

	public static void info(String message) {
		Reporter.log("Info: " + message);
		Log.info(message);
	}
	
	public static void step( String message, ExcelTestReporter excel, Integer step, String what) {
		String msg = "Info: Step=" + step + " What=" + what + " Message=" + message;
		Reporter.log(msg);
		Log.info(msg);
		excel.addRowData(new String[] {step.toString(), what, message});
	}

	public static void warn(String message) {
		Reporter.log("Warning: " + message);
		Log.warn(message);
	}

	public static void error(String message) {
		Reporter.log("Error: " + message);
		Log.error(message);
	}

	public static void fatal(String message) {
		Reporter.log("Fatal: " + message);
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}

}
