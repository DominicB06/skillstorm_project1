package com.skillstorm.project1.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.skillstorm.project1.models.Warehouse;

public class SQLWarehouseDAO implements WarehouseDAO{

	@Override
	public List<Warehouse> findAll() {
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {
			
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
	
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {
			
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
		
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {
			
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
		
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {

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
		
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {
			
			String sql = "UPDATE warehouses SET address = ?, capacity = ? WHERE warehouseID = ?";
			
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, warehouse.getAddress());
			stmt.setDouble(2, warehouse.getCapacity());
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
		
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {
			
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

	
	// for testing
//		public static void main(String[] args) {
//			SQLWarehouseDAO dao = new SQLWarehouseDAO();
//			Warehouse wh = new Warehouse(2, 750.0);
//			Warehouse wh2 = new Warehouse(1, "1234 main st" ,633.0);
//			
//			List<Warehouse> wh = new LinkedList<>();
//			
//			wh = dao.findAll();
//			
//			System.out.println(wh.get(0).getWarehouseID());
//			System.out.println(wh.get(0).getAddress());
//			System.out.println(wh.get(0).getCapacity());
			
		
//			Warehouse wh = dao.findByAddress("1000 vanguard blvd");
//			System.out.println(wh.getWarehouseID());
//			System.out.println(wh.getAddress());
//			System.out.println(wh.getCapacity());
			
//			int insert = dao.save(wh);
//			
//			if(insert != 0) {
//				System.out.println("Update succesful, Given warehouse ID: " + insert);
//			}else {
//				System.out.println("Update failed");
//			}
			
//			if(dao.delete(11)) {
//				System.out.println("Delete success");
//			}else {
//				System.out.println("Delete fail");
//			}
			
//			if(dao.update(wh2)) {
//				System.out.println("Update success");
//			}else {
//				System.out.println("Update fail");
//			}
//			
//		}

}
