package rs.etf.ms130329.ballgame.polygon.view.drawables;

import android.graphics.Rect;

/**
 * Created by stevan on 7/27/17.
 */

public abstract class Figure extends ExtendedDrawable {

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

    public boolean isIntersecting(Figure figure) {
        return Rect.intersects(getBounds(), figure.getBounds());
    }

}
