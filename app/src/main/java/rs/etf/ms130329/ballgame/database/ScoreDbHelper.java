package rs.etf.ms130329.ballgame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rs.etf.ms130329.ballgame.database.ScoreContract.ScoreEntry;

/**
 * Created by stevan on 8/1/17.
 */

public class ScoreDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Score.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScoreEntry.TABLE_NAME + "(" +
                    ScoreEntry._ID + " INTEGER PRIMARY KEY," +
                    ScoreEntry.COLUMN_NAME_POLYGON_NAME + " TEXT," +
                    ScoreEntry.COLUMN_NAME_TIME + " REAL," +
                    ScoreEntry.COLUMN_NAME_PLAYER_NAME + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreEntry.TABLE_NAME;

    public ScoreDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
