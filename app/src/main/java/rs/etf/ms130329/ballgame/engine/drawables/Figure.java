package rs.etf.ms130329.ballgame.engine.drawables;

import android.graphics.Rect;

import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

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

    public Point getPoint() {
        return point;
    }

    public boolean isIntersecting(Figure figure) {
        return Rect.intersects(getBounds(), figure.getBounds());
    }

}
