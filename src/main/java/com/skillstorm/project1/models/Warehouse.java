package com.skillstorm.project1.models;

public class Warehouse {
	
	private int warehouseID;
	private String address;
	private double capacity;
	
	public Warehouse(int warehouseID, String address, double capacity) {
		super();
		this.warehouseID = warehouseID;
		this.address = address;
		this.capacity = capacity;
	}
	
	public Warehouse(int warehouseID, double capacity) {
		super();
		this.warehouseID = warehouseID;
		this.capacity = capacity;
	}
	
	public Warehouse(String address, double capacity) {
		super();
		this.address = address;
		this.capacity = capacity;
	}

	public int getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(int warehouseID) {
		this.warehouseID = warehouseID;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
	
	
	

}
