package rs.etf.ms130329.ballgame.engine.physics.collision;

import android.annotation.SuppressLint;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;

import static android.util.FloatMath.sqrt;

/**
 * Created by stevan on 7/31/17.
 */

public class BounceCollision extends Collision {

    Vector normal;
    private float penetration;

    @SuppressLint("FloatMath")
    BounceCollision(Point closest, Ball ball, float distanceSquared) {
        normal = Vector.getVectorBetweenPoints(ball.getPosition(), closest).getUnitVector();
        penetration = ball.getRadius() - sqrt(distanceSquared);
    }

    public Vector getNormal() {
        return normal;
    }

    public float getPenetration() {
        return penetration;
    }

}
