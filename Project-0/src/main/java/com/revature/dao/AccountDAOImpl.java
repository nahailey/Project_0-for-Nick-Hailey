package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;
import com.revature.util.ConnectionUtility;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public List<Account> getAllAccountsFromClient(int clientId) throws SQLException {
			try (Connection con = ConnectionUtility.getConnection()) {
			List<Account> accounts = new ArrayList<>();
			
			String sql = "SELECT * FROM Project_0.account a WHERE a.client_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				String acctType = rs.getString("accttype");
				String accountnumber = rs.getString("accountnumber");
				Double acctBalance = rs.getDouble("acctBalance");
				String routingnumber = rs.getString("routingnumber");
				int client_id = rs.getInt("client_id");
				
				
				
				Account a = new Account(acctType, acctBalance, accountnumber, routingnumber, client_id);
				
				accounts.add(a);
			}
			
			return accounts;
		}
	}
	@Override
	public Account getAccountById(int clientId, int accountId) throws SQLException {
			try (Connection con = ConnectionUtility.getConnection()) {
			
			String sql = "SELECT * FROM Project_0.account a WHERE a.client_id = ? AND a.account_id = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, clientId);
			pstmt.setInt(2,accountId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				String acctType = rs.getString("accttype");
				String accountnumber = rs.getString("accountnumber");
				Double acctBalance = rs.getDouble("acctBalance");
				String routingnumber = rs.getString("routingnumber");
				int client_id = rs.getInt("client_id");
				
				
				
				Account a = new Account(acctType, acctBalance, accountnumber, routingnumber, client_id);
				
				return a;
			} else {
				return null;
			}
			
		}
	}

	@Override
	public Account addAccountForClient(AddOrEditAccountDTO account) throws SQLException {
		try(Connection con = ConnectionUtility.getConnection()) {
			String sql = "INSERT INTO Project_0.account (acctType, acctBalance, accountnumber, routingnumber, client_id) VALUES (?,?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, account.getAcctType());
			pstmt.setDouble(2, account.getAcctBalance());
			pstmt.setString(3, account.getAccountnumber());
			pstmt.setString(4, account.getRoutingnumber());
			pstmt.setInt(5, account.getClient_id());
			
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated !=1) {
				throw new SQLException("Could not update client account");
			}
			
			Account createdAccount = new Account(account.getAcctType(), account.getAcctBalance(), account.getAccountnumber(), account.getRoutingnumber(), account.getClient_id());
		
			return createdAccount;

		}
	}

	@Override
		public List<Account> accountBalanceRange (int clientId, int lessThan, int greaterThan) throws SQLException {
			
			try (Connection con = ConnectionUtility.getConnection()) {
				List<Account> accounts = new ArrayList<>();
				
				String sql = "SELECT * FROM Project_0.account a WHERE a.client_id = ? AND a.acctBalance <= ? and a.acctBalance >= ?";
				
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, clientId);
				pstmt.setInt(2, lessThan);
				pstmt.setInt(3, greaterThan);
				
				
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					String acctType = rs.getString("accttype");
					String accountnumber = rs.getString("accountnumber");
					Double acctBalance = rs.getDouble("acctBalance");
					String routingnumber = rs.getString("routingnumber");
					int client_id = rs.getInt("client_id");
					
					
					
					Account a = new Account(acctType, acctBalance, accountnumber, routingnumber, client_id);
					
					accounts.add(a);
				}
				
				return accounts;
			}
	}

	
	@Override
	public void deleteAccount(int clientId, int accountId) throws SQLException {
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "DELETE FROM jdbc_demo.account WHERE client_id = ? AND id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, clientId);
			pstmt.setInt(2, accountId);
			
			int recordsDeleted = pstmt.executeUpdate();
			
			if (recordsDeleted != 1) {
				throw new SQLException("Record was not able to be deleted");
			}
		}
	}
	
	@Override
	public Account editAccount(double acctBalance) throws SQLException {
		try(Connection con = ConnectionUtility.getConnection()){
			String sql = "UPDATE Project__0.account SET AcctBalance = ? WHERE a.client_id= ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setDouble(1, acctBalance);
			
			double recordsUpdated = pstmt.executeUpdate();
			if (recordsUpdated != 1) {
				throw new SQLException("Records could not be updated for balance");
			}
			
		}
		return new Account(null, acctBalance, null, null, 0);
	}
}
