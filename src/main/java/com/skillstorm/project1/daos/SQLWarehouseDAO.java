package com.skillstorm.project1.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.project1.conf.WarehouseDBCreds;
import com.skillstorm.project1.models.Inventory;
import com.skillstorm.project1.models.Warehouse;

public class SQLWarehouseDAO implements WarehouseDAO{

	@Override
	public List<Warehouse> findAll() {
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT * FROM warehouses";
			
			// generate statement and resultset to get results from query
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			// want to store all warehouses in a linked list
			LinkedList<Warehouse> warehouses = new LinkedList<>();
			
			// get each warehouse in db and add it to linked list
			while(rs.next()) {
				Warehouse wh = new Warehouse(rs.getInt("warehouseID"), rs.getString("address"), rs.getDouble("capacity"));
				warehouses.add(wh);
			}
			
			return warehouses;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public Warehouse findById(int id) {
	
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT * FROM warehouses WHERE warehouseID = ?";
			// generate statement and resultset to get results from query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			// use if to make sure their is a result
			// if result exists set a warehouse object to the result
			if(rs.next()) {
				Warehouse wh = new Warehouse(rs.getInt("warehouseID"), rs.getString("address"), rs.getDouble("capacity"));
				return wh;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Warehouse findByAddress(String address) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT * FROM warehouses WHERE address = ?";
			
			// generate statement and resultset to get results from query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, address);
			
			ResultSet rs = stmt.executeQuery();
			
			// use if to make sure their is a result
			// if result exists set a warehouse object to the result
			if(rs.next()) {
				Warehouse wh = new Warehouse(rs.getInt("warehouseID"), rs.getString("address"), rs.getDouble("capacity"));
				return wh;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int save(Warehouse warehouse) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {

			String sql = "INSERT INTO warehouses(address, capacity) VALUES (?, ?)";
			// want to make sure insert is successful before committing
			conn.setAutoCommit(false);
			
			// generate prepared statement
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, warehouse.getAddress());
			stmt.setDouble(2, warehouse.getCapacity());
			
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
	public boolean update(Warehouse warehouse) {
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "UPDATE warehouses SET address = ?, capacity = ? WHERE warehouseID = ?";
		
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			// If user doesnt provide an updated address, keep address the original
			if(warehouse.getAddress() == "") {
				Warehouse original = findById(warehouse.getWarehouseID());
				stmt.setString(1, original.getAddress());
			}else {
				stmt.setString(1, warehouse.getAddress());
			}
			
			// If user doesnt provide an updated capacity, keep the original
			if(warehouse.getCapacity() == 0) {
				Warehouse original = findById(warehouse.getWarehouseID());
				stmt.setDouble(2, original.getCapacity());
			}else {
				stmt.setDouble(2, warehouse.getCapacity());
			}
			
			stmt.setInt(3, warehouse.getWarehouseID());
			
			
			// execute the statement and make sure update was successful 
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected == 1) {
				conn.commit();
				return true;
			}
			else {
				conn.rollback();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public boolean delete(int id) {
		String sql = "DELETE FROM warehouses WHERE warehouseID = ?";
		
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			// if we delete a warehouse we also need to delete all vaults in the warehouse
			SQLInventoryDAO inventoryDAO = new SQLInventoryDAO();
			List<Inventory> vaults = inventoryDAO.findByWarehouse(id);
			for(Inventory i : vaults) {
				inventoryDAO.delete(i.getVaultID());
			}
			
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
