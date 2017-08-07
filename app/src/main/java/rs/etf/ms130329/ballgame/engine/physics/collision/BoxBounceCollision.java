package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.game.controller.BallStateObservable;

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

    @Override
    public void resolve(Ball ball, Acceleration acceleration, float dT, float frictionFactor, float collisionFactor) {
        BallStateObservable.getInstance().setCollisionBoxState(collisionMap.isBallGlued(boundId));
        ball.accelerate(acceleration, dT, frictionFactor, collisionFactor, this);
    }
}
