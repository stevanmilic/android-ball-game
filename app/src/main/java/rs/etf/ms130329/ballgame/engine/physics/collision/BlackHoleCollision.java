package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.game.controller.BallStateObservable;

/**
 * Created by stevan on 8/7/17.
 */

public class BlackHoleCollision extends HoleCollision {

    @Override
    public void resolve(BallStateObservable ballState) {
        ballState.setInBlackHoleState();
    }
}
