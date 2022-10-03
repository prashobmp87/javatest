package com.nagarro.javatest.service;

import java.util.List;

import com.nagarro.javatest.dto.AccountStatementDTO;
import com.nagarro.javatest.dto.StatementQueryParams;

public interface StatementService {

	
	public AccountStatementDTO getAccountAndStatementByQueryParams(StatementQueryParams statementQueryParams) throws Exception;
	public void printAllAccountAndStatementByQueryParams(StatementQueryParams statementQueryParams) throws Exception;
	
}
