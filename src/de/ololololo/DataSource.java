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
	
	// Database fields
    private SQLiteDatabase mDatabase;
    private MySQLiteHelper mDbHelper;
    private String[] mAllColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_DUE,MySQLiteHelper.COLUMN_COMPLETED };

    private ArrayList<Task> mTasks;

    private DataSource(Context context) {
        mDbHelper = new MySQLiteHelper(context);
    }
	
	public static DataSource getInstance(Context context){
		if (instance == null){
			instance = new DataSource(context);
            instance.open();
            instance.mTasks = new ArrayList<Task>();
        }
		return instance;
	}
	
	public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Task newTask(String name, Calendar dueDate) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_DUE, dueDate.getTimeInMillis());
        values.put(MySQLiteHelper.COLUMN_COMPLETED, 0);
        long insertId = mDatabase.insert(MySQLiteHelper.TABLE_ITEMS, null,
                values);
        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_ITEMS,
                mAllColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Task newItem = cursorToTask(cursor);
        cursor.close();
        getTasks();
        return newItem;
    }

    public void removeTask(Task task) {
        int id = task.getId();
        removeTask(id);
    }    
    public void removeTask(int id) {
        System.out.println("Item deleted with id: " + id);
        mDatabase.delete(MySQLiteHelper.TABLE_ITEMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        getTasks();
    }

    
    public ArrayList<Task> getTasks() {
        mTasks.clear();

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_ITEMS,
                mAllColumns, null, null, null, null, null);

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

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.setTimeInMillis(cursor.getLong(2));
        task.setId((int)cursor.getLong(0));
        task.setName(cursor.getString(1));
        task.setDue(tmpCal);
        task.setCompleted(cursor.getInt(3) == 0 ? false : true);
        return task;
    }
	
	public void editTask (int id, String newName, Calendar newDueDate) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, newName);
        values.put(MySQLiteHelper.COLUMN_DUE, newDueDate.getTimeInMillis());
        String where = MySQLiteHelper.COLUMN_ID + "=" +id;
        mDatabase.update(MySQLiteHelper.TABLE_ITEMS, values, where,null);
        getTasks();
	}
	
	public Task getTask (int id) {
        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_ITEMS,
                mAllColumns, MySQLiteHelper.COLUMN_ID+"="+id, null, null, null, null);

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
	
	public void printAllTasks (){
		for (Task t : getTasks()) {
			System.out.println(t.getId() + ": " + t.getName() + (t.isCompleted() ? "(Completed)" : "(not completed)"));
		}
	}
}
