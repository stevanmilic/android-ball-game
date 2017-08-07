package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.game.controller.BallStateObservable;

/**
 * Created by stevan on 8/5/17.
 */

public interface Collision {

    void resolve(BallStateObservable ballState);
}
