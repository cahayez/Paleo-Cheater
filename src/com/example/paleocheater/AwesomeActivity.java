package com.example.paleocheater;


import java.text.SimpleDateFormat;

import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
//import android.text.format.DateFormat;
import java.text.DateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
//import android.os.Build;

public class AwesomeActivity extends ActionBarActivity {

	
	private Button heavyThings, superFood, walk;
	private TextView tvPoints, tvTimesCheated;
	public int points;
	private int timesCheated;
	private DAO datasource;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_awesome);
		
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		points = data.getInt("points");
		timesCheated = data.getInt("timesCheated");
		
		heavyThings = (Button) findViewById(R.id.btnHeavy);
		superFood = (Button) findViewById(R.id.btnSuperFood);
		walk = (Button) findViewById(R.id.btnWalked);
		tvPoints = (TextView) findViewById(R.id.tvPoints_Awe);
		tvTimesCheated = (TextView) findViewById(R.id.tvTimesCheated_Awe);
		
		tvPoints.setText(Integer.toString(points));
		tvTimesCheated.setText(Integer.toString(timesCheated));
		
		datasource = new DAO(this);
		datasource.open();
		
		

//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.awesome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick(View v) {

		if(v.getId() == R.id.btnHeavy){
			points = points + 100;
			datasource.createCheat(getCurrentDate(), "Heavy Things", 100, false);
		}
		if(v.getId() == R.id.btnSuperFood){
			points = points + 50;
			datasource.createCheat(getCurrentDate(), "Super Food", 50, false);
		}
		if(v.getId() == R.id.btnWalked){
			points = points + 50;
			datasource.createCheat(getCurrentDate(), "Walked", 50, false);
		}

		if(points < 0) points = 0;
		tvPoints.setText(Integer.toString(points));
		//tvTimesCheated.setText(Integer.toString(timesCheated));
		
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_awesome,
					container, false);
			return rootView;
		}
	}

	private String getCurrentDate(){
		return dateFormat.format(Calendar.getInstance().getTime());
	}
}
