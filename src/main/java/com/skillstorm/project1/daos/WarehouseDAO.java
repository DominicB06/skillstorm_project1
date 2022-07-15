package com.skillstorm.project1.daos;

import java.util.List;

import com.skillstorm.project1.models.Warehouse;

public interface WarehouseDAO {

		/**
		 * Retrieve every entry in warehouses
		 * @return List of warehouses
		 */
		public List<Warehouse> findAll();
		
		/**
		 * 
		 * @param id to search for
		 * @return The warehouse with the corresponding id
		 */
		public Warehouse findById(int id);
		
		/**
		 * 
		 * @param address to search for
		 * @return The warehouse with the corresponding address
		 */
		public Warehouse findByAddress(String address);
		
		/**
		 *  (Create) Insert data into database
		 *  @param warehouse object
		 *  @return Returns generated warehouseID if update successful and 0 if error occurred
		 */
		public int save(Warehouse warehouse); 
		
		/**
		 * Update an existing entry
		 * @param Warehouse object
		 * @return true if success, false if error
		 */
		public boolean update(Warehouse warehouse); 
		
		/**
		 * Delete an entry
		 * @param int id
		 * @return true if success, false if error
		 */
		public boolean delete(int id);
}
