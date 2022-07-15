package com.skillstorm.project1.models;

public class Inventory {

	private int itemID;
	private double size;
	private int warehouse;
	
	public Inventory(double size, int warehouse) {
		super();
		this.size = size;
		this.warehouse = warehouse;
	}

	public Inventory(int itemID, double size, int warehouse) {
		super();
		this.itemID = itemID;
		this.size = size;
		this.warehouse = warehouse;
	}
	
	public Inventory(int itemID, double size) {
		super();
		this.itemID = itemID;
		this.size = size;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public int getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(int warehouse) {
		this.warehouse = warehouse;
	}

	@Override
	public String toString() {
		return "Inventory [itemID=" + itemID + ", size=" + size + ", warehouse=" + warehouse + "]";
	}
	
	
}
