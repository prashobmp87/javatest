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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StatementDTO implements Comparable<StatementDTO>{

	
	private LocalDate dateField;
	private BigDecimal amount;
	
	public int compareTo(StatementDTO statementDTO) {
		return this.getDateField().compareTo(statementDTO.getDateField());
			
	
	}
}
