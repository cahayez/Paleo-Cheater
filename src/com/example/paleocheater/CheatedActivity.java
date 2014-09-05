package com.example.paleocheater;

//import com.Curt.buttonpusher.R;

//TODO If points go below 0, they start above 0.  Unsigned int or something weird like that.



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormat;


import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
//import android.support.v7.internal.widget.ActionBarContextView;
import android.support.v4.app.Fragment;
//import android.content.Context;
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

public class CheatedActivity extends ActionBarActivity {

	private Button sugar, bread, corn, dairy, soy, pufa;
	private TextView tvPoints, tvTimesCheated;
	public int points;
	private int timesCheated;

	//private Calendar startDate;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	//private boolean debug = false;
	private DAO datasource;


	@Override
	protected void onPause(){
		super.onPause();
		
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		datasource.close();
	}
	
//	@Override
//	protected void onResume(){
//		
//	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fragment_cheated);
		init();

		//points = readData("Points");
		
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		points = data.getInt("points");
		timesCheated = data.getInt("timesCheated");
		
		//points = datasource.getPoints();
		tvPoints.setText(Integer.toString(points));
		//timesCheated = datasource.getTimesCheated();
		tvTimesCheated.setText(Integer.toString(timesCheated));

		/*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        */
		 

	}
	
	private void init(){

		sugar = (Button) findViewById(R.id.btnHeavy);
		bread = (Button) findViewById(R.id.btnSuperFood);
		corn = (Button) findViewById(R.id.btnWalked);
		dairy = (Button) findViewById(R.id.btnDairy);
		soy = (Button) findViewById(R.id.btnSoy);
		pufa = (Button) findViewById(R.id.btnPUFA);
		tvPoints = (TextView) findViewById(R.id.tvPoints_Nav);
		tvTimesCheated = (TextView) findViewById(R.id.tvTimesCheated_Nav);

//		File cheatedFile = getBaseContext().getFileStreamPath("Cheated");
//		File pointsFile = getBaseContext().getFileStreamPath("Points");
//		File startDateFile = getBaseContext().getFileStreamPath("startDate");
//
//		if(debug){
//			cheatedFile.delete();
//			pointsFile.delete();
//			startDateFile.delete();
//		}
//
//		if(!cheatedFile.exists()){
//			saveData("Cheated", 0);
//		}
//		if(!pointsFile.exists()){
//			saveData("Points", 100);
//		}
		/*
    	if(!startDateFile.exists()){
    		startDate = Calendar.getInstance();
    		//@SuppressWarnings("unused")
    		//String stuff;
    		saveData("startDate", dateFormat.format(startDate.getTime()));
    		if(debug){
    			saveData("startDate", "2014/06/01");
    		}
    		//stuff = dateFormat.format(startDate.getTime());
    		//stuff = readSavedData("startDate");
    		//System.out.println(stuff);
    	}
		 */

		datasource = new DAO(this);
		datasource.open();
	}



	public void onClick(View v) {

		if(v.getId() == R.id.btnHeavy){
			/*
			if(noCheatClickedToday()){
				points = points - 100;
			}
			 */
			points = points - 10;
			datasource.createCheat(getCurrentDate(), "Sugar", 10, true);
			timesCheated++;
		}
		if(v.getId() == R.id.btnSuperFood){
			points = points - 20;
			datasource.createCheat(getCurrentDate(), "Bread", 20, true);
			timesCheated++;
		}
		if(v.getId() == R.id.btnWalked){
			points = points - 20;
			datasource.createCheat(getCurrentDate(), "Corn", 20, true);
			timesCheated++;
		}
		if(v.getId() == R.id.btnDairy){
			points = points - 5;
			datasource.createCheat(getCurrentDate(), "Dairy", 5, true);
			timesCheated++;
		}
		if(v.getId() == R.id.btnSoy){
			points = points - 20;
			datasource.createCheat(getCurrentDate(), "Soy", 20, true);
			timesCheated++;
		}
		if(v.getId() == R.id.btnPUFA){
			points = points - 20;
			datasource.createCheat(getCurrentDate(), "PUFA", 20, true);
			timesCheated++;
		}
		if(v.getId() == R.id.btnStartover){

			datasource.deleteAllCheats();
			timesCheated = 0;
			points = 100;
		}
		
//		no cheat button			
//		//Only saves and add points if this is the first time you clicked today.
//		if(!datasource.noCheatClickedToday())
//		{
//			points = points + 100;
//			datasource.createCheat(getCurrentDate(), "Didn't Cheat", 100, false);
//			//saveData("startDate", getCurrentDate());
//		}
//
//		//TODO remove 100pts if they click a cheat today

		if(points < 0) points = 0;
		tvPoints.setText(Integer.toString(points));
//		saveData("Points", points);
//		saveData("Cheated", timesCheated);
		tvTimesCheated.setText(Integer.toString(timesCheated));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cheated, container, false);
			return rootView;
		}
	}

//	public void saveData(String FILENAME, int data){
//		try {
//			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//			fos.write(data);
//			fos.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void saveData(String FILENAME, String data) {
//		try {
//			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
//			fos.write(data.getBytes());
//			fos.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	//}
//
//	public int readData(String FILENAME){
//		int output = 0;
//		FileInputStream fis = null;
//		File file = getBaseContext().getFileStreamPath(FILENAME);
//		if(!file.exists()){
//			saveData(FILENAME, 0);
//		}
//
//		try {
//			fis = openFileInput(FILENAME);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			output = fis.read();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return output;
//	}

//	public String readSavedData (String FILENAME ) {
//		StringBuffer datax = new StringBuffer("");
//		try {
//			FileInputStream fIn = openFileInput (FILENAME) ;
//			InputStreamReader isr = new InputStreamReader ( fIn ) ;
//			BufferedReader buffreader = new BufferedReader ( isr ) ;
//
//			String readString = buffreader.readLine ( ) ;
//			while ( readString != null ) {
//				datax.append(readString);
//				readString = buffreader.readLine ( ) ;
//			}
//
//			isr.close ( ) ;
//		} catch ( IOException ioe ) {
//			ioe.printStackTrace ( ) ;
//		}
//		return datax.toString() ;
//	}

	private String getCurrentDate(){
		return dateFormat.format(Calendar.getInstance().getTime());
	}

//	private Calendar getLastNoCheat()
//	{
//		//Get the last time this button was clicked
//		File startDateFile = getBaseContext().getFileStreamPath("startDate");
//		Calendar lastClicked = Calendar.getInstance();
//		//Calendar currentDate = Calendar.getInstance();
//		if(startDateFile.exists()){
//			try {
//				lastClicked.setTime(dateFormat.parse(readSavedData("startDate")));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else{
//			try {
//				lastClicked.setTime(dateFormat.parse("2000/01/01"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		return lastClicked;
//	}
	

	//Is the last time the "I didn't cheat today" was clicked today?
	

}
