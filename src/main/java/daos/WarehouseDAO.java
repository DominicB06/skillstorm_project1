package daos;

import java.util.List;

import models.Warehouse;

public interface WarehouseDAO {

	
	//CRUD is Create, Read, Update, Delete
	
		// Read functions
		public List<Warehouse> findAll();
		public Warehouse findById(int id);
		public Warehouse findByName(String address);
		
		// Create function
		// return artist since we can get its key (if auto incremented)
		public Warehouse save(Warehouse warehouse); 
		
		// Update function
		public void update(Warehouse warehouse); // contains id and updates as needed
		
		// Delete function
		public void delete(int id);
		public void deleteMany(int[] ids);
	
	
}
