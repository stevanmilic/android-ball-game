package rs.etf.ms130329.ballgame.model.physics.collision;

import android.graphics.Rect;

import rs.etf.ms130329.ballgame.model.objects.Ball;
import rs.etf.ms130329.ballgame.model.objects.Box;
import rs.etf.ms130329.ballgame.model.physics.geometry.Vector;
import rs.etf.ms130329.ballgame.model.physics.motion.Position;

/**
 * Created by stevan on 7/31/17.
 */

public class BoxCollision extends Collision{

    public BoxCollision(float collisionFactor, Ball ball, Box box) {
        super(collisionFactor);

        Box.CollisionState collisionState = box.getCollisionState();

        Rect boxRect = box.getBounds();
        Position ballPosition = ball.getPosition();

        Vector closest = null;

        switch(collisionState){
            case LEFT:
                closest = new Vector(boxRect.left, ballPosition.getPointY());
                normal = new Vector(1 , 0) ;
                break;
            case TOP:
                closest = new Vector(ballPosition.getPointX(), boxRect.top);
                normal = new Vector(0 , 1) ;
                break;
            case RIGHT:
                closest = new Vector(boxRect.right, ballPosition.getPointY());
                normal = new Vector(-1 , 0) ;
                break;
            case BOTTOM:
                normal = new Vector(0 , -1) ;
                closest = new Vector(ballPosition.getPointX(), boxRect.bottom);
        }

        penetration = closest.getDistance(ballPosition);

    }
}
