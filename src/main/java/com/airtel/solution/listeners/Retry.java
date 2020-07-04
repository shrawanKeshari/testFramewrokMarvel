package com.airtel.solution.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.airtel.solution.extentReporting.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

public class Retry implements IRetryAnalyzer {

	private int count = 0;
	private static int maxTry = 0; // Run the failed test 2 times

	public boolean retry(ITestResult iTestResult) {
		if (!iTestResult.isSuccess()) { // Check if test not succeed
			if (count < maxTry) { // Check if maxtry count is reached
				count++; // Increase the maxTry count by 1
				iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed
				extendReportsFailOperations(iTestResult); // ExtentReports fail operations
				return true; // Tells TestNG to re-run the test
			}
		} else {
			iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
		}
		return false;
	}

	public void extendReportsFailOperations(ITestResult iTestResult) {
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
				getStackTrace(iTestResult.getThrowable().getStackTrace()));
	}

	public String getStackTrace(StackTraceElement[] stackTraceElements) {
		StringBuilder stackTrace = new StringBuilder();

		int length = stackTraceElements.length;

		for (int i = 0; i < length; i++) {
			stackTrace.append(stackTraceElements[i]).append("\n");
		}

		return stackTrace.toString();
	}

}
