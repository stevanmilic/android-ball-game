package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 7/31/17.
 */

public class BoxBounceCollision extends BounceCollision {

    private int boundId;

    private static CollisionMap collisionMap;

    public BoxBounceCollision(Point closest, Ball ball, float distanceSquared, int boundId) {
        super(closest, ball, distanceSquared);

        this.boundId = boundId;

        if(collisionMap == null) {
            collisionMap = new CollisionMap();
        }

        collisionMap.newEncounter(closest, boundId);
    }

    public boolean isBallGlued() {
        return collisionMap.isBallGlued(boundId);
    }

}
