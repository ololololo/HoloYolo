package de.ololololo;


import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class EditActivity extends Activity {
	//Logic
	DataSource mDs;
	
	int mTaskId = -1;
	
	//UI
	EditText mEtTaskname;
	DatePicker mDatePicker;
	Button mBttnCancel;
	Button mBttnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		mDs = DataSource.getInstance(this);
				
		initUI();
		
		//set the fields when editing
		if (getIntent().getExtras() != null){
			Bundle extras = getIntent().getExtras();
			mTaskId = extras.getInt(MainActivity.BUNDLE_TASK_ID);
			Task mTask = mDs.getTask(mTaskId);
			
			mEtTaskname.setText(mTask.getName());
			Calendar tmpDate = mTask.getDue();
			mDatePicker.updateDate(tmpDate.get(Calendar.YEAR), tmpDate.get(Calendar.MONTH), tmpDate.get(Calendar.DAY_OF_MONTH));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
	
	private void initUI(){
		mEtTaskname = (EditText) findViewById(R.id.edit_taskname);
		mDatePicker = (DatePicker) findViewById(R.id.datePicker1);
		mBttnCancel = (Button) findViewById(R.id.edit_bttn_cancel);
		mBttnSave = (Button) findViewById(R.id.edit_bttn_save);
		
		mBttnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		mBttnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tmpName = mEtTaskname.getText().toString();
				Calendar cal = Calendar.getInstance();
				cal.set(mDatePicker.getYear(), mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
				
				if(mTaskId == -1){ // not defined in onCreate, we are creating a new task
					mDs.newTask(tmpName, cal);
				} else { // we have an existing task and update it
					mDs.editTask(mTaskId, tmpName, cal);
				}
				
				finish();
				
			}
		});
	}

}
