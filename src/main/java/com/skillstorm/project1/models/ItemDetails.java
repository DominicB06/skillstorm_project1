package com.skillstorm.project1.models;

public class ItemDetails {

	private int serialNum;
	private String name;
	private int vaultID;

	public ItemDetails() {
		
	}
	
	public ItemDetails(int serialNum, String name, int vaultID) {
		super();
		this.serialNum = serialNum;
		this.name = name;
		this.vaultID = vaultID;
	}

	public int getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVaultID() {
		return vaultID;
	}

	public void setVaultID(int vaultID) {
		this.vaultID = vaultID;
	}

	@Override
	public String toString() {
		return "ItemDetails [serialNum=" + serialNum + ", name=" + name + ", vaultID=" + vaultID + "]";
	}
	
	
	
}
