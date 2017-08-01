package rs.etf.ms130329.ballgame.engine.objects;


import rs.etf.ms130329.ballgame.engine.drawables.Rectangle;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 7/27/17.
 */

public class Obstacle extends Rectangle {

    public enum CollisionState {
        EXISTS,
        NONE;

        public Point closest;
        public float distanceSquared;
    }

    private CollisionState collisionState;

    static final long serialVersionUID = 8L;

    public Obstacle(int color, float x, float y, float width, float height) {
        super(color, x, y, width, height);
    }

    public void setCollisionState(Ball ball) {
        Point closest = Point.clampPoint(ball.getPosition(), left, right, top, bottom);
        float distanceSquared = closest.getDistanceSquared(ball.getPosition());

        if (distanceSquared < ball.getRadius() * ball.getRadius()) {
            collisionState = CollisionState.EXISTS;
            collisionState.closest = closest;
            collisionState.distanceSquared = distanceSquared;
        } else {
            collisionState = CollisionState.NONE;
        }

    }

    public CollisionState getCollisionState() {
        return collisionState;
    }
}
