package rs.etf.ms130329.ballgame.engine.objects;

import rs.etf.ms130329.ballgame.engine.physics.collision.HoleCollision;
import rs.etf.ms130329.ballgame.engine.physics.collision.WinningHoleCollision;

/**
 * Created by stevan on 7/27/17.
 */

public class WinningHole extends Hole {

    static final long serialVersionUID = 12L;

    public WinningHole(int color, float x, float y, int radius) {
        super(color, x, y, radius);
    }

    @Override
    protected float getCollisionRadius(float ballRadius) {
        return -ballRadius;
    }

    @Override
    protected HoleCollision getHoleCollision() {
        return new WinningHoleCollision();
    }
}
