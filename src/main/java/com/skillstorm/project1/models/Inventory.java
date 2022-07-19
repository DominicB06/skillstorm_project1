package com.skillstorm.project1.models;

public class Inventory {

	private int vaultID;
	private double size;
	private int warehouse;
	
	public Inventory() {
	}
	
	public Inventory(double size, int warehouse) {
		super();
		this.size = size;
		this.warehouse = warehouse;
	}

	public Inventory(int vaultID, double size, int warehouse) {
		super();
		this.vaultID = vaultID;
		this.size = size;
		this.warehouse = warehouse;
	}
	
	public Inventory(int vaultID, double size) {
		super();
		this.vaultID = vaultID;
		this.size = size;
	}

	public int getVaultID() {
		return vaultID;
	}

	public void setVaultID(int vaultID) {
		this.vaultID = vaultID;
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
		return "Inventory [vaultID=" + vaultID + ", size=" + size + ", warehouse=" + warehouse + "]";
	}
	
	
}
