package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.game.controller.BallStateObservable;

/**
 * Created by stevan on 8/7/17.
 */

public class WinningHoleCollision extends HoleCollision {

    @Override
    public void resolve(Ball ball, Acceleration acceleration, float dT, float frictionFactor, float collisionFactor) {
        BallStateObservable.getInstance().setInWinningHoleState();
    }
}
