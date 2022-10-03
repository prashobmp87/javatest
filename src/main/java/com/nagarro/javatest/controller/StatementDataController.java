package com.nagarro.javatest.controller;



import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.javatest.dto.AccountStatementDTO;
import com.nagarro.javatest.dto.ApiResponse;
import com.nagarro.javatest.dto.StatementQueryParams;
import com.nagarro.javatest.service.StatementService;
import com.nagarro.javatest.util.ApplicationException;
import com.nagarro.javatest.util.JavaTestConstants;
import com.nagarro.javatest.util.JavaTestUtility;

import lombok.extern.slf4j.Slf4j;




@RestController
@RequestMapping("/api")
@Slf4j
public class StatementDataController {


	@Autowired
	private StatementService statementService;
	
	@Value("${backstatement.days}")
	private int defaultBackStatementDays;
	
	@GetMapping("/v1/getStatementData/{accountId}/{fromDate}/{toDate}/{fromAmt}/{toAmt}")
    public ApiResponse<AccountStatementDTO> getStatementData(@PathVariable("accountId") long accountId,
    											@PathVariable("fromDate") String fromDate,@PathVariable("toDate") String toDate,
    											@PathVariable("fromAmt") String fromAmt,@PathVariable("toAmt") String toAmt) {
		log.info("Inside StatementDataController.getStatementData:");
		ApiResponse<AccountStatementDTO> apiResponse =null;
		try {
			StatementQueryParams queryParams = parseInputParams( accountId, fromDate, toDate, fromAmt, toAmt);
			log.info("queryParams"+queryParams);

			statementService.printAllAccountAndStatementByQueryParams(queryParams);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(queryParams);
			
			apiResponse = ApiResponse.<AccountStatementDTO> builder()
																.successStatus(true)
																.responseDescription("SUCCESS")
																.responseData(accountStatementDTO)
																.build();
		   
        } catch(ApplicationException e) {
        	apiResponse=	ApiResponse.<AccountStatementDTO> builder()
			.successStatus(false)
			.responseDescription(e.getUserDisplayMessage())
			.build();
        	
        }catch (Exception e) {
           log.error("Error inside StatementDataController.getStatementData {}",e.getMessage());
           apiResponse = ApiResponse.<AccountStatementDTO> builder()
			.successStatus(false)
			.responseDescription("Backend Error")
			.build();
        }
	    log.info("B4 leaving StatementDataController.getStatementData with result"+apiResponse);
        return apiResponse;
    }
	
	
	@GetMapping("/v1/getUserStatementData/{accountId}")
    public ApiResponse<AccountStatementDTO> getUserStatementData(@PathVariable("accountId") long accountId) {
		log.info("Inside StatementDataController.getUserStatementData:");
		ApiResponse<AccountStatementDTO> apiResponse =null;
		try {
			StatementQueryParams queryParams = getDefaultSearchParam( accountId);
			log.info("queryParams"+queryParams);

			statementService.printAllAccountAndStatementByQueryParams(queryParams);
			AccountStatementDTO accountStatementDTO = statementService.getAccountAndStatementByQueryParams(queryParams);
			
			apiResponse = ApiResponse.<AccountStatementDTO> builder()
																.successStatus(true)
																.responseDescription("SUCCESS")
																.responseData(accountStatementDTO)
																.build();
		   
        } catch(ApplicationException e) {
        	apiResponse=	ApiResponse.<AccountStatementDTO> builder()
			.successStatus(false)
			.responseDescription(e.getUserDisplayMessage())
			.build();
        	
        }catch (Exception e) {
           log.error("Error inside StatementDataController.getUserStatementData {}",e.getMessage());
           apiResponse = ApiResponse.<AccountStatementDTO> builder()
			.successStatus(false)
			.responseDescription("Backend Error")
			.build();
        }
	    log.info("B4 leaving StatementDataController.getUserStatementData with result"+apiResponse);
        return apiResponse;
    }
	
	
	
	private  StatementQueryParams parseInputParams(long accountId,String fromDate,String toDate,String fromAmt,String toAmt){
		log.info("Inside StatementDataController.parseInputParams");
		StatementQueryParams queryParams = null;
		try {
			if(accountId==0) {
				throw new Exception("Invalid accountId");
			}
			LocalDate dtFromDate=fromDate!=null && !JavaTestConstants.INVALID_VALUES.contains(fromDate)?JavaTestUtility.getDateFromString(fromDate,JavaTestConstants.DEFAULT_DATE_FORMAT):null;
			LocalDate dtToDate=fromDate!=null && !JavaTestConstants.INVALID_VALUES.contains(toDate)?JavaTestUtility.getDateFromString(toDate,JavaTestConstants.DEFAULT_DATE_FORMAT):null;
	
			BigDecimal fromAmtBD=fromAmt!=null && !JavaTestConstants.INVALID_VALUES.contains(fromAmt)?new BigDecimal(fromAmt):JavaTestConstants.ZERO_BIG_DECIMAL_VALUE;
			BigDecimal toAmtBD=fromAmt!=null && !JavaTestConstants.INVALID_VALUES.contains(toAmt)?new BigDecimal(toAmt):JavaTestConstants.ZERO_BIG_DECIMAL_VALUE;
	
			if(dtFromDate==null && dtToDate==null && fromAmtBD.compareTo(JavaTestConstants.ZERO_BIG_DECIMAL_VALUE)==0 && toAmtBD.compareTo(JavaTestConstants.ZERO_BIG_DECIMAL_VALUE)==0) {
				/**
				 * set 90 days Search
				 */
				return getDefaultSearchParam(accountId);
			}
			
			 queryParams=StatementQueryParams.builder()
					 .accountId(accountId)
					 .fromDate(dtFromDate)
					 .toDate(dtToDate)
					 .fromAmt(fromAmtBD)
					 .toAmt(toAmtBD)
					 .build();
			
		}catch(Exception e) {
			log.error("Error inside StatementDataController.parseInputParams");
			throw new ApplicationException(400, "Input Entered is not valid",e); 
		}
		log.info("Leaving StatementDataController.parseInputParams");
		return queryParams;
	}
	
	private  StatementQueryParams getDefaultSearchParam(long accountId) throws Exception {
		log.info("Inside StatementDataController.getDefaultSearchParam");
		LocalDate dtFromDate=LocalDate.now().minusDays(defaultBackStatementDays);
		if(accountId==0) {
			throw new ApplicationException(400, "Input Entered is not valid",null); 
		}
		return StatementQueryParams.builder()
				 .accountId(accountId)
				 .fromDate(dtFromDate)
				 .toDate(null)
				 .fromAmt(null)
				 .toAmt(null)
				 .build();
	}
}
