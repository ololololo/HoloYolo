package de.ololololo.utils;

import de.ololololo.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList; 

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class FileHandler extends Activity{

	private Context ctx;
	private SQLiteDatabase db;
	
	//Public Methods
	public ArrayList<Task> getData() {
		return new ArrayList<Task>();
	}
	
	public String writeData(ArrayList<Task> data) throws FileNotFoundException {
		
		db = openOrCreateDatabase("ToDo.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		final String CREATE_TABLE_TASK = "CREATE TABLE tbl_task (id INTEGER PRIMARY KEY, task TEXT, date DATE);";
		db.execSQL(CREATE_TABLE_TASK);
		ContentValues values = new ContentValues();
		
		for (int i = 0;i<data.size();i++)
		{
			values.put("id", data.get(i).getId());
			values.put("task", data.get(i).getname());
			values.put("date", data.get(i).getDue().toString());
		}
		
		if (true) { //write successful
			return null;
		} else {
			//On error 
			return "Error Message";
		}
	}
}
