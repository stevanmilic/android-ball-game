package rs.etf.ms130329.ballgame.model.drawables;

import android.graphics.Rect;

/**
 * Created by stevan on 7/27/17.
 */

public abstract class Figure extends ExtendedDrawable {

    static final long serialVersionUID = 3L;

    protected float x;
    protected float y;

    Figure(int color, float x, float y) {
        super(color);
        this.x = x;
        this.y = y;
    }

    public boolean isIntersecting(Figure figure) {
        return Rect.intersects(getBounds(), figure.getBounds());
    }

}
