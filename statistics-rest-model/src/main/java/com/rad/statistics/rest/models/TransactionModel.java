package com.rad.statistics.rest.models;


public class TransactionModel {

	double amount;
	
	long entryTime;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}
	
	
}
