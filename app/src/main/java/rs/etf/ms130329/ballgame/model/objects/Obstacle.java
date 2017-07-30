package rs.etf.ms130329.ballgame.model.objects;


import rs.etf.ms130329.ballgame.model.drawables.Rectangle;

/**
 * Created by stevan on 7/27/17.
 */

public class Obstacle extends Rectangle {

    static final long serialVersionUID = 8L;

    public Obstacle(int color, float x, float y, float width, float height) {
        super(color, x, y, width, height);
    }
}
