package com.skillstorm.project1.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.skillstorm.project1.conf.WarehouseDBCreds;
import com.skillstorm.project1.models.ItemDetails;

public class SQLItemDetailsDAO implements ItemDetailsDAO{

	@Override
	public ItemDetails findBySerialNum(int serialNum) {
		// need to changed this later to use application.properties
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT name, vaultID FROM itemdetails WHERE serialNum = ?";
			
			// generate statement and resultset to get results from query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, serialNum);
			
			ResultSet rs = stmt.executeQuery();
		
			// get all items
			if(rs.next()) {
				ItemDetails i = new ItemDetails(serialNum, rs.getString("name"), rs.getInt("vaultID"));
				return i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ItemDetails findByVaultID(int vaultID) {
		// need to changed this later to use application.properties
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT serialNum, name FROM itemdetails WHERE vaultID = ?";
			
			// generate statement and resultset to get results from query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, vaultID);
			
			ResultSet rs = stmt.executeQuery();
		
			// get all items
			if(rs.next()) {
				ItemDetails i = new ItemDetails(rs.getInt("serialNum"), rs.getString("name"), vaultID);
				return i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ItemDetails findByName(String name) {
		// need to changed this later to use application.properties
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "SELECT serialNum, vaultID FROM itemdetails WHERE name = ?";
			
			// generate statement and resultset to get results from query
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			
			ResultSet rs = stmt.executeQuery();
		
			// get all items
			if(rs.next()) {
				ItemDetails i = new ItemDetails(rs.getInt("serialNum"), name, rs.getInt("vaultID"));
				return i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean save(ItemDetails item) {
		// need to changed this later to use application.properties
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()) {
			
			String sql = "INSERT INTO itemdetails VALUES (?, ?, ?)";
			
			// want to make sure insert is successful before committing
			conn.setAutoCommit(false);
			
			// generate prepared statement
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, item.getSerialNum());
			stmt.setString(2, item.getName());
			stmt.setInt(3, item.getVaultID());
			
			// execute the statement and make sure update was successful 
			int rowsAffected = stmt.executeUpdate();
			if(rowsAffected != 0) {
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
	public boolean update(ItemDetails item) {
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()){
			
			String sql = "UPDATE itemdetails SET serialNum = ?, name = ?, vaultID = ? WHERE serialNum = ?";
			
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, item.getSerialNum());
			stmt.setString(2, item.getName());
			stmt.setInt(3, item.getVaultID());
			stmt.setInt(4, item.getSerialNum());
			
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
	public boolean delete(int serialNum) {
		try(Connection conn = WarehouseDBCreds.getInstance().getConnection()){
			
			String sql = "DELETE FROM itemdetails WHERE serialNum = ?";
			
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, serialNum);
			
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
	

}















