package rs.etf.ms130329.ballgame.game.model;

import android.content.Context;
import android.util.TypedValue;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;

/**
 * Created by stevan on 7/30/17.
 */

public class GameModel {

    public GameModel(Context context, Polygon polygon) {

        TypedValue frictionFactor = new TypedValue();
        context.getResources().getValue(R.dimen.friction_factor, frictionFactor, true);

        TypedValue collisionFactor = new TypedValue();
        context.getResources().getValue(R.dimen.collision_factor, collisionFactor, true);

        polygon.setFrictionFactor(frictionFactor.getFloat());
        polygon.setCollisionFactor(collisionFactor.getFloat());
    }
}
