package rs.etf.ms130329.ballgame.engine.objects;

import android.graphics.Rect;

import rs.etf.ms130329.ballgame.engine.drawables.Background;
import rs.etf.ms130329.ballgame.engine.drawables.Figure;

/**
 * Created by stevan on 7/27/17.
 */

public class Box extends Background {

    static final long serialVersionUID = 9L;

    public enum CollisionState {
        NONE,
        TOP,
        LEFT,
        RIGHT,
        BOTTOM
    }

    private CollisionState collisionState;

    public Box(int color, int width, int height) {
        super(color, width, height);
    }

    public void setCollisionState(Ball ball) {
        Rect boxBounds = getBounds();
        Rect figureBounds = ball.getBounds();
        if (figureBounds.left <= boxBounds.left) {
            collisionState = CollisionState.LEFT;
        } else if (figureBounds.top <= boxBounds.top) {
            collisionState = CollisionState.TOP;
        } else if (figureBounds.right >= boxBounds.right) {
            collisionState = CollisionState.RIGHT;
        } else if (figureBounds.bottom >= boxBounds.bottom) {
            collisionState = CollisionState.BOTTOM;
        } else {
            collisionState = CollisionState.NONE;
        }
    }

    public CollisionState getCollisionState() {
        return collisionState;
    }
}
