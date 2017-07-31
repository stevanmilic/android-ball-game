package rs.etf.ms130329.ballgame.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import rs.etf.ms130329.ballgame.model.objects.Box;
import rs.etf.ms130329.ballgame.model.objects.Polygon;
import rs.etf.ms130329.ballgame.model.physics.collision.BoxCollision;
import rs.etf.ms130329.ballgame.model.physics.collision.Collision;
import rs.etf.ms130329.ballgame.model.physics.motion.Acceleration;

/**
 * Created by stevan on 7/30/17.
 */

public class GameView extends View{

    private Polygon polygon;

    public GameView(Context context, Polygon polygon) {
        super(context);
        this.polygon = polygon;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        polygon.draw(canvas);
    }

    public void update(float[] s, float dT){
        Acceleration acceleration = new Acceleration(-s[0], -s[1]);
        Collision collision = null;
        polygon.getBox().setCollisionState(polygon.getBall());
        if(polygon.getBox().getCollisionState() != Box.CollisionState.NONE) {
            collision = new BoxCollision(polygon.getCollisionFactor(), polygon.getBall(), polygon.getBox());
        }
        polygon.getBall().accelerate(acceleration, dT, polygon.getFrictionFactor(), collision);
        invalidate();
    }

}
