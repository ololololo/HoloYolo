package de.ololololo;

import de.ololololo.utils.*;
 
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private static DataSource instance;

    private SQLiteDatabase mDatabase;
    private MySQLiteHelper mDbHelper;
    private String[] mAllColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_DUE,MySQLiteHelper.COLUMN_EVENTID };

    //ArrayList, that contains all Tasks
    private ArrayList<Task> mTasks;

    private DataSource(Context context) {
    	//Creates Database, if needed. Initializes it.
        mDbHelper = new MySQLiteHelper(context);
    }
	
	public static DataSource getInstance(Context context){
		//There should only be one instance of the datasource at a time (singleton)
		if (instance == null){
			instance = new DataSource(context);
            instance.open();
            instance.mTasks = new ArrayList<Task>();
        }
		return instance;
	}
	
	public void open() throws SQLException {
		//Opens database for r/w
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    //Creates a task in db with specified name, date and returns it
    public Task newTask(String name, Calendar dueDate, long eventid) {
    	//Variable, that holds all information to be inserted into db
    	long tome  = dueDate.getTimeInMillis();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_DUE, dueDate.getTimeInMillis());
        values.put(MySQLiteHelper.COLUMN_EVENTID, eventid);
        
        
        long insertId = mDatabase.insert(MySQLiteHelper.TABLE_ITEMS, null, values);
        
        //get inserted Task back from db
        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_ITEMS,
                mAllColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Task newItem = cursorToTask(cursor);
        cursor.close();
        
        //Fetches all tasks from db and saved them into mTasks
        getTasks();
        return newItem;
    }

    //removes given task from db
    public void removeTask(Task task) {
        int id = task.getId();
        removeTask(id);
        
    }  
    //removes given task (by id) from db  
    public void removeTask(int id) {
        mDatabase.delete(MySQLiteHelper.TABLE_ITEMS, MySQLiteHelper.COLUMN_ID + " = " + id, null);

        //Fetches all tasks from db and saved them into mTasks
        getTasks();
    }

    //Reads all tasks from db, saves them in mTasks and returns mTasks
    public ArrayList<Task> getTasks() {
    	//Empty task list
        mTasks.clear();

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_ITEMS, mAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            mTasks.add(task);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return mTasks;
    }

    //Helping method to convert db cursor to task object
    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.setTimeInMillis(cursor.getLong(2));
        task.setId((int)cursor.getLong(0));
        task.setName(cursor.getString(1));
        task.setEventID(cursor.getLong(3));
        task.setDue(tmpCal);
        
        
        return task;
    }
	
    //Edits name and date of task with specified id 
	public void editTask (int id, String newName, Calendar newDueDate, long eventid) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, newName);
        values.put(MySQLiteHelper.COLUMN_DUE, newDueDate.getTimeInMillis());
        values.put(MySQLiteHelper.COLUMN_EVENTID, eventid);
        
        String where = MySQLiteHelper.COLUMN_ID + "=" +id;
        
        mDatabase.update(MySQLiteHelper.TABLE_ITEMS, values, where,null);

        //Fetches all tasks from db and saved them into mTasks
        getTasks();
	}
	
	//Finds a task by id
	public Task getTask (int id) {
        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_ITEMS, mAllColumns, MySQLiteHelper.COLUMN_ID+"="+id, null, null, null, null);

        Task task;
        cursor.moveToFirst();
        if (cursor.isAfterLast()){
            //Empty result
            task = null;    
        } else {
            task = cursorToTask(cursor);
        }
        // Make sure to close the cursor
        cursor.close();

        return task;
	}
	
	//Prints all tasks on console (for debugging)
	public void printAllTasks (){
		for (Task t : mTasks) {
			System.out.println(t.getId() + ": " + t.getName() + (t.isCompleted() ? "(Completed)" : "(not completed)"));
		}
	}
}
