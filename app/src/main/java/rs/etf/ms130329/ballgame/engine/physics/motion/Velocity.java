package rs.etf.ms130329.ballgame.engine.physics.motion;

import rs.etf.ms130329.ballgame.engine.physics.collision.BounceCollision;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;

/**
 * Created by stevan on 7/31/17.
 */

public class Velocity extends Vector {

    static final long serialVersionUID = 16L;

    public Velocity(float pointX, float pointY) {
        super(pointX, pointY);
    }

    public void changeDueToAcceleration(Acceleration acceleration, float dT, float frictionFactor) {
        pointX = pointX + acceleration.getPointX() * dT * frictionFactor;
        pointY = pointY + acceleration.getPointY() * dT * frictionFactor;
    }

    public void changeDueToCollision(BounceCollision bounceCollision, float collisionFactor) {
        float velocityAlongNormal = dotProduct(bounceCollision.getNormal());

        if (velocityAlongNormal > 0) {
            return;
        }

        float j = -(1 + collisionFactor) * velocityAlongNormal;

        Vector impulse = Vector.multiply(bounceCollision.getNormal(), j);

        add(impulse);
    }
}
