package rs.etf.ms130329.ballgame.engine.objects;

/**
 * Created by stevan on 7/27/17.
 */

public class BlackHole extends Hole {

    static final long serialVersionUID = 11L;

    public BlackHole(int color, float x, float y, int radius) {
        super(color, x, y, radius);
    }

    @Override
    protected float getCollisionRadius(float ballRadius) {
        return 0;
    }
}
