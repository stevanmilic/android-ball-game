package rs.etf.ms130329.ballgame.model.objects;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import rs.etf.ms130329.ballgame.model.drawables.Circle;
import rs.etf.ms130329.ballgame.model.physics.collision.Collision;
import rs.etf.ms130329.ballgame.model.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.model.physics.motion.Position;
import rs.etf.ms130329.ballgame.model.physics.motion.Velocity;

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

    public Position getPosition() {
        return position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void accelerate(Acceleration acceleration, float dT, float frictionFactor, Collision collision) {

        velocity.changeDueToAcceleration(acceleration, dT, frictionFactor);

        if(collision != null) {
            velocity.changeDueToCollision(collision);
        }

        position.verletIntegration(velocity, acceleration, dT);

        if(collision != null) {
            position.correction(collision);
        }

        super.setCircleBounds(position.getPointX(), position.getPointY(), radius);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(position.getPointX(), position.getPointY(), radius, paint);
    }
}