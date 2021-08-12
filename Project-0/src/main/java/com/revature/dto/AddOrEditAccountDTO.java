package com.revature.dto;

public class AddOrEditAccountDTO {

	private String accountnumber;
	private String acctType;
	private Double acctBalance;
	private String routingnumber;
	private int client_id;
	
	public String getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public Double getAcctBalance() {
		return acctBalance;
	}
	public void setAcctBalance(Double acctBalance) {
		this.acctBalance = acctBalance;
	}
	public String getRoutingnumber() {
		return routingnumber;
	}
	public void setRoutingnumber(String routingnumber) {
		this.routingnumber = routingnumber;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	
	
}
