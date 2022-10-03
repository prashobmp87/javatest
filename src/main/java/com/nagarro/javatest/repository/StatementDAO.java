package com.nagarro.javatest.repository;

import java.util.List;

import com.nagarro.javatest.dto.StatementDTO;
import com.nagarro.javatest.dto.StatementQueryParams;
import com.nagarro.javatest.model.Statement;


public interface StatementDAO {


	public List<Statement> getStatements(StatementQueryParams statementQueryParams) throws Exception;
	public void saveStatement(Statement statement) throws Exception;
    public List<Statement> getAllStatements() throws Exception;
    public List<StatementDTO> getStatementsAsDTO(StatementQueryParams statementQueryParams) throws Exception;

	
}
