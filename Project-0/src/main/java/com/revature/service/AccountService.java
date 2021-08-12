package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddOrEditAccountDTO;
import com.revature.exception.AccountNotFoundException;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Account;

public class AccountService {

	private AccountDAO accountDao;
	private ClientDAO clientDao;

	public AccountService() {
		this.accountDao = new AccountDAOImpl();
		this.clientDao = new ClientDAOImpl();
	}

	public List<Account> getAllAccountsFromClient(String clientIdString, String getterThan, String lessThan)
			throws BadParameterException, DatabaseException, ClientNotFoundException {
		// does this client exist

		try {
			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) { // this checks to see if there is a client available
				throw new ClientNotFoundException("Client with the id " + clientId + " was not found");
			}
			

			List<Account> accounts = accountDao.getAllAccountsFromClient(clientId);

			return accounts;

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client with the id " + clientIdString + " was not found");
		}

	}

	public Account addAccountForClient(AddOrEditAccountDTO account) throws DatabaseException {
		try {
			Account addedAccountForClient = accountDao.addAccountForClient(account);

			return addedAccountForClient;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("DAO Error");

		}

	}

	public void deleteAccount(String clientId, String accountId)
			throws DatabaseException, BadParameterException, ClientNotFoundException, AccountNotFoundException {
		try {
			int clientid = Integer.parseInt(clientId);
			int acctId = Integer.parseInt(accountId);

			// check client exist first
			if (clientDao.getClientById(clientid) == null) {
				throw new ClientNotFoundException("This client does not exist");
			}
			// check account exist first
			if (accountDao.getAccountById(clientid, acctId) == null) {
				throw new AccountNotFoundException("This account not exist");
			}
			accountDao.deleteAccount(clientid, acctId);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException("both client and account ID's are invalid");
		}
	}

//	public static Account accountBalanceRange(String clientId, String accountId) throws ClientNotFoundException {
//		try {
//			int acctId = Integer.parseInt(accountId);
//			int clientid = Integer.parseInt(clientId);
//
//			if (clientDao.getClientById(clientid) == null) {
//				throw new ClientNotFoundException(" Client with id " + clientId + " was not found");
//			}
//
//			Account accounts = accountDao.getSpecificAccountFromClient(accountId, clientId);
//
//			return accounts;
//		} catch (SQLException e) {
//			throw new DatabaseException(e.getMessage());
//		}
//	
//		return null;

}

