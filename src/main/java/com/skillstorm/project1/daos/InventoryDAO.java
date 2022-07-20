package com.skillstorm.project1.daos;

import java.util.List;

import com.skillstorm.project1.models.Inventory;

public interface InventoryDAO {

		/**
		 * 	Find all items in a given warehouse
		 * 	@param Warehouses id to search for
		 * 	@return List of items in a given warehouse
		 */
		public List<Inventory> findByWarehouse(int warehouseId);
		
		public Inventory findByVaultId(int vaultId);
		
		
		/**
		 * Find the current space used in a warehouse
		 * @param warehouseID
		 * @return The amount of used space
		 */
		public double findUsage(int warehouseId);
			
		/**
		 *  (Create) Insert data into database
		 *  @param warehouse object
		 *  @return Returns generated itemID if update successful, -1 if capacityCheck fails and 0 for any other error
		 */
		public int save(Inventory item); 
		
		/**
		 * 	Update an existing entry
		 * 	@param Inventory item to update
		 * 	@return true if success, false if error
		 */
		public boolean update(Inventory item); 
		
		/**
		 * 	Delete an entry
		 * 	@param int id of item to delete
		 * 	@return true if success, false if error
		 */
		public boolean delete(int id);
}







