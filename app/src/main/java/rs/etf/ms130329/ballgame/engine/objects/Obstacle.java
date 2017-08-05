package rs.etf.ms130329.ballgame.engine.objects;


import java.util.List;

import rs.etf.ms130329.ballgame.engine.drawables.Rectangle;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.collision.ObstacleBounceCollision;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 7/27/17.
 */

public class Obstacle extends Rectangle implements Collidable{

    static final long serialVersionUID = 8L;

    public Obstacle(int color, float x, float y, float width, float height) {
        super(color, x, y, width, height);
    }

    @Override
    public void detectCollisions(List<Collision> collisionList, Ball ball) {
        Point closest = Point.clampPoint(ball.getPosition(), left, right, top, bottom);
        float distanceSquared = closest.getDistanceSquared(ball.getPosition());

        if (distanceSquared < ball.getRadius() * ball.getRadius()) {
            collisionList.add(new ObstacleBounceCollision(closest, ball, distanceSquared));
        }
    }
}
