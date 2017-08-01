package rs.etf.ms130329.ballgame.engine.objects;

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
}
