package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.objects.Obstacle;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;

import static android.util.FloatMath.sqrt;
import static java.lang.Math.abs;

/**
 * Created by stevan on 7/31/17.
 */

public class ObstacleCollision extends Collision {
    //TODO: make it smart boy!
    public ObstacleCollision(float collisionFactor, Ball ball, Obstacle obstacle, Obstacle.CollisionState
            collisionState) {
        super(collisionFactor);
        Vector closest = new Vector(collisionState.closest.getPointX(), collisionState.closest.getPointY());

        normal = Vector.subtract(ball.getPosition(), closest).getUnitVector();

        if(abs(normal.getPointX()) == 1 && abs(normal.getPointY()) == 1) {
            //edges
            normal.setPointX(normal.getPointX() * 0.7f);
            normal.setPointY(normal.getPointY() * 0.7f);
        }

        penetration = ball.getRadius() - sqrt(collisionState.distanceSquared);
    }
    //TO SHOW OFF: it works yo!
}
