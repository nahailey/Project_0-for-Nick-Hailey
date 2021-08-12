package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrEditClientDTO;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;


//POST /clients: Creates a new client--complete
//GET /clients: Gets all clients--complete
//GET /clients/{client_id}: Get client with an id of X (if the client exists)--complete
//PUT /clients/{client_id}: Update client with an id of X (if the client exists)--complete
//DELETE /clients/{client_id}: Delete client with an id of X (if the client exists)--complete
//POST /clients/{client_id}/accounts: Create a new account for a client with id of X (if client exists)
//GET /clients/{client_id}/accounts: Get all accounts for client with id of X (if client exists)
//GET /clients/{client_id}/accounts?amountLessThan=2000&amountGreaterThan=400: Get all accounts for client id of X with balances between 400 and 2000 (if client exists)
//GET /clients/{client_id}/accounts/{account_id}: Get account with id of Y belonging to client with id of X (if client and account exist AND if account belongs to client)
//PUT /clients/{client_id}/accounts/{account_id}: Update account with id of Y belonging to client with id of X (if client and account exist AND if account belongs to client)
//DELETE /clients/{client_id}/accounts/{account_id}: Delete account with id of Y belonging to client with id of X (if client and account exist AND if account belongs to client)
public class ClientController implements Controller {

	private ClientService clientService;

	public ClientController() {
		this.clientService = new ClientService();
	
	}
	
	private Handler getAllClients = (ctx) -> {
		List<Client> clients = clientService.getAllClients();
		
		ctx.json(clients);
	};
	private Handler getClientById = (ctx) -> {
	
		String clientid = ctx.pathParam("clientid");
		
		Client client = clientService.getClientById(clientid);
		ctx.json(client);
	};
	private Handler addClient = (ctx) -> {
		AddOrEditClientDTO clientToAdd = ctx.bodyAsClass(AddOrEditClientDTO.class);
		
		Client addedClient = clientService.addClient(clientToAdd);
		ctx.json(addedClient);
	};
	private Handler editClient = (ctx) -> {
		AddOrEditClientDTO clientToEdit = ctx.bodyAsClass(AddOrEditClientDTO.class);
		
		String clientId = ctx.pathParam("clientid");
		Client editedClient = clientService.editClient(clientId, clientToEdit);
		
		ctx.json(editedClient);
	};
	private Handler deleteClient = ctx -> {
		String clientId = ctx.pathParam("clientid");
		
		clientService.deleteClient(clientId);
		ctx.json("client deleted");
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/client", getAllClients);
		app.get("/client/:clientid", getClientById);
		app.post("/client", addClient);
		app.put("/client/:clientid", editClient);
		app.delete("/client/:clientid", deleteClient);
//	}
	
//	
//	private Handler getAllShips = (ctx) -> {		
//		List<Client> ships = clientService.getAllClients();
//		
//		ctx.status(200); // 200 means OK
//		ctx.json(ships);
//	};
//	
//	private Handler getShipById = (ctx) -> {
//		String shipid = ctx.pathParam("shipid");
//		
//		Client ship = clientService.getShipById(shipid);
//		ctx.json(ship);
//	};
//	
//	private Handler addShip = (ctx) -> {
//		AddOrEditShipDTO shipToAdd = ctx.bodyAsClass(AddOrEditShipDTO.class);
//		
//		Client addedShip = clientService.addShip(shipToAdd);
//		ctx.json(addedShip);
//	};
//	
//	private Handler editShip = (ctx) -> {
//		AddOrEditShipDTO shipToEdit = ctx.bodyAsClass(AddOrEditShipDTO.class);
//		
//		String shipId = ctx.pathParam("shipid");
//		Client editedShip = clientService.editShip(shipId, shipToEdit);
//		
//		ctx.json(editedShip);
//	};
//	
//	private Handler deleteShipById = (ctx) -> {
//		String shipId = ctx.pathParam("shipid");
//		
//		clientService.deleteShip(shipId);
//	};
	
	
	}
}
