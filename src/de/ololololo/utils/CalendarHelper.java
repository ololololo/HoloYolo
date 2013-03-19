package de.ololololo.utils;

import java.util.ArrayList;
import java.util.Calendar;

import de.ololololo.DataSource;
import de.ololololo.Task;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class CalendarHelper {
	
	public static final String[] EVENT_PROJECTION = new String[] {
	    Calendars._ID,                           // 0
	    Calendars.ACCOUNT_NAME,                  // 1
	    Calendars.CALENDAR_DISPLAY_NAME,         // 2
	    Calendars.OWNER_ACCOUNT                  // 3
	};
	  
	// The indices for the projection array above.
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
	private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
	
	public void syncEvent(String accountType, String authName, Context mC)
	{
		DataSource mDs = DataSource.getInstance(mC);
		ArrayList<Task> mAl = mDs.getTasks();
		
		Cursor cur = null;
		ContentResolver cr = mC.getContentResolver();
		Uri uri = Calendars.CONTENT_URI;   
		String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" 
		                        + Calendars.ACCOUNT_TYPE + " = ?) AND ("
		                        + Calendars.OWNER_ACCOUNT + " = ?))";
		String[] selectionArgs = new String[] {authName, accountType,
		        authName}; 
		// Submit the query and get a Cursor object back. 
		cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
		
		while (cur.moveToNext()) {
		    long calID = 0;
		    String displayName = null;
		    String accountName = null;
		    String ownerName = null;
		      
		    // Get the field values
		    calID = cur.getLong(PROJECTION_ID_INDEX);
		    displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
		    accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
		    ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
		    
		    for(int i = 0; i<mAl.size();i++)
		    {
		    	
		    	
		    	long startMillis = 0; 
		    	long endMillis = 0;     
		    	Calendar beginTime = mAl.get(i).getDue();
		    	startMillis = beginTime.getTimeInMillis();
		    	Calendar endTime = mAl.get(i).getDue();
		    	endMillis = endTime.getTimeInMillis()+60000;
		    	

		    	ContentResolver cr2 = mC.getContentResolver();
		    	ContentValues values = new ContentValues();
		    	values.put(Events.DTSTART, startMillis);
		    	values.put(Events.DTEND, endMillis);
		    	values.put(Events.TITLE, mAl.get(i).getName());
		    	values.put(Events.DESCRIPTION, mAl.get(i).getName());
		    	values.put(Events.CALENDAR_ID, calID);
		    	values.put(Events.EVENT_TIMEZONE, "America/Los_Angeles");
		    	Uri uri2 = cr.insert(Events.CONTENT_URI, values);
		    	
		    	long eventID = Long.parseLong(uri2.getLastPathSegment());
		    	Log.i("bla",""+eventID);
		    	mAl.get(i).setEventID(eventID);
		    	mDs.editTask(mAl.get(i).getId(), mAl.get(i).getName(), mAl.get(i).getDue(), eventID);
		    }
		    
		}
	}
	
	public static void deleteFromCalendar(long id, Context mC)
	{
		long eventID = id;
		
		ContentResolver cr = mC.getContentResolver();
		ContentValues values = new ContentValues();
		Uri deleteUri = null;
		deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		int rows = mC.getContentResolver().delete(deleteUri, null, null);
	}

}
