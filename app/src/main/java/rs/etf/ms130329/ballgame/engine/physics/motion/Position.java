package rs.etf.ms130329.ballgame.engine.physics.motion;

import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;

import static java.lang.Math.max;

/**
 * Created by stevan on 7/31/17.
 */

public class Position extends Vector {

    static final long serialVersionUID = 15L;

    public Position(float pointX, float pointY) {
        super(pointX, pointY);
    }

    public Position(Point point) {
        super(point.getPointX(), point.getPointY());
    }

    public void verletIntegration(Velocity velocity, Acceleration acceleration, float dT) {
        pointX += velocity.getPointX() * dT + acceleration.getPointX() * dT * dT / 2;
        pointY += velocity.getPointY() * dT + acceleration.getPointY() * dT * dT / 2;
    }

    public void correction(Collision collision) {
        final float percent = 0.2f;
        final float slop = 0.01f;

        Vector correction = Vector.multiply(collision.getNormal(), max(collision.getPenetration() - slop, 0.0f) * percent);

        add(correction);
    }
}
