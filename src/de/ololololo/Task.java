package de.ololololo;

import java.util.Date;
public class Task {
		
	private long id;
    private String name;
    private Date due;
    private boolean completed;

    public Task () {}
    public Task (int id, String name, Date due) {
    	this.id = id;
    	this.name = name;
    	this.due = due;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
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
} 
