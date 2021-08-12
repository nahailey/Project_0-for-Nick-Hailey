package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddOrEditClientDTO;
import com.revature.model.Client;
import com.revature.util.ConnectionUtility;

public class ClientDAOImpl implements ClientDAO {
	// 1. get a Connection object
	// 2. Obtain some sort of Statement object (Statement, PreparedStatement,
	// CallableStatement) Ex.Statement stmt = con.createStatement();
	// 3. Execute the query Ex.String sql = "SELECT * FROM Project_0.clients";
	// ResultSet rs = stmt.executeQuery(sql);
	// 4. Process the results (ResultSet)
	@Override
	public List<Client> getAllClients() throws SQLException {
		// 1: Construct a list for Clients
		List<Client> clients = new ArrayList<>();

		try (Connection con = ConnectionUtility.getConnection()) {

			Statement stmt = con.createStatement();

			// define our Query
			String sql = "SELECT * FROM Project_0.client";
			ResultSet rs = stmt.executeQuery(sql); // this returns a result set object

			while (rs.next()) {

				int id = rs.getInt("id");
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");

				Client client = new Client(id, firstname, lastname);

				clients.add(client);
			}
		}

		return clients;
	}

	@Override
	public Client getClientById(int id) throws SQLException {
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM Project_0.client WHERE id = ?"; // ? is a place holder for value
			PreparedStatement pstmt = con.prepareStatement(sql); // Method takes in a string

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int client_id = rs.getInt("id");
				String firstname = rs.getString("First_name");
				String lastname = rs.getString("Last_name");

				Client client = new Client(client_id, firstname, lastname);
				return (client);
			} else {
				return null; // this means there is no client
			}

		}

	}

	@Override
	public Client addClient(AddOrEditClientDTO client) throws SQLException {
		try (Connection con = ConnectionUtility.getConnection()) {

			String sql = "INSERT INTO Project_0.client (first_name, last_name) VALUES (?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, client.getFirst_name());
			pstmt.setString(2, client.getLast_name());

			int recordsUpdated = pstmt.executeUpdate();

			if (recordsUpdated != 1) {
				throw new SQLException("Could not insert a new client");
			}
			
			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {

				Client createdClient = new Client(generatedKeys.getInt(1), client.getFirst_name(), client.getLast_name());
				
				return createdClient;
			} else {
				throw new SQLException("id could not be auto generated for client");
			}

			
		}

	}

	@Override
	public Client editClient(int clientId, AddOrEditClientDTO client) throws SQLException {
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "UPDATE Project_0.client SET first_name = ?, last_name = ? WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, client.getFirst_name());
			pstmt.setString(2, client.getLast_name());
			pstmt.setInt(3, clientId);

			int recordsUpdated = pstmt.executeUpdate();
			if (recordsUpdated != 1) {
				throw new SQLException("Records could not be updated");
			}
			return new Client(clientId, client.getFirst_name(), client.getLast_name());
		}

	}

	@Override
	public void deleteClient(int clientId) throws SQLException {
		try (Connection con = ConnectionUtility.getConnection()) {
			con.setAutoCommit(false);// telling the database to not make the changes automatically that im about to implement 
			
			String sql = "DELETE FROM Project_0.account WHERE client_id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, clientId);
			int recordsDeleted = pstmt.executeUpdate();
			
			if (recordsDeleted < 1) { // no records were deleted if not 1
				throw new SQLException("Account seemed to not be able to be deleted.");
			}
			
			String sql2 = "DELETE FROM Project_0.client WHERE id = ?";
			PreparedStatement pstmt2 = con.prepareStatement(sql2);

			pstmt2.setInt(1, clientId);

			int recordsDeleted2 = pstmt2.executeUpdate();

			if (recordsDeleted2 != 1) { // no records were deleted if not 1
				throw new SQLException("Client seemed to not be able to be deleted.");
			}
			con.commit();
		}
	}
}
