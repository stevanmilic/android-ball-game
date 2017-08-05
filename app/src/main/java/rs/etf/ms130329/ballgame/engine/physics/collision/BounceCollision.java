package rs.etf.ms130329.ballgame.engine.physics.collision;

import android.annotation.SuppressLint;
import android.os.SystemClock;

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

    //TODO: Should be set from GameController(GAME_DELAY)
    private static final long DELAY = 200;

    private static final String TAG = "BounceCollision";

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

    boolean isBallGlued(Point closest, Point lastClosestPoint, long lastClosestEncounterTime) {
        if (lastClosestPoint != null && (lastClosestPoint.getPointX() == closest.getPointX()
                || lastClosestPoint.getPointY() == closest.getPointY())) {
            long lastClosestEncounter = SystemClock.elapsedRealtime() - lastClosestEncounterTime;
            return lastClosestEncounter <= DELAY;
        } else {
            return false;
        }

    }

}
