package com.revature.controller;

import java.util.List;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller {
	
	private AccountService accountService;
	
	public AccountController() {
		this.accountService = new AccountService();
	}

	private Handler getAccountFromClient = (ctx)-> {
		String clientId = ctx.pathParam("clientid");
		
		String lessThan = ctx.queryParam("amountLessThan");
		String greaterThan = ctx.queryParam("amountGreaterThan");
		
		List<Account> accountsFromClient = accountService.getAllAccountsFromClient(clientId, greaterThan, lessThan);
		
		ctx.json(accountsFromClient);
	};
	
	private Handler addAccountForClient = (ctx) -> {
		AddOrEditAccountDTO accountToAdd = ctx.bodyAsClass(AddOrEditAccountDTO.class);
		
		Account addedAccount = accountService.addAccountForClient(accountToAdd);
		ctx.json(addedAccount);
	};
	
//	private Handler accountBalanceRange = (ctx) -> {
//		String clientid = ctx.pathParam(":clientid");
//		String accountid = ctx.pathParam("accountid");
//		Account account = AccountService.accountBalanceRange(clientid, accountid);
//		ctx.json(account);
//	};
	
	private Handler deleteAccount = (ctx) -> {
		String accountId = ctx.pathParam("account_id");
		String clientId = ctx.pathParam("clientid");
		
		accountService.deleteAccount(clientId, accountId);
	};

	@Override
	public void mapEndpoints(Javalin app) {
	app.get("/client/:clientid/account", getAccountFromClient);
	app.post("/client/:clientid/account", addAccountForClient);
	app.delete("/client/: clientid/account/:account_id", deleteAccount);
	}
}
