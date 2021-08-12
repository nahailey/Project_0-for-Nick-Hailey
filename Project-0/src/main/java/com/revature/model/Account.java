package com.revature.model;

import java.util.Objects;

public class Account {

	private int id;
	private int client_id;
	private String accountnumber;
	private String acctType;
	private Double acctBalance;
	private String routingnumber;
	//private Client client; //what client this account actually belongs to 
	public Account() {
		super();
	}
	
	
	public Account(String acctType, Double acctBalance,String accountnumber, String routingnumber, int client_id) {
		this.accountnumber = accountnumber;
		this.acctBalance = acctBalance;
		this.acctType = acctType;
		this.routingnumber = routingnumber;
		this.client_id = client_id;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getClient_id() {
		return client_id;
	}


	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}


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


	@Override
	public int hashCode() {
		return Objects.hash(accountnumber, acctBalance, acctType, client_id, id, routingnumber);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountnumber, other.accountnumber) && Objects.equals(acctBalance, other.acctBalance)
				&& Objects.equals(acctType, other.acctType) && client_id == other.client_id && id == other.id
				&& Objects.equals(routingnumber, other.routingnumber);
	}


	@Override
	public String toString() {
		return "Account [id=" + id + ", client_id=" + client_id + ", accountnumber=" + accountnumber + ", acctType="
				+ acctType + ", acctBalance=" + acctBalance + ", routingnumber=" + routingnumber + "]";
	}

}
	