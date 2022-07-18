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
import com.skillstorm.project1.daos.SQLInventoryDAO;
import com.skillstorm.project1.models.Inventory;
import com.skillstorm.project1.services.URLParserService;

@WebServlet(urlPatterns = "/inventory/*")
public class InventoryServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6453203564351495933L;
	
	SQLInventoryDAO dao = new SQLInventoryDAO();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		// For findByWarehouse
		try {
			//int id = urlService.extractIDFromURL(req.getPathInfo());
			int id = Integer.parseInt(req.getParameter("id"));
			List<Inventory> i = dao.findByWarehouse(id);
			
			// if a warehouse was returned successfully
			// send it as a response as json
			if(i != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(i));
			} else { // if no warehouse was found then client made a mistake so send 404 and a message
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString("Error: No item found with that id"));
			}
			
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// for findUsage
		try {
			
			int usageId = Integer.parseInt(req.getParameter("usageId"));
			double usage = dao.findUsage(usageId);
			
			if(usage != 0) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(usage));
			} else {
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString("Error: No warehouse found with that id"));
			}
			
			return;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		resp.setStatus(400);
		resp.getWriter().print(mapper.writeValueAsString("Error: No search parameters provided"));
		return;
	}
	
	// Save
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		InputStream reqBody = req.getInputStream();
		// use ObjectMapper to map the incoming java object to our inventory object
		Inventory i = mapper.readValue(reqBody, Inventory.class);
		int generatedKey = dao.save(i);
		// if the generatedKey is returned then Create was successful
		if(generatedKey > 0) {
			// set the itemID to the generatedKey before sending it back to client
			i.setItemID(generatedKey);
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString(i));
			resp.setStatus(201);
		}else if(generatedKey == -1) { // -1 is returned if inserting did not pass capacity check
			resp.getWriter().print("Error: This warehouse does not have enough space for this inventory");
			resp.setStatus(400);
		}else {
			resp.getWriter().print("Error: Something went wrong trying to create this inventory");
			resp.setStatus(400);
		}
	}
	
	// Update
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream reqBody = req.getInputStream();
		Inventory i = mapper.readValue(reqBody, Inventory.class);
		boolean updated = dao.update(i);
		// if the update was successful send the data and set status to 201
		if(updated) {
			resp.setContentType("application/json");
			resp.getWriter().print("Success: Inventory has been updated");
			resp.setStatus(201);
		}else { //else update was a failure
			resp.getWriter().print("Error: Could not update the inventory");
			resp.setStatus(400);
		}
	}
	
	// Delete
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		boolean deleted = dao.delete(id);
		// if the delete was successful send the data and set status to 201
		if(deleted) {
			resp.setContentType("application/json");
			resp.getWriter().print("Success: Inventory has been deleted");
			resp.setStatus(201);
		}else { //else delete was a failure
			resp.getWriter().print("Error: Could not delete the inventory");
			resp.setStatus(400);
		}
	}

}
