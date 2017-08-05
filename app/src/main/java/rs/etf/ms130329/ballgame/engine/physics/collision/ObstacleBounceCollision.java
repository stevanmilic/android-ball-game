package rs.etf.ms130329.ballgame.engine.physics.collision;

import android.os.SystemClock;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

import static java.lang.Math.abs;

/**
 * Created by stevan on 7/31/17.
 */

public class ObstacleBounceCollision extends BounceCollision {

    private static Point lastClosestPoint;
    private static long lastClosestEncounterTime = 0;

    private static boolean ballGlued = false;

    public ObstacleBounceCollision(Point closest, Ball ball, float distanceSquared) {
        super(closest, ball, distanceSquared);

        ballGlued = isBallGlued(closest, lastClosestPoint, lastClosestEncounterTime);

        lastClosestPoint = closest;
        lastClosestEncounterTime = SystemClock.elapsedRealtime();

        if(abs(normal.getPointX()) == 1 && abs(normal.getPointY()) == 1) {
            //edges
            normal.setPointX(normal.getPointX() * 0.7f);
            normal.setPointY(normal.getPointY() * 0.7f);
        }
    }

    public static boolean isBallGlued() {
        return ballGlued;
    }
}
