package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.game.controller.BallStateObservable;

import static java.lang.Math.abs;

/**
 * Created by stevan on 7/31/17.
 */

public class ObstacleBounceCollision extends BounceCollision {

    private int obstacleId;

    private static CollisionMap collisionMap;

    public ObstacleBounceCollision(Point closest, Ball ball, float distanceSquared, int obstacleId) {
        super(closest, ball, distanceSquared);

        this.obstacleId = obstacleId;

        if(collisionMap == null){
            collisionMap = new CollisionMap();
        }

        collisionMap.newEncounter(closest, obstacleId);

        if(abs(normal.getPointX()) == 1 && abs(normal.getPointY()) == 1) {
            //edges
            normal.setPointX(normal.getPointX() * 0.7f);
            normal.setPointY(normal.getPointY() * 0.7f);
        }
    }

    @Override
    public void resolve(BallStateObservable ballState) {
        if(!collisionMap.isBallGlued(obstacleId)) {
            ballState.setCollisionObstacleState();
        }
        ballState.setRunningState(false);
    }
}
