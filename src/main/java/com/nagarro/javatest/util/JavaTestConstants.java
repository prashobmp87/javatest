package com.nagarro.javatest.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class JavaTestConstants {

	public static final String DEFAULT_DATE_FORMAT="dd.MM.yyyy";
	public static final BigDecimal ZERO_BIG_DECIMAL_VALUE=new BigDecimal("0");
	public static final List<String> INVALID_VALUES=Arrays.asList("","NA","-");
	public static final String ACCOUNT_NUMBER_MASK="********";
	public static final String DAO_LAYER_ERROR="Error in DAO";
	public static final String SERVICE_LAYER_ERROR="Error in Service";


}
