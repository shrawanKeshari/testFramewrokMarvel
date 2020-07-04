package com.airtel.solution.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	public static String getFormattedDateTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(getFormattedDateTime("dd-MMM-yyyy_HH-mm-ss.SSS"));
	}
}
