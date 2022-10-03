package com.nagarro.javatest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StatementQueryParams {

	private long accountId;
	private LocalDate fromDate;
	private LocalDate toDate;
	private BigDecimal fromAmt;
	private BigDecimal toAmt;
	
	
	
}
