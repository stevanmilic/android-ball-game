package rs.etf.ms130329.ballgame.engine.physics.collision;

import android.graphics.Rect;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.objects.Box;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;
import rs.etf.ms130329.ballgame.engine.physics.motion.Position;

/**
 * Created by stevan on 7/31/17.
 */

public class BoxCollision extends Collision{

    public BoxCollision(float collisionFactor, Ball ball, Box box) {
        super(collisionFactor);

        Box.CollisionState collisionState = box.getCollisionState();

        Rect boxRect = box.getBounds();
        Position ballPosition = ball.getPosition();

        Point closest = null;

        switch(collisionState){
            case LEFT:
                closest = new Point(boxRect.left, ballPosition.getPointY());
                normal = new Vector(1 , 0) ;
                break;
            case TOP:
                closest = new Point(ballPosition.getPointX(), boxRect.top);
                normal = new Vector(0 , 1) ;
                break;
            case RIGHT:
                closest = new Point(boxRect.right, ballPosition.getPointY());
                normal = new Vector(-1 , 0) ;
                break;
            case BOTTOM:
                closest = new Point(ballPosition.getPointX(), boxRect.bottom);
                normal = new Vector(0 , -1) ;
        }

        penetration = closest.getDistance(ballPosition);

    }
}
