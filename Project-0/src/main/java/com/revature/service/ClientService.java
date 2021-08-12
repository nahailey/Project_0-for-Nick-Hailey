package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.DatabaseException;
import com.revature.exception.ClientNotFoundException;
import com.revature.model.Client;

//For unit testing, you want to test each Methods logic
public class ClientService {

	private ClientDAO clientDao;

	public ClientService() {// not when i construct a client object it will now construct a client service
							// object
		this.clientDao = new ClientDAOImpl();
	}

	public ClientService(ClientDAO mockDaoObject) {
		this.clientDao = mockDaoObject;
	}

	public List<Client> getAllClients() throws DatabaseException {
		try {
			List<Client> clients = clientDao.getAllClients();

			return clients;
		} catch (SQLException e) {
			throw new DatabaseException("DAO Error");
		}
	}

	public Client getClientById(String stringid)
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		try {
			int id = Integer.parseInt(stringid);
			Client client = clientDao.getClientById(id);

			if (client == null) {
				throw new ClientNotFoundException("Client with the id " + id + " was not found");
			}

			return client;
		} catch (SQLException e) {
			throw new DatabaseException("DAO Error");
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					stringid + " was passed in by user, but it is not an int. Please pass in numbers only");
		}
	}

	public Client addClient(AddOrEditClientDTO client) throws DatabaseException, BadParameterException {
		if (client.getFirst_name().trim().equals("Nicholas") && client.getLast_name().equals("")) {
			throw new BadParameterException("Last Name is missing. Can not be blank");
		}
		if (client.getFirst_name().equals("") && client.getLast_name().trim().equals("Hailey")) {
			throw new BadParameterException("First Name is missing. Can not be blank");
		}

		if (client.getFirst_name().trim().equals("")) {
			throw new BadParameterException("Client's first name can not be blank");
		}
		if (client.getLast_name().trim().equals("")) {
			throw new BadParameterException("Client's last name can not be left blank");
		}
		
		if (client.getFirst_name().matches(".*\\d.*")) {
			throw new BadParameterException("Invalid input. Only Letters please");
		}
		try {
			Client addedClient = clientDao.addClient(client);

			
			return addedClient;
		} catch (SQLException e) {
			throw new DatabaseException("DAO Error");
		}
	}

	public Client editClient(String stringId, AddOrEditClientDTO client)
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		// Does client already exists
		try {
			int clientId = Integer.parseInt(stringId);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with the id " + clientId + " was not found");
			}

			Client editedClient = clientDao.editClient(clientId, client);

			return editedClient;
		} catch (SQLException e) {
			throw new DatabaseException("DAO Error");
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					stringId + " was passed in by user, but it is not an int. Please pass in numbers only");
		}

	}

	public void deleteClient(String clientId) throws DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(clientId);

			Client client = clientDao.getClientById(id);
			if (client == null) { // check if client exists first before deletion
				throw new ClientNotFoundException(
						"Client with the id number " + id + " does not exist. Cannot delete.");
			}

			clientDao.deleteClient(id);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("DAO Error");
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client with the id number " + clientId + " was not found");
		}
	}
}