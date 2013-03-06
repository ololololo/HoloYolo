package de.ololololo;

import de.ololololo.utils.*;

import java.util.ArrayList;
import java.util.Date;

public class DataSource {

	private FileHandler fileHandler;
	
	
	public DataSource() {
		fileHandler = new FileHandler();
	}
	
	
	public void newTask (String name, Date dueDate) {
		
	}
	
	public void removeTask (int id) {
		
	}
	
	public void editTask (int id, String newName, Date newDueDate) {
		
	}
	
	public Task getTask (int id) {
		return null;
	}
	
	public ArrayList<Task> getTasks () {
		return null;
	}
}
