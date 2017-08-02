package rs.etf.ms130329.ballgame.game.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.TypedValue;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.database.ScoreContract.*;
import rs.etf.ms130329.ballgame.database.ScoreDbHelper;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;

/**
 * Created by stevan on 7/30/17.
 */

public class GameModel {

    private ScoreDbHelper scoreDbHelper;

    public GameModel(Context context, Polygon polygon) {

        TypedValue frictionFactor = new TypedValue();
        context.getResources().getValue(R.dimen.friction_factor, frictionFactor, true);

        TypedValue collisionFactor = new TypedValue();
        context.getResources().getValue(R.dimen.collision_factor, collisionFactor, true);

        polygon.setFrictionFactor(frictionFactor.getFloat());
        polygon.setCollisionFactor(collisionFactor.getFloat());

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
