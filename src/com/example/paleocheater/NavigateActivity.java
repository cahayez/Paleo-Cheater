package com.example.paleocheater;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
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

public class NavigateActivity extends ActionBarActivity {

	private Button cheat, noCheat, awesome;
	private TextView tvPoints, tvTimesCheated;
	public int points;
	private int timesCheated;
	private DAO datasource;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_navigate);

		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
		 */

		cheat = (Button) findViewById(R.id.btnCheated);
		noCheat = (Button) findViewById(R.id.btnNoCheat_Navigate);
		awesome = (Button) findViewById(R.id.btnDidAwesome);
		tvPoints = (TextView) findViewById(R.id.tvPoints_Nav);
		tvTimesCheated = (TextView) findViewById(R.id.tvTimesCheated_Nav);

		datasource = new DAO(this);
		datasource.open();

		points = datasource.getPoints();
		tvPoints.setText(Integer.toString(points));
		timesCheated = datasource.getTimesCheated();
		tvTimesCheated.setText(Integer.toString(timesCheated));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigate, menu);
		return true;
	}

	public void onClick(View v) {
		if(v.getId() == R.id.btnNoCheat_Navigate){
			//Only saves and add points if this is the first time you clicked today.
			if(!datasource.noCheatClickedToday())
			{
				points = points + 100;
				datasource.createCheat(getCurrentDate(), "Didn't Cheat", 100, false);
			}

			//TODO remove 100pts if they click a cheat today

		}
		//Start new activity and pass the points and times cheated so I don't have to load from a database.
		if(v.getId() == R.id.btnCheated){
			Bundle extras = new Bundle();
			Intent intent = new Intent(this, CheatedActivity.class);
			extras.putInt("points", points);
			extras.putInt("timesCheated", timesCheated);
			intent.putExtras(extras);
			startActivity(intent);
		}
		if(v.getId() == R.id.btnDidAwesome){
			Bundle extras = new Bundle();
			Intent intent = new Intent(this, AwesomeActivity.class);
			extras.putInt("points", points);
			extras.putInt("timesCheated", timesCheated);
			intent.putExtras(extras);
			startActivity(intent);
		}
		
		if(points < 0) points = 0;
		tvPoints.setText(Integer.toString(points));
		tvTimesCheated.setText(Integer.toString(timesCheated));
	}

	@Override
	protected void onResume(){
		super.onResume();
		points = datasource.getPoints();
		tvPoints.setText(Integer.toString(points));
		timesCheated = datasource.getTimesCheated();
		tvTimesCheated.setText(Integer.toString(timesCheated));
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_navigate,
					container, false);
			return rootView;
		}
	}

	private String getCurrentDate(){
		return dateFormat.format(Calendar.getInstance().getTime());
	}


}
