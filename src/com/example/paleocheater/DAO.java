package com.example.paleocheater;




import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



public class DAO {
	private String filname;
	private byte[] data;
	private SQLiteDatabase database;
	private BaseQuery baseQuery;


	private String[] cheatColumns = {Cheat.ID_COLUMN, Cheat.CHEATDATE_COLUMN, Cheat.CHEAT_COLUMN,
			Cheat.POINTS_COLUMN, Cheat.ISCHEAT_COLUMN};


	public DAO(Context context){
		baseQuery = new BaseQuery(context);
	}

	public void open() throws SQLException{
		database = baseQuery.getWritableDatabase(); //It will be interesting to see how this works


	}
	public void close(){
		baseQuery.close();
	}

	public Cheat createCheat(String cheatDate, String cheat, int points, boolean isCheat){
		ContentValues values = new ContentValues();
		values.put(Cheat.CHEATDATE_COLUMN, cheatDate);
		values.put(Cheat.CHEAT_COLUMN, cheat);
		values.put(Cheat.POINTS_COLUMN, points);
		values.put(Cheat.ISCHEAT_COLUMN, isCheat);

		long insertId = database.insert(Cheat.TABLE_NAME, null, values);
		Cursor cursor = database.query(Cheat.TABLE_NAME, cheatColumns, Cheat.ID_COLUMN + " = " + insertId, null, 
				null,null,null);
		cursor.moveToFirst();

		Cheat newCheat = cursorToCheat(cursor);
		cursor.close();
		return newCheat;
	}


	public void deleteCheat(Cheat cheat){
		long id = cheat.getId();
		System.out.println("Cheat deleted with id: " + id);
		database.delete(Cheat.TABLE_NAME, Cheat.ID_COLUMN
				+ " = " + id, null);
	}

	public void deleteAllCheats(){
		database.delete(Cheat.TABLE_NAME, null, null);
	}

	public Cheat getCheatById(long id){

		Cursor cursor = database.query(Cheat.TABLE_NAME, cheatColumns, cheatColumns[0] + " = " + id, null, null, null, null, null);
		cursor.moveToFirst();

		Cheat cheat = new Cheat();
		if(!cursor.isAfterLast()){
			cheat = cursorToCheat(cursor);
		}

		return cheat;
	}

	private Cheat cursorToCheat(Cursor cursor){
		Cheat cheat = new Cheat();
		cheat.setId(cursor.getLong(0));
		cheat.setCheatDate(cursor.getString(1));
		cheat.setCheat(cursor.getString(2));
		cheat.setPoints(cursor.getInt(3));
		if(cursor.getInt(4)>0){
			cheat.setCheat(true);
		}else{
			cheat.setCheat(false);
		}
		//cheat.setCheat(Boolean.getBoolean(cursor.getInt(4)));
		return cheat;
	}


	//Search for a cheat by any String parameter.  Returns List that matches.
	public List<Cheat> searchCheats(String search, String column){
		List<Cheat> cheats = new ArrayList<Cheat>();

		for (int i = 0; i<cheatColumns.length; i++){
			if(column.equalsIgnoreCase(cheatColumns[i])){
				Cursor cursor = database.query(Cheat.TABLE_NAME, cheatColumns, cheatColumns[i] + " LIKE " + search, null, null, null, null, null);
				cursor.moveToFirst();
				//cursor.moveToFirst();
				while (!cursor.isAfterLast()){
					Cheat cheat = cursorToCheat(cursor);
					cheats.add(cheat);
					cursor.moveToNext();
				}
				cursor.close();
			}
		}
		return cheats;
	}

	//Search for a cheat by any String parameter.  Returns List that matches.
	public List<Cheat> searchCheats2(String[] columns, String[] values){
		List<Cheat> cheats = new ArrayList<Cheat>();
		Cheat cheat;

		//Calling this where clause statically - this might not actually work
		String where = Cheat.whereClause(columns, values);

		Cursor cursor = database.query(Cheat.TABLE_NAME, cheatColumns, where, null, null, null, Cheat.CHEAT_COLUMN, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			cheat = cursorToCheat(cursor);
			cheats.add(cheat);
			cursor.moveToNext();
		}
		cursor.close();

		return cheats;
	}

	//Returns all the records in the Cheat table
	public List<Cheat> getAllRecords() {
		List<Cheat> cheats = new ArrayList<Cheat>();

		Cursor cursor = database.query(Cheat.TABLE_NAME,
				cheatColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Cheat cheat = cursorToCheat(cursor);
			cheats.add(cheat);
			cursor.moveToNext();
		}
		cursor.close();
		return cheats;
	}



	public Calendar getLastNoCheat(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar lastClicked = Calendar.getInstance();

		Cursor cursor = database.query(Cheat.TABLE_NAME, cheatColumns, null, null, null, null, Cheat.CHEATDATE_COLUMN + " DESC", "1");
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			try {
				lastClicked.setTime(dateFormat.parse(cursor.getString(1)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			try {
				lastClicked.setTime(dateFormat.parse("2000/01/01"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return lastClicked;
	}

	//Gets number of records in Cheat table that have the boolean "ischeat" set to true
	public int getTimesCheated(){
		List<Cheat> cheats = new ArrayList<Cheat>();
		cheats = getAllRecords();
		int numCheats = 0;
		if(!cheats.isEmpty()){
			for (int i=0; i<cheats.size(); i++){
				if(cheats.get(i).isCheat()){
					numCheats++;
				}
			}
		}
		return numCheats;
	}
	
	public int getPoints(){
		List<Cheat> cheats = new ArrayList<Cheat>();
		cheats = getAllRecords();
		int points = 100;
		if(!cheats.isEmpty()){
			for (int i=0; i<cheats.size(); i++){
				if(cheats.get(i).isCheat()){
					points = points - cheats.get(i).getPoints();
				}
				else{
					points = points + cheats.get(i).getPoints();
				}
			}
		}
		if(points<0) points = 0;
		return points;
	}
	
	public boolean noCheatClickedToday(){
		Calendar currentDate = Calendar.getInstance();
		Calendar lastClicked = getLastNoCheat();
		//Calendar lastClicked = getLastNoCheat();

		if(lastClicked.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR))
		{
			//If the last time the button was clicked is today, return true. 
			return true;
		}else{
			return false;
		}
	}
	
}