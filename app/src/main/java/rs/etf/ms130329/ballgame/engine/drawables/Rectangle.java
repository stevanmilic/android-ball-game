package rs.etf.ms130329.ballgame.engine.drawables;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 7/27/17.
 */

public class Rectangle extends Figure {

    static final long serialVersionUID = 5L;

    protected float left, top, right, bottom;

    public Rectangle(int color, float centerX, float centerY, float width, float height) {
        super(color, centerX, centerY);

        left = centerX - width / 2;
        right = centerX + width / 2;
        bottom = centerY + height / 2;
        top = centerY - height / 2;

        super.setBounds(left, top, right, bottom);
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        super.setBounds(left, top, right, bottom);
    }
}

