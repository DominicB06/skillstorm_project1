package com.skillstorm.project1.daos;

import com.skillstorm.project1.models.ItemDetails;

public interface ItemDetailsDAO {
	
	/**
	 * Find item by its real life serial number
	 * @param serialNum of item to find
	 * @return ItemDetails object requested
	 */
	public ItemDetails findBySerialNum(int serialNum);
	
	/**
	 * Find an item by its database generated itemID
	 * @param int itemID to search for
	 * @return ItemDetails object requested
	 */
	public ItemDetails findByItemID(int itemID);
		
	/**
	 * Find an item by its name
	 * @param String name of the item you want to search for
	 * @return ItemDetails object requested
	 */
	public ItemDetails findByName(String name);
	
	/**
	 * Create a new entry in the database
	 * @param The ItemDetail object to save to the database
	 * @return True if success, false if failed
	 */
	public boolean save(ItemDetails item);
	
	/**
	 * Update an existing item in the database
	 * @param The updated ItemDetail object (Should include fields you do not want updated)
	 * @return True if success, false if failed
	 */
	public boolean update(ItemDetails item); 
	
	/**
	 * Delete an item from the database
	 * @param serialNum of item to delete
	 * @return True if success, false if failed
	 */
	public boolean delete(int serialNum);

}
