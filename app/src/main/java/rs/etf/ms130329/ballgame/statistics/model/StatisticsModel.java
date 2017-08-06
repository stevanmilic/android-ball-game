package rs.etf.ms130329.ballgame.statistics.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import rs.etf.ms130329.ballgame.database.ScoreContract.ScoreEntry;
import rs.etf.ms130329.ballgame.database.ScoreDbHelper;

/**
 * Created by stevan on 8/1/17.
 */

public class StatisticsModel {

    private ScoreDbHelper scoreDbHelper;

    public StatisticsModel(Context context) {
        scoreDbHelper = ScoreDbHelper.getInstance(context);
    }

    public Cursor getPolygonCursor() {
        SQLiteDatabase database = scoreDbHelper.getReadableDatabase();

        String[] columns = {ScoreEntry._ID, ScoreEntry.COLUMN_NAME_POLYGON_NAME};

        Cursor cursor = database.query(true, ScoreEntry.TABLE_NAME, columns, null, null, ScoreEntry.COLUMN_NAME_POLYGON_NAME,
                null, ScoreEntry._ID + " ASC", null);

        return cursor;
    }

    public Cursor getPolygonStatisticCursor(String polygonName) {
        SQLiteDatabase database = scoreDbHelper.getReadableDatabase();

        String[] columns = {ScoreEntry._ID, ScoreEntry.COLUMN_NAME_PLAYER_NAME, ScoreEntry.COLUMN_NAME_TIME};

        String whereClause = ScoreEntry.COLUMN_NAME_POLYGON_NAME + " = ?";
        String[] whereArgs = {polygonName};

        Cursor cursor = database.query(ScoreEntry.TABLE_NAME, columns, whereClause, whereArgs, null, null,
                ScoreEntry.COLUMN_NAME_TIME + " ASC");

        return cursor;
    }

    public void deleteStatistics() {
        SQLiteDatabase database = scoreDbHelper.getWritableDatabase();
        database.delete(ScoreEntry.TABLE_NAME, null, null);
    }

    public void deletePolygonStatistics(String polygonName) {
        SQLiteDatabase database = scoreDbHelper.getWritableDatabase();

        String whereClause = ScoreEntry.COLUMN_NAME_POLYGON_NAME + " = ?";
        String[] whereArgs = {polygonName};

        database.delete(ScoreEntry.TABLE_NAME, whereClause, whereArgs);
    }
}
