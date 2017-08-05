package rs.etf.ms130329.ballgame.engine.objects;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.util.List;

import rs.etf.ms130329.ballgame.engine.drawables.Circle;
import rs.etf.ms130329.ballgame.engine.physics.collision.BounceCollision;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.engine.physics.motion.Position;
import rs.etf.ms130329.ballgame.engine.physics.motion.Velocity;

/**
 * Created by stevan on 7/27/17.
 */

public class Ball extends Circle {

    static final long serialVersionUID = 7L;

    private Position position;
    private Velocity velocity;

    public Ball(int color, float x, float y, int radius) {
        super(color, x, y, radius);
        position = new Position(point);
        velocity = new Velocity(0, 0);
    }

    public void resetPosition() {
        position.setPointX(point.getPointX());
        position.setPointY(point.getPointY());
        velocity = new Velocity(0, 0);
    }

    public Position getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void accelerate(Acceleration acceleration, float dT, List<Collision> collisionList,
                           float frictionFactor, float collisionFactor) {

        velocity.changeDueToAcceleration(acceleration, dT, frictionFactor);

        if (!collisionList.isEmpty()) {
            for (Collision collision : collisionList) {
                velocity.changeDueToCollision((BounceCollision) collision, collisionFactor);
            }
        }

        position.verletIntegration(velocity, acceleration, dT);

        if (!collisionList.isEmpty()) {
            for (Collision collision : collisionList) {
                position.correction((BounceCollision) collision);
            }
        }

        super.setCircleBounds(position.getPointX(), position.getPointY(), radius);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(position.getPointX(), position.getPointY(), radius, paint);
    }
}
