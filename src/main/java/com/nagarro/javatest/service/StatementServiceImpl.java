package com.nagarro.javatest.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.javatest.dto.AccountStatementDTO;
import com.nagarro.javatest.dto.StatementDTO;
import com.nagarro.javatest.dto.StatementQueryParams;
import com.nagarro.javatest.model.Account;
import com.nagarro.javatest.model.Statement;
import com.nagarro.javatest.repository.AccountDAO;
import com.nagarro.javatest.repository.StatementDAO;
import com.nagarro.javatest.util.ApplicationException;
import com.nagarro.javatest.util.JavaTestConstants;
import com.nagarro.javatest.util.JavaTestUtility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatementServiceImpl implements  StatementService{

	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private StatementDAO statementDAO;
	
	public AccountStatementDTO getAccountAndStatementByQueryParams(StatementQueryParams statementQueryParams) throws Exception{
		log.info("Inside StatementServiceImpl.getAccountAndStatementByQueryParams");
		AccountStatementDTO accountStatementDTO=null;
		try {
			Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			List<StatementDTO> statements = null;
			List<StatementDTO> filteredStatements  =null;
			if(account!=null && account.getId()>0) {
				statements = statementDAO.getStatementsAsDTO(statementQueryParams);
				filteredStatements = statements.stream().filter(statemnt->statementFilter(statementQueryParams,statemnt)).collect(Collectors.toList());
				Collections.sort(filteredStatements);
				accountStatementDTO=AccountStatementDTO.builder()
						.accountNumber(JavaTestUtility.getMaskedValue(account.getAccountNumber(), 4))
						.statements(filteredStatements)
						.build();
			}else {
				accountStatementDTO=AccountStatementDTO.builder()
						.accountNumber("No Data Found")
						.statements(filteredStatements)
						.build();
			}
			
		}catch(ApplicationException ae) {
			throw ae;
		}
		catch(Exception e) {
    		throw new ApplicationException(700, JavaTestConstants.DAO_LAYER_ERROR, e);
    	}
		log.info("B4 leaving StatementServiceImpl.getAccountAndStatementByQueryParams");
		return accountStatementDTO;
	}
	
	public void printAllAccountAndStatementByQueryParams(StatementQueryParams statementQueryParams) throws Exception{
		log.info("Inside StatementServiceImpl.printAllAccountAndStatementByQueryParams");
		List<Account> accounts =accountDAO.getAllAccount();
		List<Statement> statements = statementDAO.getAllStatements();
		log.info("accounts:"+accounts);
		log.info("statements:"+statements);
		log.info("B4 leaving StatementServiceImpl.printAllAccountAndStatementByQueryParams");
	}
 
	private boolean statementFilter(StatementQueryParams statementQueryParams,StatementDTO statement) {
		//log.info("Inside statementFilter");
		try {
			BigDecimal statementAmt =statement.getAmount();
			BigDecimal searchFromAmt = statementQueryParams.getFromAmt();
			BigDecimal searchToAmt = statementQueryParams.getToAmt();
	
			if(searchFromAmt!=null && searchFromAmt.compareTo(JavaTestConstants.ZERO_BIG_DECIMAL_VALUE)>0 &&(statementAmt.compareTo(searchFromAmt))<0){
				
				return false;
			}
			
			if(searchToAmt !=null && searchToAmt.compareTo(JavaTestConstants.ZERO_BIG_DECIMAL_VALUE)>0 &&(statementAmt.compareTo(searchToAmt))>0){
				
				return false;
			}
		
			LocalDate transactionDate= statement.getDateField();
			LocalDate searchFromDate= statementQueryParams.getFromDate();
			/*log.info("transactionDate"+transactionDate);
			log.info("searchFromDate"+searchFromDate);
			log.info("searchFromDate.compareTo(transactionDate)"+searchFromDate.compareTo(transactionDate));*/
			if(searchFromDate!=null && searchFromDate.compareTo(transactionDate)>0) {
				return false;
			}
			
			LocalDate searchToDate= statementQueryParams.getToDate();
			
			if(searchToDate!=null && transactionDate.compareTo(searchToDate)>0) {
				return false;
			}
		}
		catch(Exception e) {
    		throw new ApplicationException(600, JavaTestConstants.SERVICE_LAYER_ERROR, e);
    	}
		//log.info("Leaving statementFilter with true");
		return true;
	}
	
}
