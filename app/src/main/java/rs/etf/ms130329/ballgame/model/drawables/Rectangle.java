package rs.etf.ms130329.ballgame.model.drawables;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.ObjectInputStream;

import static android.R.attr.radius;

/**
 * Created by stevan on 7/27/17.
 */

public class Rectangle extends Figure {

    static final long serialVersionUID = 5L;

    private float height;
    private float width;

    public Rectangle(int color, float x, float y, float width, float height) {
        super(color, x, y);

        this.height = height;
        this.width = width;
        super.setBounds(x, y, x + width, y + height);
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        super.setBounds(x, y, x + width, y + height);
    }
}
