package com.amdocs.model;

public class Mechanic {
	
	private int customerId;
	private String mechanicName;
	private String date;

	
	public Mechanic(String mechanicName) {
		this.mechanicName = mechanicName;
	}

	public Mechanic(int id, String mechanicName, String date) {
		this.customerId = customerId;
		this.mechanicName = mechanicName;
		this.date = date;
	}

	public int getcustomerId() {
		return customerId;
	}

	public void setcustomerId(int id) {
		this.customerId = id;
	}

	public String getmechanicName() {
		return mechanicName;
	}

	public void setmechanicName(String mechanicName) {
		this.mechanicName = mechanicName;
	}

	public String getdate() {
		return date;
	}

	public void setdate(String date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Mechanic [customerId=" + customerId + ", mechanicName=" + mechanicName + ", date=" + date + "]";
	}
	
}
