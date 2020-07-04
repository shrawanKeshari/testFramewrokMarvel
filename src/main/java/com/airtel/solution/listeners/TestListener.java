package com.airtel.solution.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.airtel.solution.extentReporting.ExtentManager;
import com.airtel.solution.extentReporting.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;

public class TestListener implements ITestListener {
	
	public static int skipCount, passCount, failCount;
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public void onStart(ITestContext iTestContext) {
		System.out.println("I am in onStart method " + iTestContext.getName());
	}

	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());
		// Do tier down operations for extentreports reporting!
		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();
	}

	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
	}

	public void onTestSuccess(ITestResult iTestResult) {
		passCount++;
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
	}

	public void onTestFailure(ITestResult iTestResult) {
		failCount++;
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

		// Extentreports log and screenshot operations for failed tests.
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Failed", iTestResult.getThrowable().getMessage());
	}

	public void onTestSkipped(ITestResult iTestResult) {
		skipCount++;
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
		// Extentreports log operation for skipped tests.
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
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
