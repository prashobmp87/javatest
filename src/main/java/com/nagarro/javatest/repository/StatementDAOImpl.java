package com.nagarro.javatest.repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.nagarro.javatest.dto.StatementDTO;
import com.nagarro.javatest.dto.StatementQueryParams;
import com.nagarro.javatest.model.Statement;
import com.nagarro.javatest.util.ApplicationException;
import com.nagarro.javatest.util.JavaTestConstants;
import com.nagarro.javatest.util.JavaTestUtility;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class StatementDAOImpl implements StatementDAO{

	private static String SQL="select * from statement where account_id=?";
	private static String SQL_ALL="select * from statement";

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    
	
	private static final class StatementDataMapper implements RowMapper<Statement>{
		public Statement mapRow(ResultSet rs, int rowNum) throws SQLException{
			Statement statement =  new Statement();
			statement.setId(rs.getLong("id"));
			statement.setAccountId(rs.getLong("account_id"));
			statement.setAmount(rs.getString("amount"));
			statement.setDateField(rs.getString("datefield"));	
			return statement;
		}
	}
	
	private static final class StatementDTODataMapper implements RowMapper<StatementDTO>{
		public StatementDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
			StatementDTO statement =  new StatementDTO();
			statement.setAmount(StringUtils.hasText(rs.getString("amount"))?new BigDecimal(rs.getString("amount")):new BigDecimal(rs.getString("0")));
			try {
				statement.setDateField(StringUtils.hasText(rs.getString("datefield"))?JavaTestUtility.getDateFromString(rs.getString("datefield"), JavaTestConstants.DEFAULT_DATE_FORMAT):null);	
			}catch(Exception e) {
				log.error("Error Inside StatementDTODataMapper, date not in exepcted format, continuing..");
			}
			return statement;
		}
	}
	
    
    public void saveStatement(Statement staement)  throws Exception{
    	jdbcTemplate.queryForObject("test",String.class);
    }

    public List<Statement> getStatements(StatementQueryParams statementQueryParams)  throws Exception{
    	log.info("Inside StatementDAOImpl.getStatements");
    	List<Statement> statements = null;
    	try {
    		statements = jdbcTemplate.query(SQL,new StatementDataMapper(),statementQueryParams.getAccountId());
	    }catch(Exception e) {
			throw new ApplicationException(600, JavaTestConstants.DAO_LAYER_ERROR, e);
		}
    	log.info("B4 leaving StatementDAOImpl.getStatements");
    	return statements;
    }
    
    public List<StatementDTO> getStatementsAsDTO(StatementQueryParams statementQueryParams)  throws Exception{
    	log.info("Inside StatementDAOImpl.getStatements");
    	List<StatementDTO> statements =null;
    	try {
    		statements = jdbcTemplate.query(SQL,new StatementDTODataMapper(),statementQueryParams.getAccountId());
    	}catch(Exception e) {
    		throw new ApplicationException(600, JavaTestConstants.DAO_LAYER_ERROR, e);
    	}
    	log.info("B4 leaving StatementDAOImpl.getStatements");
    	return statements;
    }
    
    
    public List<Statement> getAllStatements()  throws Exception{
    	log.info("Inside StatementDAOImpl.getAllStatements");
    	List<Statement> statements =null;
    	try {
    		statements = jdbcTemplate.query(SQL_ALL,new StatementDataMapper());
    	}catch(Exception e) {
    		throw new ApplicationException(600, JavaTestConstants.DAO_LAYER_ERROR, e);
    	}
    	log.info("B4 leaving StatementDAOImpl.getAllStatements");
    	return statements;
    }

}
