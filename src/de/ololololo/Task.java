package de.ololololo;

import java.util.Date;
public class Task {
		
	private int id;
    private String name;
    private Date due;
    private boolean completed;

    public Task () {}
    public Task (int id, String name, Date due) {
    	this.id = id;
    	this.name = name;
    	this.due = due;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name= name;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due){
        this.due = due;
    }

    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return (completed ? "X " : "O ") + name + " (Due: "+due.toString()+")";
    }
	@Override
	public boolean equals(Object arg0) {
		Task t = (Task)arg0;
		return (t.getId() == this.id);
	}
} 
