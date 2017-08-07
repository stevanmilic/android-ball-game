package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;

/**
 * Created by stevan on 8/5/17.
 */

public interface Collision {

    void resolve(Ball ball, Acceleration acceleration, float dT, float frictionFactor, float collisionFactor);
}
