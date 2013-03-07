package de.ololololo;

import de.ololololo.utils.*;

import java.util.ArrayList;
import java.util.Date;

public class DataSource {

	private FileHandler fileHandler;
	private ArrayList<Task> tasks;
	
	
	public DataSource() {
		fileHandler = new FileHandler();
		tasks = fileHandler.getData();
	}
	
	
	public void newTask (String name, Date dueDate) {
		Task newTask = new Task(12, name, dueDate);
		tasks.add(newTask);
	}
	
	public void removeTask (int id) {
		Task t = getTask(id);
		if (t != null) {
			tasks.remove(t);
		}
	}
	
	public void editTask (int id, String newName, Date newDueDate) {
		Task t = getTask(id);
		if (t != null) {
			t.setname(newName);
			t.setDue(newDueDate);
		}
	}
	
	public Task getTask (int id) {
		for (Task t : tasks) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}
	
	public ArrayList<Task> getTasks () {
		return tasks;
	}
}
