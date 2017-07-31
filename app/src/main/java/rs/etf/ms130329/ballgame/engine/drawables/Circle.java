package rs.etf.ms130329.ballgame.model.drawables;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by stevan on 7/27/17.
 */

public class Circle extends Figure {

    static final long serialVersionUID = 4L;

    protected float radius;

    public Circle(int color, float x, float y, float radius) {
        super(color, x, y);

        this.radius = radius;
        setCircleBounds(x, y, radius);
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(point.getPointX(), point.getPointY(), radius, paint);
    }

    protected void setCircleBounds(float x, float y, float radius) {
        super.setBounds(x - radius, y - radius, x + radius, y + radius);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setCircleBounds(point.getPointX(), point.getPointY(),radius);
    }
}
