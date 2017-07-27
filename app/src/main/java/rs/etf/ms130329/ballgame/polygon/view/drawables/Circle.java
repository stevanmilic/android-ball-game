package rs.etf.ms130329.ballgame.polygon.view.drawables;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

/**
 * Created by stevan on 7/27/17.
 */

public class Circle extends Figure {

    private int radius;

    public Circle(int color, float x, float y, int radius) {
        super(color, x, y);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }
}
