package rs.etf.ms130329.ballgame.polygon.view.drawables;

/**
 * Created by stevan on 7/27/17.
 */

abstract class Figure extends ColoredDrawable {

    float x;
    float y;

    Figure(int color, float x, float y) {
        super(color);
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
