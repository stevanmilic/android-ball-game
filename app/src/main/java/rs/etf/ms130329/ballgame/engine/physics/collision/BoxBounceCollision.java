package rs.etf.ms130329.ballgame.engine.physics.collision;

import android.os.SystemClock;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 7/31/17.
 */

public class BoxBounceCollision extends BounceCollision {

    private static Point lastClosestPoint;
    private static long lastClosestEncounterTime = 0;

    private static boolean ballGlued = false;

    public BoxBounceCollision(Point closest, Ball ball, float distanceSquared) {
        super(closest, ball, distanceSquared);

        ballGlued = isBallGlued(closest, lastClosestPoint, lastClosestEncounterTime);

        lastClosestPoint = closest;
        lastClosestEncounterTime = SystemClock.elapsedRealtime();
    }

    public static boolean isBallGlued() {
        return ballGlued;
    }
}
