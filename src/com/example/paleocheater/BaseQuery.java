package com.example.paleocheater;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseQuery extends SQLiteOpenHelper {

	//private Cheat cheat;
	
	
	public static final String DATABASE_NAME = "PaleoCheater.db";
	public static final int DATABASE_VERSION = 1;
	
	
	public BaseQuery(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		
		Cheat cheat = new Cheat();
		database.execSQL(Cheat.DATABASE_CREATE);
	}
	
	
	//Drop all tables
	//TODO figure out how to upgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.w(Cheat.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + Cheat.TABLE_NAME);
		onCreate(db);
	}
}

