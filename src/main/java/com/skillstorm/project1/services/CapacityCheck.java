package com.skillstorm.project1.services;

import com.skillstorm.project1.daos.SQLInventoryDAO;
import com.skillstorm.project1.daos.SQLWarehouseDAO;
import com.skillstorm.project1.models.Warehouse;

public class CapacityCheck {
	
	/**
	 * 
	 * @param warehouseId
	 * @param int adding, the amount of weight being added to the warehouse
	 * @return returns true of adding this weight is allowed and false if the warehouse does not have enough space for this
	 */
	public boolean checkCapacity(int warehouseId, double adding) {
		SQLWarehouseDAO whDao = new SQLWarehouseDAO();
		Warehouse wh = whDao.findById(warehouseId);
		double capacity = wh.getCapacity();
		
		SQLInventoryDAO invDao = new SQLInventoryDAO();
		double usage = invDao.findUsage(warehouseId);
		
		if((usage + adding) <= capacity) {
			return true;
		}
	
		return false;
	}
	
}
