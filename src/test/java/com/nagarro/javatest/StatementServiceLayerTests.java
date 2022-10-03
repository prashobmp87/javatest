package com.nagarro.javatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.nagarro.javatest.dto.AccountStatementDTO;
import com.nagarro.javatest.dto.StatementDTO;
import com.nagarro.javatest.dto.StatementQueryParams;
import com.nagarro.javatest.model.Account;
import com.nagarro.javatest.repository.AccountDAOImpl;
import com.nagarro.javatest.repository.StatementDAOImpl;
import com.nagarro.javatest.service.StatementServiceImpl;
import com.nagarro.javatest.util.JavaTestConstants;
import com.nagarro.javatest.util.JavaTestUtility;

@SpringBootTest(classes= {StatementServiceLayerTests.class})
public class StatementServiceLayerTests {

	
	@Mock
	AccountDAOImpl accountDAO;
	
	@Mock
	StatementDAOImpl statementDAO;
	
	@InjectMocks
	StatementServiceImpl statementService;
	
	
	Account account;
	
	List<StatementDTO> statements;
	
	@Test
	@Order(1)
	public void test_getAccountAndStatementByQueryParams_accountIdOnly() {
		
		try {
			//StatementQueryParams statementQueryParams = new StatementQueryParams();
			StatementQueryParams statementQueryParams = StatementQueryParams.builder().accountId(1).build();
			
			account=Account.builder().id(1)
									 .accountNumber("123456")
									 .accountType("current")
									 .build();
			
			statements =List.of(new StatementDTO(JavaTestUtility.getDateFromString("10.10.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("100.10")),
								new StatementDTO(JavaTestUtility.getDateFromString("10.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000"))
					
												);
			
			
			
			when(accountDAO.getAccount(statementQueryParams.getAccountId())).thenReturn(account);
			when(statementDAO.getStatementsAsDTO(statementQueryParams)).thenReturn(statements);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(statementQueryParams);
			System.out.println("accountStatementDTO"+accountStatementDTO);
			assertEquals(statements.size(), accountStatementDTO.getStatements().size());
			//Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			//statements = statementDAO.getStatementsAsDTO(statementQueryParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void test_getAccountAndStatementByQueryParams_accountIdAndFromDateOnly() {
		
		try {
			
			StatementQueryParams statementQueryParams =StatementQueryParams.builder().accountId(1)
										  .fromDate(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT))
										  .build();
			
			account=Account.builder().id(1)
									 .accountNumber("123456")
									 .accountType("current")
									 .build();
			
			statements =List.of(new StatementDTO(JavaTestUtility.getDateFromString("01.01.2021",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("100.10")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000.50"))
								
												);
			int expectedOutputSize=2;
			
			
			when(accountDAO.getAccount(statementQueryParams.getAccountId())).thenReturn(account);
			when(statementDAO.getStatementsAsDTO(statementQueryParams)).thenReturn(statements);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(statementQueryParams);
			System.out.println("test_getAccountAndStatementByQueryParams_accountIdAndFromDateOnly"+accountStatementDTO);
			System.out.println("input size"+statements.size());
			System.out.println("expected output size"+accountStatementDTO.getStatements().size());

			assertEquals(expectedOutputSize, accountStatementDTO.getStatements().size());
			//Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			//statements = statementDAO.getStatementsAsDTO(statementQueryParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	public void test_getAccountAndStatementByQueryParams_accountIdAndFromDateAndToDateOnly() {
		
		try {
			
			StatementQueryParams statementQueryParams =StatementQueryParams.builder().accountId(1)
										  .fromDate(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT))
										  .toDate(JavaTestUtility.getDateFromString("31.12.2022",JavaTestConstants.DEFAULT_DATE_FORMAT))
										  .build();
			
			account=Account.builder().id(1)
									 .accountNumber("123456")
									 .accountType("current")
									 .build();
			
			statements =List.of(new StatementDTO(JavaTestUtility.getDateFromString("01.01.2021",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("100.10")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.03.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.04.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000.50"))
								
												);
			int expectedOutputSize=4;
			
			
			when(accountDAO.getAccount(statementQueryParams.getAccountId())).thenReturn(account);
			when(statementDAO.getStatementsAsDTO(statementQueryParams)).thenReturn(statements);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(statementQueryParams);
			System.out.println("test_getAccountAndStatementByQueryParams_accountIdAndFromDateOnly"+accountStatementDTO);
			System.out.println("input size"+statements.size());
			System.out.println("expected output size"+accountStatementDTO.getStatements().size());

			assertEquals(expectedOutputSize, accountStatementDTO.getStatements().size());
			//Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			//statements = statementDAO.getStatementsAsDTO(statementQueryParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	@Order(4)
	public void test_getAccountAndStatementByQueryParams_accountIdAndFromAmtOnly() {
		
		try {
			
			StatementQueryParams statementQueryParams =StatementQueryParams.builder().accountId(1)
										  .fromAmt(new BigDecimal("500"))
										  .build();
			
			account=Account.builder().id(1)
									 .accountNumber("123456")
									 .accountType("current")
									 .build();
			
			statements =List.of(new StatementDTO(JavaTestUtility.getDateFromString("01.01.2021",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("100.10")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("50")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("500")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.03.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("150")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.04.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("200")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("10000"))
								
												);
			int expectedOutputSize=3;
			
			
			when(accountDAO.getAccount(statementQueryParams.getAccountId())).thenReturn(account);
			when(statementDAO.getStatementsAsDTO(statementQueryParams)).thenReturn(statements);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(statementQueryParams);
			System.out.println("test_getAccountAndStatementByQueryParams_accountIdAndFromDateOnly"+accountStatementDTO);
			System.out.println("input size"+statements.size());
			System.out.println("expected output size"+accountStatementDTO.getStatements().size());

			assertEquals(expectedOutputSize, accountStatementDTO.getStatements().size());
			//Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			//statements = statementDAO.getStatementsAsDTO(statementQueryParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	@Order(5)
	public void test_getAccountAndStatementByQueryParams_accountIdAndFromAmtAndToAmtOnly() {
		
		try {
			
			StatementQueryParams statementQueryParams =StatementQueryParams.builder().accountId(1)
										  .fromAmt(new BigDecimal("500"))
										  .toAmt(new BigDecimal("1500"))
										  .build();
			
			account=Account.builder().id(1)
									 .accountNumber("123456")
									 .accountType("current")
									 .build();
			
			statements =List.of(new StatementDTO(JavaTestUtility.getDateFromString("01.01.2021",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("100.10")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("50")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("500")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.03.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("150")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.04.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("200")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("10000"))
								
												);
			int expectedOutputSize=2;
			
			
			when(accountDAO.getAccount(statementQueryParams.getAccountId())).thenReturn(account);
			when(statementDAO.getStatementsAsDTO(statementQueryParams)).thenReturn(statements);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(statementQueryParams);
			System.out.println("test_getAccountAndStatementByQueryParams_accountIdAndFromDateOnly"+accountStatementDTO);
			System.out.println("input size"+statements.size());
			System.out.println("expected output size"+accountStatementDTO.getStatements().size());

			assertEquals(expectedOutputSize, accountStatementDTO.getStatements().size());
			//Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			//statements = statementDAO.getStatementsAsDTO(statementQueryParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	@Order(6)
	public void test_getAccountAndStatementByQueryParams_accountId_FromDate_ToDate_FromAmt_ToAmtOnly() {
		
		try {
			
			StatementQueryParams statementQueryParams =StatementQueryParams.builder().accountId(1)
					 					  .fromDate(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT))
					 					  .toDate(JavaTestUtility.getDateFromString("31.12.2022",JavaTestConstants.DEFAULT_DATE_FORMAT))
										  .fromAmt(new BigDecimal("500"))
										  .toAmt(new BigDecimal("1500"))
										  .build();
			
			account=Account.builder().id(1)
									 .accountNumber("123456")
									 .accountType("current")
									 .build();
			
			statements =List.of(new StatementDTO(JavaTestUtility.getDateFromString("01.01.2021",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("100.10")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("50")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("500")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.03.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("150")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.04.2022",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("200")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.01.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("1000")),
								new StatementDTO(JavaTestUtility.getDateFromString("01.02.2023",JavaTestConstants.DEFAULT_DATE_FORMAT), new BigDecimal("10000"))
								
												);
			int expectedOutputSize=1;
			
			
			when(accountDAO.getAccount(statementQueryParams.getAccountId())).thenReturn(account);
			when(statementDAO.getStatementsAsDTO(statementQueryParams)).thenReturn(statements);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(statementQueryParams);
			System.out.println("test_getAccountAndStatementByQueryParams_accountIdAndFromDateOnly"+accountStatementDTO);
			System.out.println("input size"+statements.size());
			System.out.println("expected output size"+accountStatementDTO.getStatements().size());

			assertEquals(expectedOutputSize, accountStatementDTO.getStatements().size());
			//Account account =accountDAO.getAccount(statementQueryParams.getAccountId());
			//statements = statementDAO.getStatementsAsDTO(statementQueryParams);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
