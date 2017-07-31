package rs.etf.ms130329.ballgame.model.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by stevan on 7/27/17.
 */

public class Background extends ExtendedDrawable {

    static final long serialVersionUID = 2L;

    transient private Bitmap bitmap;
    private int width, height;

    public Background(int color, int width, int height) {
        super(color);

        this.width = width;
        this.height = height;
        super.setBounds(0, 0, width, height);


        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(paint.getColor());
    }

    public boolean inBounds(Figure figure) {
        return getBounds().contains(figure.getBounds());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        super.setBounds(0, 0, width, height);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(paint.getColor());
    }
}
