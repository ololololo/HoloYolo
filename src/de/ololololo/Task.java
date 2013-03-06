package de.ololololo;

import java.util.Date;
public class Task {
		
	private long id;
    private String name;
    private long due;
    private boolean completed;


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
        return new Date(due);
    }

    public void setDue(Date due){
        this.due = due.getTime();
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
        return (completed ? "X " : "O ") + name + " (Due: "+getDue().toString()+")";
    }
} 
