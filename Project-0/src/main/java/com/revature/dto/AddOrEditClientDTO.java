package com.revature.dto;

public class AddOrEditClientDTO {

	private String firstName;
	private String lastName;
	
	public AddOrEditClientDTO() {
		super();
	}

	public String getFirst_name() {
		return firstName;
	}

	public void setFirst_name(String firstName) {
		this.firstName = firstName;
	}

	public String getLast_name() {
		return lastName;
	}

	public void setLast_name(String lastName) {
		this.lastName = lastName;
	}


}
