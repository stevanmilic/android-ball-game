package rs.etf.ms130329.ballgame.model.physics.motion;

import rs.etf.ms130329.ballgame.model.physics.collision.Collision;
import rs.etf.ms130329.ballgame.model.physics.geometry.Vector;

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

    public void changeDueToCollision(Collision collision) {
        float velocityAlongNormal = dotProduct(collision.getNormal());

        if (velocityAlongNormal > 0) {
            return;
        }

        float j = -(1 + collision.getCollisionFactor()) * velocityAlongNormal;

        Vector impulse = Vector.multiply(collision.getNormal(), j);

        add(impulse);
    }
}
