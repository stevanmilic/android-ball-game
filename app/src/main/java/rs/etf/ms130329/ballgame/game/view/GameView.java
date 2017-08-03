package rs.etf.ms130329.ballgame.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import rs.etf.ms130329.ballgame.engine.objects.BlackHole;
import rs.etf.ms130329.ballgame.engine.objects.Box;
import rs.etf.ms130329.ballgame.engine.objects.Hole;
import rs.etf.ms130329.ballgame.engine.objects.Obstacle;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.engine.physics.collision.BoxCollision;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.collision.ObstacleCollision;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.game.controller.GameController.GameState;

/**
 * Created by stevan on 7/30/17.
 */

@Deprecated
public class GameView extends View {

    private Polygon polygon;

    public GameView(Context context, Polygon polygon) {
        super(context);
        this.polygon = polygon;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public String getPolygonName() {
        return polygon.getName();
    }

    public void setBallToStartingPosition() {
        polygon.getBall().resetPosition();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        polygon.draw(canvas);
    }

    public GameState update(float[] s, float dT) {

        polygon.getWinningHole().setCollisionState(polygon.getBall());
        if (polygon.getWinningHole().getCollisionState() != Hole.CollisionState.NONE) {
            return GameState.WON;
        } else {
            for (BlackHole blackHole : polygon.getBlackHoles()) {
                blackHole.setCollisionState(polygon.getBall());
                if(blackHole.getCollisionState() != Hole.CollisionState.NONE) {
                    return GameState.LOST;
                }
            }
        }

        run(s, dT);

        return GameState.RUNNING;
    }

    public void run(float[] s, float dT) {
        Acceleration acceleration = new Acceleration(-s[0], -s[1]);
        Collision collision = null;

        polygon.getBox().setCollisionState(polygon.getBall());
        if (polygon.getBox().getCollisionState() != Box.CollisionState.NONE) {
            collision = new BoxCollision(polygon.getCollisionFactor(), polygon.getBall(), polygon.getBox());
        } else {
            for (Obstacle obstacle : polygon.getObstacles()) {
                obstacle.setCollisionState(polygon.getBall());
                if (obstacle.getCollisionState() != Obstacle.CollisionState.NONE) {
                    collision = new ObstacleCollision(polygon.getCollisionFactor(), polygon.getBall(), obstacle,
                            obstacle.getCollisionState());
                    break;
                }
            }
        }

        polygon.getBall().accelerate(acceleration, dT, polygon.getFrictionFactor(), collision);

        invalidate();
    }

}
