package rs.etf.ms130329.ballgame.engine.objects;

import android.graphics.Rect;

import java.util.List;

import rs.etf.ms130329.ballgame.engine.drawables.Background;
import rs.etf.ms130329.ballgame.engine.physics.collision.BoxBounceCollision;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 7/27/17.
 */

public class Box extends Background implements Collidable{

    static final long serialVersionUID = 9L;

    public final int HORIZONTAL_ID = 0;
    public final int VERTICAL_ID = 1;

    public Box(int color, int width, int height) {
        super(color, width, height);
    }

    @Override
    public void detectCollisions(List<Collision> collisionList, Ball ball) {
        Rect boxBounds = getBounds();
        Rect ballBounds = ball.getBounds();

        Point closest = null;

        //closest to x
        if (ballBounds.left <= boxBounds.left) {
            closest = new Point(boxBounds.left, ball.getPosition().getPointY());
        } else if (ballBounds.right >= boxBounds.right) {
            closest = new Point(boxBounds.right, ball.getPosition().getPointY());
        }

        if (closest != null) {
            float distanceSquared = closest.getDistanceSquared(ball.getPosition());
            collisionList.add(new BoxBounceCollision(closest, ball, distanceSquared, HORIZONTAL_ID));
        }

        closest = null;

        //closest to y
        if (ballBounds.top <= boxBounds.top) {
            closest = new Point(ball.getPosition().getPointX(), boxBounds.top);
        } else if (ballBounds.bottom >= boxBounds.bottom) {
            closest = new Point(ball.getPosition().getPointX(), boxBounds.bottom);
        }

        if (closest != null) {
            float distanceSquared = closest.getDistanceSquared(ball.getPosition());
            collisionList.add(new BoxBounceCollision(closest, ball, distanceSquared, VERTICAL_ID));
        }
    }

}
