package com.revature.dao;//layer where are the implementation of Accounts will be 

import java.sql.SQLException;
import java.util.List;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;

public interface AccountDAO {
	

	List<Account> getAllAccountsFromClient(int clientId) throws SQLException;
	
	List<Account> accountBalanceRange(int clientId, int lessThan, int greaterThan) throws SQLException;
	
	public abstract Account addAccountForClient(AddOrEditAccountDTO account) throws SQLException;
	
	public abstract Account editAccount(double acctBalance) throws SQLException;
	
	public abstract void deleteAccount(int clientId, int accountId) throws SQLException;

	public abstract Account getAccountById(int clientId, int accountId) throws SQLException;

	

	

	
}
