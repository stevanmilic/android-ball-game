package rs.etf.ms130329.ballgame.model.objects;

import android.graphics.Rect;

import rs.etf.ms130329.ballgame.model.drawables.Background;
import rs.etf.ms130329.ballgame.model.drawables.Figure;

/**
 * Created by stevan on 7/27/17.
 */

public class Box extends Background {

    static final long serialVersionUID = 9L;

    enum CollisionState {
        NONE,
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }

    public Box(int color, int width, int height) {
        super(color, width, height);
    }

    public CollisionState getCollision(Figure figure) {
        Rect boxBounds = getBounds();
        Rect figureBounds = figure.getBounds();
        if(figureBounds.left <= boxBounds.left) {
            return CollisionState.LEFT;
        } else if(figureBounds.top <= boxBounds.top) {
            return CollisionState.TOP;
        } else if(figureBounds.right >= boxBounds.right) {
            return CollisionState.RIGHT;
        } else if(figureBounds.bottom >= boxBounds.bottom) {
            return CollisionState.BOTTOM;
        } else {
            return CollisionState.NONE;
        }

    }
}
