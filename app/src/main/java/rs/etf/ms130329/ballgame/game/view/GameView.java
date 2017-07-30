package rs.etf.ms130329.ballgame.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.TypedValue;
import android.view.View;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.model.objects.Polygon;

/**
 * Created by stevan on 7/30/17.
 */

public class GameView extends View{

    private Polygon polygon;

    public GameView(Context context, Polygon polygon) {
        super(context);
        this.polygon = polygon;


        TypedValue frictionFactor = new TypedValue();
        getResources().getValue(R.dimen.friction_factor, frictionFactor, true);

        TypedValue collisionFactor = new TypedValue();
        getResources().getValue(R.dimen.collision_factor, collisionFactor, true);

        polygon.getBall().setFactors(frictionFactor.getFloat(), collisionFactor.getFloat());

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        polygon.draw(canvas);
    }

    public void update(float[] s, float dT){
        polygon.getBall().accelerate(s[0], s[1], dT, polygon.getBox().getCollision(polygon.getBall()));
        invalidate();
    }

}
