package com.example.paleocheater;


import android.content.Context;



public class Cheat {
	public static final String TABLE_NAME = "Cheat";
	public static final String CHEATDATE_COLUMN = "CheatDate" ;
	public static final String CHEAT_COLUMN = "Cheat";
	public static final String POINTS_COLUMN = "Points";
	public static final String ISCHEAT_COLUMN = "isCheat";
	public static final String ID_COLUMN = "_ID";
	
	private long id;
	private String cheatDate;
	private String cheat;
	private int points;
	private boolean isCheat;
		
	public Cheat(String cheatDate, String cheat, int points,
			boolean isCheat) {
		super();
		this.cheatDate = cheatDate;
		this.cheat = cheat;
		this.points = points;
		this.isCheat = isCheat;
	}

	public Cheat() {
		super();
		cheatDate = null;
		cheat = null;
		points = 0;
		isCheat = false;
	}
	
	public static final String DATABASE_CREATE = 
			"create table "
			+ TABLE_NAME + "(" 
			+ ID_COLUMN + " INTEGER primary key autoincrement, " 
			+ CHEATDATE_COLUMN + " DATETIME, "
			+ CHEAT_COLUMN + " TEXT, "
			+ POINTS_COLUMN + " INTEGER, "
			+ ISCHEAT_COLUMN + " BOOLEAN" 
			+ ");";
	
	//This method is "static".  I'm not sure if that will actually work...
	public static String whereClause(String[] columns, String[] values){
		String where = "";
		for (int i = 0; i < columns.length; i++){
			if(i!=0);
				where = where + " OR ";
			where = where + columns[i] + " LIKE " + values[i];
		}
		return where;
	}

	public String getCheatDate() {
		return cheatDate;
	}

	public void setCheatDate(String cheatDate) {
		this.cheatDate = cheatDate;
	}

	public String getCheat() {
		return cheat;
	}

	public void setCheat(String cheat) {
		this.cheat = cheat;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isCheat() {
		return isCheat;
	}

	public void setCheat(boolean isCheat) {
		this.isCheat = isCheat;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	//TODO this is empty.
	public void create(){
		
	}
	
	//TODO this doesn't do anything.
	public void update(Context context, int rowId){
		
		String query = "Update table " + TABLE_NAME + " SET " + CHEATDATE_COLUMN + "=" + cheatDate + ", " + POINTS_COLUMN + "=" + points + ", " +
				ISCHEAT_COLUMN + "=" + isCheat + " WHERE" + ID_COLUMN + "=" + rowId; 
		DBHelper helper = new DBHelper(context);
		
	}
	
}
