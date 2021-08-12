package com.revature.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Client;

public class ClientServiceTest {

	private ClientService clientService;
	private ClientDAO clientDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.clientDao = mock(ClientDAO.class); // used mockito to make "fake/mock" object

		this.clientService = new ClientService(clientDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_getAllClients_positive() throws DatabaseException, SQLException {
		List<Client> mockValues = new ArrayList<>();
		mockValues.add(new Client(1, "Nicholas", "Hailey"));
		mockValues.add(new Client(2, "John", "Hailey"));
		when(clientDao.getAllClients()).thenReturn(mockValues);

		List<Client> actual = clientService.getAllClients();
		List<Client> expected = new ArrayList<>();

		expected.add(new Client(1, "Nicholas", "Hailey"));
		expected.add(new Client(2, "John", "Hailey"));
//		expected.add(new Client(3, "Alex", "Hailey"));
//		expected.add(new Client(4, "Mike", "Hailey"));
//		expected.add(new Client(5, "Bryan", "Hailey"));
//		expected.add(new Client(6, "Chris", "Hailey"));
//		expected.add(new Client(7, "Ivy", "Hailey"));

		assertEquals(expected, actual);
	}

	@Test
	public void test_getAllClients_negative() throws SQLException {
		when(clientDao.getAllClients()).thenThrow(SQLException.class);

		// may throw a database exception
		try {
			clientService.getAllClients();

			fail();
		} catch (DatabaseException e) {
			String exceptionmessage = e.getMessage();
			assertEquals("DAO Error", exceptionmessage);
		}
	}

	@Test
	public void test_getClientById_idStringNotAnInt() throws DatabaseException, ClientNotFoundException {
		try {
			clientService.getClientById("asdasdasd");

			fail();
		} catch (BadParameterException e) {
			assertEquals("asdasdasd was passed in by user, but it is not an int. Please pass in numbers only",
					e.getMessage());
		}
	}

	@Test
	public void test_getClientById_existingId()
			throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
		when(clientDao.getClientById(eq(1))).thenReturn(new Client(1, "Nicholas", "Hailey"));

		Client actual = clientService.getClientById("1");

		Client expected = new Client(1, "Nicholas", "Hailey");

		assertEquals(expected, actual);
	}

	@Test
	public void test_getClientById_clientNotFoundException()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		try {
			clientService.getClientById("20");
		} catch (ClientNotFoundException e) {
			assertEquals("Client with the id number 20 was not found", e.getMessage());
		}
	}

	@Test
	public void test_getClientById_sqlException() throws ClientNotFoundException, SQLException, BadParameterException {
		try {
			when(clientDao.getClientById(anyInt())).thenThrow(SQLException.class);

			clientService.getClientById("1");

			fail();
		} catch (DatabaseException e) {
			assertEquals("DAO Error", e.getMessage());
		}
	}

	@Test
	public void test_addClient_positiveath() throws DatabaseException, BadParameterException, SQLException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Nicholas");
		dto.setLast_name("Hailey");

		when(clientDao.addClient(eq(dto))).thenReturn(new Client(1, "Nicholas", "Hailey"));

		Client actual = clientService.addClient(dto);
		assertEquals(new Client(1, "Nicholas", "Hailey"), actual);
	}

	@Test(expected = BadParameterException.class)
	public void test_addClient_bothBlankname() throws DatabaseException, BadParameterException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("");
		dto.setLast_name("");

		clientService.addClient(dto);
	}

	@Test(expected = BadParameterException.class)
	public void test_addClient_blanknameWithSpaces() throws DatabaseException, BadParameterException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("                    ");
		dto.setLast_name("                     ");

		clientService.addClient(dto);
	}

	@Test(expected = BadParameterException.class)
	public void test_addClient_firstNameIsBlank() throws DatabaseException, BadParameterException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("");
		dto.setLast_name("Hailey");

		clientService.addClient(dto);
	}

	@Test(expected = BadParameterException.class)
	public void test_addClient_lastNameIsBlank() throws DatabaseException, BadParameterException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Nicholas");
		dto.setLast_name("");

		clientService.addClient(dto);
	}

	@Test(expected = DatabaseException.class)
	public void test_SQLExceptionEncountered() throws SQLException, DatabaseException, BadParameterException {
		when(clientDao.addClient(any())).thenThrow(SQLException.class);

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Nicholas");
		dto.setLast_name("Hailey");

		clientService.addClient(dto);
	}

	@Test
	public void test_editClient_positive()
			throws DatabaseException, ClientNotFoundException, BadParameterException, SQLException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Tom");
		dto.setLast_name("Hanks");

		Client clientWithId9 = new Client(9, "Tim", "Toddy");
		when(clientDao.getClientById(eq(9))).thenReturn(clientWithId9);

		when(clientDao.editClient(eq(9), eq(dto))).thenReturn(new Client(9, "Tom", "Hanks"));
		clientService.editClient("9", dto);

		Client actual = clientService.editClient("9", dto);

		Client expected = new Client(9, "Tom", "Hanks");
		assertEquals(expected, actual);
	}
	
	@Test
	public void test_editClient_clientDoesNotExists() throws DatabaseException, BadParameterException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Nicholas");
		dto.setLast_name("Hailey");
		
		try {
			clientService.editClient("50", dto);
			
			fail();
		} catch(ClientNotFoundException e ) {
			assertEquals("Client with the id 50 was not found", e.getMessage());
		}
	}
	
	@Test (expected = BadParameterException.class)
	public void test_editClient_invalidId() throws DatabaseException, ClientNotFoundException, BadParameterException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Nicholas");
		dto.setLast_name("Hailey");
		
		clientService.editClient("pop", dto);
	}
	
	@Test (expected = DatabaseException.class)
	public void test_editClient_SQLExceptionHit() throws SQLException, DatabaseException, BadParameterException, ClientNotFoundException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirst_name("Nicholas");
		dto.setLast_name("Hailey");
		
		when(clientDao.getClientById(eq(20))).thenReturn(new Client(20, "Timmy", "Turner"));
		when(clientDao.editClient(eq(20), eq(dto))).thenThrow(SQLException.class);
		
		clientService.editClient("20", dto);
	}
	
//	@Test
//	public void test_deleteClient_positive() throws DatabaseException, BadParameterException, ClientNotFoundException {
//		
//		clientService.deleteClient(any());
//	}
}

