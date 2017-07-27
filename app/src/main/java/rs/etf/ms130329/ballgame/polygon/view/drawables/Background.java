package rs.etf.ms130329.ballgame.polygon.view.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

/**
 * Created by stevan on 7/27/17.
 */

public class Background extends ColoredDrawable {

    private Bitmap bitmap;

    public Background(int color, int width, int height) {
        super(color);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(paint.getColor());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

}
