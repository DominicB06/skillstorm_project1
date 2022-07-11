package daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import models.Warehouse;

public class SQLWarehouseDAO implements WarehouseDAO{

	@Override
	public List<Warehouse> findAll() {
		
		// to get all the warehouses we select *
		String sql = "SELECT * FROM warehouses";
		
		// need to changed this later to use application.properties
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/warehouse","root","password123")) {
			
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
	
	public static void main(String[] args) {
		SQLWarehouseDAO dao = new SQLWarehouseDAO();
		
		List<Warehouse> wh = new LinkedList<>();
		
		wh = dao.findAll();
		
		System.out.println(wh.get(0).getWarehouseID());
		System.out.println(wh.get(0).getAddress());
		System.out.println(wh.get(0).getCapacity());
	}

	@Override
	public Warehouse findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Warehouse findByName(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Warehouse save(Warehouse warehouse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Warehouse warehouse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMany(int[] ids) {
		// TODO Auto-generated method stub
		
	}

}
