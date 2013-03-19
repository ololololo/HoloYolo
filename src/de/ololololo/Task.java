package de.ololololo;

import java.util.Calendar;

import android.util.Log;
public class Task {
		
	private int id;
    private String name;
    private Calendar due;
    private boolean completed;
    private long eventid = -1;

    public Task () {}
    public Task (int id, String name, Calendar due) {
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

    public Calendar getDue() {
        return due;
    }

    public void setDue(Calendar due){
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
    	Log.i("bla",name+"Hallo");
    	//Log.i("bla",due.toString());
    	
    	
    	return name + ";  Due: " + due.get(Calendar.DAY_OF_MONTH) + "." + (due.get(Calendar.MONTH)+1) + "." + due.get(Calendar.YEAR);
        // debug return (completed ? "X " : "O ") + name + " (Due: "+due.toString()+")";
    }
	@Override
	public boolean equals(Object arg0) {
		Task t = (Task)arg0;
		return (t.getId() == this.id);
	}
	
	public void setEventID(long i)
	{
		eventid = i;
	}
	
	public long getEventID()
	{
		return eventid;
	}
} 
