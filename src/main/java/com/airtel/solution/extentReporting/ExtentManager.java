package com.airtel.solution.extentReporting;

import com.airtel.solution.util.DateTimeUtil;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter() {
		if (extent == null) {
			String workingDirectory = System.getProperty("user.dir");
			System.out.println("CurrentDirectory " + workingDirectory);
			extent = new ExtentReports(workingDirectory + "/test-output/ExtentReports/ExtentReportResults"
					+ DateTimeUtil.getFormattedDateTime("dd-MMM-yyyy_HH-mm-ss.SSS") + ".html", true);
		}

		return extent;
	}

	public static void main(String[] args) {
//		"file:///home/shrawan/repos/Personal/testFramework/test-output/mismatchData/mismatchData05-Jun-2020_23-07-01.420.txt"
		System.out.println("file://"+System.getProperty("user.dir")+"/test-output/mismatchData/mismatchData05-Jun-2020_23-07-01.420.txt");
	}
}
