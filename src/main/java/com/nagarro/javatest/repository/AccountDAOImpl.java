package com.nagarro.javatest.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.nagarro.javatest.model.Account;
import com.nagarro.javatest.model.Statement;
import com.nagarro.javatest.util.ApplicationException;
import com.nagarro.javatest.util.JavaTestConstants;

import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class AccountDAOImpl implements AccountDAO {

	
	private static String SQL="select * from account where id=?";
	private static String SQL_ALL="select * from account";


	@Autowired
	private JdbcTemplate jdbcTemplate;
	
private static final class AccountDataMapper implements RowMapper<Account>{
		
		public Account mapRow(ResultSet rs, int rowNum) throws SQLException{
			Account account =  new Account();
			
			account.setId(rs.getLong("id"));
			account.setAccountNumber(rs.getString("account_number"));
			account.setAccountType(rs.getString("account_type"));
			
			return account;

		}
	}
	
	public Account getAccount(long id) throws Exception{
		log.info("Inside AccountDAOImpl.getAccount");
		Account account =null;
    	try {
			 List<Account> accounts = (List)jdbcTemplate.query(SQL,new AccountDataMapper(),id);
			 if(accounts!=null && accounts.size()>0) {
				 account=accounts.get(0);
			 }
    	
		}catch(Exception e) {
			throw new ApplicationException(600, JavaTestConstants.DAO_LAYER_ERROR, e);
		}
		log.info("B4 leaving AccountDAOImpl.getAccount");
    	return account;
	}
	public void saveAccount(Statement statement) throws Exception{
		log.info("Inside AccountDAOImpl.saveAccount");

		log.info("B4 leaving AccountDAOImpl.saveAccount");

	}
    public List<Account> getAllAccount() throws Exception{
		log.info("Inside AccountDAOImpl.getAllAccount");
		List<Account> accounts = null;
		try {
			accounts = jdbcTemplate.query(SQL_ALL,new AccountDataMapper());
	    }catch(Exception e) {
			throw new ApplicationException(600, JavaTestConstants.DAO_LAYER_ERROR, e);
		}
    	log.info("B4 leaving StatementDAOImpl.getAllAccount");
    	return accounts;

    }

	
	
}
