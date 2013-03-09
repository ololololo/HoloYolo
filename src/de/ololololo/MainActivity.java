package de.ololololo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	// Static strings for exchanging data with bundles
	public static String BUNDLE_TASK_ID = "bundle_task_id";
	
	//Data
	private DataSource mDs;
	private ArrayList<Task> mTaskList;
	
	ArrayAdapter<Task> mAdapter;
	
	//UI
	Button mNewTaskButton;
	ListView mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDs = DataSource.getInstance(); //also reads the file
		
		mTaskList = mDs.getTasks();
		if (mTaskList == null){ //mTaskList must not be null for initUI()
			mTaskList = new ArrayList<Task>();
		}
		initUI();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_about:
			final Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
			
		default:
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	private void initUI(){
		//Button
		mNewTaskButton = (Button) findViewById(R.id.button_add);
		mNewTaskButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(getApplicationContext(), EditActivity.class);
				startActivity(intent);
				
			}
		});
		//List
		ListView mList = (ListView) findViewById(R.id.list_main);
		mAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, mTaskList);
		mList.setAdapter(mAdapter);
	}
	

}
