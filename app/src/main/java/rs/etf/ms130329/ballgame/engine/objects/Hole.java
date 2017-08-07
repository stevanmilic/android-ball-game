package rs.etf.ms130329.ballgame.engine.objects;

import java.util.List;

import rs.etf.ms130329.ballgame.engine.drawables.Circle;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.collision.HoleCollision;

/**
 * Created by stevan on 7/27/17.
 */

abstract class Hole extends Circle implements Collidable {

    static final long serialVersionUID = 7L;

    Hole(int color, float x, float y, int radius) {
        super(color, x, y, radius);
    }

    protected abstract float getCollisionRadius(float ballRadius);

    protected abstract HoleCollision getHoleCollision();

    @Override
    public void detectCollisions(List<Collision> collisionList, Ball ball) {
        float distanceToBall = point.getDistanceSquared(ball.getPosition());
        float radiusSum = radius + getCollisionRadius(ball.getRadius());
        if (distanceToBall <= radiusSum * radiusSum) {
            collisionList.add(getHoleCollision());
        }
    }

}
