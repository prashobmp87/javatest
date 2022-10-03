package com.nagarro.javatest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JavaTestUtility {

	
	public static LocalDate getDateFromString(String date,String dateFormat) throws Exception{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		
		return LocalDate.parse(date, formatter);
	}
	
	public static String getMaskedValue(String value,int noOfCharsToShow) {
		int strLength = value.length();
		if (strLength < 4) {
			return value;
		}
	    return JavaTestConstants.ACCOUNT_NUMBER_MASK+value.substring( value.length() - 4,value.length());
	    
	}
}
