package de.ololololo;

import de.ololololo.utils.*;

import java.util.ArrayList;
import java.util.Date;

public class DataSource {

	private static DataSource instance;
	
	private FileHandler fileHandler;
	private ArrayList<Task> tasks;
	private int lastId;
	
	private DataSource() {
		fileHandler = new FileHandler();
		tasks = fileHandler.getData();
		
		//Search for highest id
		lastId = 0;
		for (Task t : tasks) {
			if (t.getId() > lastId) {
				lastId = t.getId();
			}
		}
	}
	
	public static DataSource getInstance(){
		if (instance == null){
			instance = new DataSource();
		}
		return instance;
	}
	
	public void newTask (String name, Date dueDate) {
		lastId++;
		Task newTask = new Task(lastId, name, dueDate);
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
			t.setName(newName);
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
	
	public void markAsComplete (int id) {
		Task t = getTask(id);
		if (t != null) {
			t.setCompleted(true);
		}
	}
	public void markAsInComplete (int id) {
		Task t = getTask(id);
		if (t != null) {
			t.setCompleted(false);
		}
	}
	public ArrayList<Task> getTasks () {
		return tasks;

	}
	public boolean close(){
		//Save the list to the xml
		return false; //temp buildfix
	}
	
	public void printAllTasks (){
		for (Task t : tasks) {
			System.out.println(t.getId() + ": " + t.getName() + (t.isCompleted() ? "(Completed)" : "(not completed)"));
		}
	}
}
