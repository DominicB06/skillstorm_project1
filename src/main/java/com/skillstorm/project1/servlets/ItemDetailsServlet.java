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
import com.skillstorm.project1.daos.SQLItemDetailsDAO;
import com.skillstorm.project1.models.ItemDetails;
import com.skillstorm.project1.services.URLParserService;

@WebServlet(urlPatterns = "/itemdetails/*")
public class ItemDetailsServlet extends HttpServlet{

	private static final long serialVersionUID = -8494012713691904819L;

	
	SQLItemDetailsDAO dao = new SQLItemDetailsDAO();
	ObjectMapper mapper = new ObjectMapper();
	URLParserService urlService = new URLParserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// check if there is a vaultID
		try {
			int id = Integer.parseInt(req.getParameter("vaultID"));
			List<ItemDetails> items = dao.findByVaultID(id);
			
			if(items != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(items));
			} else { // if no warehouse was found then client made a mistake so send 404 and a message
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString("Error: No item found with that id"));
			}
			return;
		} catch (Exception e) {
		}
		
		// check if there is a serialNum
		try {
			int serialNum = Integer.parseInt(req.getParameter("serialNum"));
			ItemDetails item = dao.findBySerialNum(serialNum);
			
			if(item != null) {
				resp.setContentType("application/json");
				resp.getWriter().print(mapper.writeValueAsString(item));
			} else { // if no warehouse was found then client made a mistake so send 404 and a message
				resp.setStatus(404);
				resp.getWriter().print(mapper.writeValueAsString("Error: No item found with that serial number"));
			}
			return;
		} catch (Exception e) {
		}
		
		String name = req.getParameter("name");
		if(name != null) {
			try {
				ItemDetails item = dao.findByName(name);
				
				if(item != null) {
					resp.setContentType("application/json");
					resp.getWriter().print(mapper.writeValueAsString(item));
				} else { // if no warehouse was found then client made a mistake so send 404 and a message
					resp.setStatus(404);
					resp.getWriter().print(mapper.writeValueAsString("Error: No item found with that name"));
				}
				return;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		resp.setStatus(400);
		resp.getWriter().print(mapper.writeValueAsString("Error: No search criteria provided"));
		return;
	
	}
	
	// Save
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// get the object information from the client
		InputStream reqBody = req.getInputStream();
		// use ObjectMapper to map the incoming java object to our warehouse object
		ItemDetails item = mapper.readValue(reqBody, ItemDetails.class);
		boolean success = dao.save(item);
		// if save was a success send message back
		if(success) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString("Success: Item created successfuly"));
			resp.setStatus(201);
		}else {
			resp.setStatus(400);
		}
	}
	
	// Update
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		InputStream reqBody = req.getInputStream();
		ItemDetails item = mapper.readValue(reqBody, ItemDetails.class);
		boolean updated = dao.update(item);
		// if the update was successful send message
		if(updated) {
			resp.setContentType("application/json");
			resp.getWriter().print(mapper.writeValueAsString("Success: Item has been updated"));
			resp.setStatus(201);
		}else {
			resp.getWriter().print(mapper.writeValueAsString("Error: Could not update the item"));
			resp.setStatus(400);
		}
	}
	
	// Delete
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		// for delete 1 item
		 try {
			 int serialNum = Integer.parseInt(req.getParameter("serialNum"));
				
				boolean deleted = dao.delete(serialNum);
				// if the delete was successful send the data and set status to 201
				if(deleted) {
					resp.setContentType("application/json");
					resp.getWriter().print(mapper.writeValueAsString("Success: Item has been deleted"));
					resp.setStatus(201);
				}else { //else delete was a failure
					resp.getWriter().print(mapper.writeValueAsString("Error: Could not delete the item"));
					resp.setStatus(400);
				}
			
		} catch (Exception e) {
		}
		 
		
	}
}








