package com.nagarro.javatest.repository;

import java.util.List;

import com.nagarro.javatest.model.Account;
import com.nagarro.javatest.model.Statement;


public interface AccountDAO {


	public Account getAccount(long id) throws Exception;
	public void saveAccount(Statement statement) throws Exception;
    public List<Account> getAllAccount() throws Exception;

	
}
