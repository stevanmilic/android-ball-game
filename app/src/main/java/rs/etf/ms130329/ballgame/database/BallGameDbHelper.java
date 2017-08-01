package rs.etf.ms130329.ballgame.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stevan on 8/1/17.
 */

public class BallGameDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BallGame.db";

    private static final String SQL_CREATE_SCORES = "...";

    private static final String SQL_CREATE_PREFERENCES = "...";

    public BallGameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCORES);
        db.execSQL(SQL_CREATE_PREFERENCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
