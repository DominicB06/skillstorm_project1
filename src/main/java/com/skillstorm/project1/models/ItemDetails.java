package com.skillstorm.project1.models;

public class ItemDetails {

	private int serialNum;
	private String name;
	private int itemID;
	
	public ItemDetails(int serialNum, String name, int itemID) {
		super();
		this.serialNum = serialNum;
		this.name = name;
		this.itemID = itemID;
	}
	
	public ItemDetails(int serialNum, String name) {
		super();
		this.serialNum = serialNum;
		this.name = name;
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

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	
	
}
