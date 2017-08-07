package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;

import static java.lang.Math.sqrt;

/**
 * Created by stevan on 7/31/17.
 */

abstract public class BounceCollision implements Collision {

    Vector normal;
    private float penetration;

    BounceCollision(Point closest, Ball ball, float distanceSquared) {
        normal = Vector.getVectorBetweenPoints(ball.getPosition(), closest).getUnitVector();
        penetration = (float) (ball.getRadius() - sqrt(distanceSquared));
    }

    public Vector getNormal() {
        return normal;
    }

    public float getPenetration() {
        return penetration;
    }

}
