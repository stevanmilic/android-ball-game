package rs.etf.ms130329.ballgame.model.drawables;

import android.graphics.Rect;

import rs.etf.ms130329.ballgame.model.physics.geometry.Point;

/**
 * Created by stevan on 7/27/17.
 */

public abstract class Figure extends ExtendedDrawable {

    static final long serialVersionUID = 3L;

    protected Point point;

    Figure(int color, float x, float y) {
        super(color);
        point = new Point(x, y);
    }

    public boolean isIntersecting(Figure figure) {
        return Rect.intersects(getBounds(), figure.getBounds());
    }

}
