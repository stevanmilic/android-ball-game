package rs.etf.ms130329.ballgame.engine.objects;

import rs.etf.ms130329.ballgame.engine.drawables.Circle;

/**
 * Created by stevan on 7/27/17.
 */

abstract public class Hole extends Circle {

    public enum CollisionState {
        EXISTS,
        NONE
    }

    private CollisionState collisionState;

    static final long serialVersionUID = 7L;

    Hole(int color, float x, float y, int radius) {
        super(color, x, y, radius);
    }

    protected abstract float getCollisionRadius(float ballRadius);

    public void setCollisionState(Ball ball) {
        float distanceToBall = point.getDistanceSquared(ball.getPosition());
        float radiusSum = radius + getCollisionRadius(ball.getRadius());
        if(distanceToBall <= radiusSum*radiusSum) {
            collisionState = CollisionState.EXISTS;
        } else {
            collisionState = CollisionState.NONE;
        }
    }

    public CollisionState getCollisionState() {
        return collisionState;
    }
}
