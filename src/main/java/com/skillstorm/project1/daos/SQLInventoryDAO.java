package com.skillstorm.project1.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.project1.conf.WarehouseDBCreds;
import com.skillstorm.project1.models.Inventory;
import com.skillstorm.project1.services.CapacityCheck;


public class SQLInventoryDAO implements InventoryDAO {

	@Override
	public List<Inventory> findByWarehouse(int warehouseId) {
		
		// need to changed this later to use application.properties
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			LinkedList<Inventory> items = new LinkedList<>();
			String sql = "SELECT itemID, size FROM inventory WHERE warehouse = ?";
			
			// generate statement and resultset to get results from query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, warehouseId);
			
			ResultSet rs = stmt.executeQuery();
		
			// get all items
			while(rs.next()) {
				Inventory i = new Inventory(rs.getInt("itemID"), rs.getDouble("size"), warehouseId);
				items.add(i);
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public double findUsage(int warehouseId) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT SUM(size) FROM inventory WHERE warehouse = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, warehouseId);
			
			ResultSet rs = stmt.executeQuery();
		
			// get all items
			if(rs.next()) {
				double sum = rs.getDouble(1);
				return sum;
			}
			
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return 0;
	}

	@Override
	public int save(Inventory item) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
		
			CapacityCheck check = new CapacityCheck();
			if(!check.checkCapacity(item.getWarehouse(), item.getSize())) {
				return -1;
			}
			
			String sql = "INSERT INTO inventory(size, warehouse) VALUES (?, ?)";
		
			// want to make sure insert is successful before committing
			conn.setAutoCommit(false);
			
			// generate prepared statement
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1, item.getSize());
			stmt.setInt(2, item.getWarehouse());
			
			// execute the statement and make sure update was successful 
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected != 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					conn.commit();
					return id; 
				}
			}
			else {
				conn.rollback();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean update(Inventory item) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()){
			
			CapacityCheck check = new CapacityCheck();
			if(!check.checkCapacity(item.getWarehouse(), item.getSize())) {
				return false;
			}
			
			String sql = "UPDATE inventory SET size = ?, warehouse = ? WHERE itemID = ?";
			
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, item.getSize());
			stmt.setInt(2, item.getWarehouse());
			stmt.setInt(3, item.getItemID());
			
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected == 1) {
				conn.commit();
				return true;
			}else {
				conn.rollback();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean delete(int id) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "DELETE FROM inventory WHERE itemID = ?";
			
			// want to make sure insert is successful before committing
			conn.setAutoCommit(false);
			
			// generate prepared statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			// execute the statement and make sure update was successful 
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected == 1) {
				conn.commit();
				return true;
			}
			else {
				conn.rollback();
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

















