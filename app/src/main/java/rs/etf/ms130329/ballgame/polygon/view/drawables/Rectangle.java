package rs.etf.ms130329.ballgame.polygon.view.drawables;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

/**
 * Created by stevan on 7/27/17.
 */

public class Rectangle extends Figure {

    private float height;
    private float width;

    public Rectangle(int color, float x, float y, float width, float height) {
        super(color, x, y);
        this.height = height;
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }
}
