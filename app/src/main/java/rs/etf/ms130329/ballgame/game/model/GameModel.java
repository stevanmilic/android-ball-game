package rs.etf.ms130329.ballgame.game.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.database.ScoreContract.*;
import rs.etf.ms130329.ballgame.database.ScoreDbHelper;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.settings.SettingsActivity;

/**
 * Created by stevan on 7/30/17.
 */

public class GameModel {

    private ScoreDbHelper scoreDbHelper;

    public GameModel(Context context, Polygon polygon) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        float frictionFactor = Float.parseFloat(sharedPreferences.getString(SettingsActivity.KEY_PREF_FRICTION_FACTOR,
                context.getResources().getString(R.string.friction_factor_default)));
        float collisionFactor = Float.parseFloat(sharedPreferences.getString(SettingsActivity.KEY_PREF_COLLISION_FACTOR,
                context.getResources().getString(R.string.collision_factor_default)));

        polygon.setFrictionFactor(frictionFactor);
        polygon.setCollisionFactor(collisionFactor);

        scoreDbHelper = new ScoreDbHelper(context);
    }

    public long insertScoreEntry(String polygonName, double time, String playerName) {
        SQLiteDatabase database = scoreDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoreEntry.COLUMN_NAME_POLYGON_NAME, polygonName);
        values.put(ScoreEntry.COLUMN_NAME_TIME, time);
        values.put(ScoreEntry.COLUMN_NAME_PLAYER_NAME, playerName);

        long rowId = database.insert(ScoreEntry.TABLE_NAME, null, values);

        return rowId;
    }

}
