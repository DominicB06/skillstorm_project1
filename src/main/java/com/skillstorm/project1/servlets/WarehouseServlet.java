package com.skillstorm.project1.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.project1.daos.SQLWarehouseDAO;
import com.skillstorm.project1.models.Warehouse;
import com.skillstorm.project1.services.URLParserService;

@WebServlet(urlPatterns = "/warehouses/*")
public class WarehouseServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9020597701714742978L;
	
	SQLWarehouseDAO dao = new SQLWarehouseDAO();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// For warehouse.findById
		try {
			//int id = urlService.extractIDFromURL(req.getPathInfo());
			int id = Integer.parseInt(req.getParameter("id"));
			Warehouse wh = dao.findById(id);
			
			// if a warehouse was returned successfully
			// send it as a response as json
			if(wh != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(wh));
			} else { // if no warehouse was found then client made a mistake so send 404 and a message
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString("Error: No warehouse found with that id"));
			}
			
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// for warehouse.findByAddress
		String address = req.getParameter("address");
		if(address != null) {
			try {
				//String address = urlService.extractStringFromURL(req.getPathInfo());
				Warehouse wh = dao.findByAddress(address);
				
				// if a warehouse was returned successfully
				// send it as a response as json
				if(wh != null) {
					resp.setContentType("application/json");
					resp.getWriter().print(mapper.writeValueAsString(wh));
				} else { // if no warehouse was found then client made a mistake so send 404 and a message
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString("Error: No warehouse found with that address"));
				}
				
				return;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		
		// if we get past the above two try blocks then the user just wants all warehouses
		List<Warehouse> wh = dao.findAll();
		resp.setContentType("application/json");
		resp.getWriter().print(mapper.writeValueAsString(wh));
	}
	
	// Save
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// get the object information from the client
		InputStream reqBody = req.getInputStream();
		// use ObjectMapper to map the incoming java object to our warehouse object
		Warehouse wh = mapper.readValue(reqBody, Warehouse.class);
		int generatedKey = dao.save(wh);
		// if the generatedKey is returned then Create was successful
		if(generatedKey != 0) {
			// set the warehouseID to the generatedKey before sending it back to client
			wh.setWarehouseID(generatedKey);
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(wh));
			resp.setStatus(201);
		}else {
			//if generated key was 0 then the warehouse was not created
			resp.getWriter().print("Error: Could not create warehouse");
			resp.setStatus(400);
		}
	}
	
	// Update
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream reqBody = req.getInputStream();
		Warehouse wh = mapper.readValue(reqBody, Warehouse.class);
		boolean updated = dao.update(wh);
		// if the update was successful send the data and set status to 201
		if(updated) {
			resp.setContentType("application/json");
			resp.getWriter().print("Success: Warehouse has been updated");
			resp.setStatus(201);
		}else { //else update was a failure
			resp.getWriter().print("Error: Could not update the warehouse");
			resp.setStatus(400);
		}
	}
	
	// Delete
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// use URLParserService to get the id they want to delete
//		int id = urlService.extractIDFromURL(req.getPathInfo());
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		boolean deleted = dao.delete(id);
		// if the delete was successful send the data and set status to 201
		if(deleted) {
			resp.setContentType("application/json");
			resp.getWriter().print("Success: Warehouse has been deleted");
			resp.setStatus(201);
		}else { //else delete was a failure
			resp.getWriter().print("Error: Could not delete the warehouse");
			resp.setStatus(400);
		}
	}
}













