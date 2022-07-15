package com.skillstorm.project1.conf;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class WarehouseDBCreds {

	private static WarehouseDBCreds instance;
	private String url;
	private String username;
	private String password;
	
	private WarehouseDBCreds() {
		
		try {
			// Load into memory immediately so that you have it
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Read the properties (key/value pairs) from application.properties
			try(InputStream input = WarehouseDBCreds.class.getClassLoader().getResourceAsStream("application.properties")){
				//properties object
				Properties props = new Properties();
				props.load(input); // load in the file we opened
				
				// Grab out the keys you want
				this.url = props.getProperty("db.url");
				this.username = props.getProperty("db.username");
				this.password = props.getProperty("db.password");
				
			}catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static WarehouseDBCreds getInstance() {
		
		if(instance == null) {
			instance = new WarehouseDBCreds();
		}
		return instance;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	// Don't need if doing method 2 in MySQLArtistDAOImpl
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, username, password);
	}
}
