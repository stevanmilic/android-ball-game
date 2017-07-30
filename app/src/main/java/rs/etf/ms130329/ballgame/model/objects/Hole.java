package rs.etf.ms130329.ballgame.model.objects;

import rs.etf.ms130329.ballgame.model.drawables.Circle;

/**
 * Created by stevan on 7/27/17.
 */

class Hole extends Circle {

    static final long serialVersionUID = 7L;

    Hole(int color, float x, float y, int radius) {
        super(color, x, y, radius);
    }
}
