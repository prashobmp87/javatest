package com.nagarro.javatest.dto;

import java.util.List;

import com.nagarro.javatest.model.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AccountStatementDTO {

	
	private StatementQueryParams statementQueryParams;
	private String accountNumber;
	private List<StatementDTO> statements;
}
