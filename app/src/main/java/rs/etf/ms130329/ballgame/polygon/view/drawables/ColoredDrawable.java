package rs.etf.ms130329.ballgame.polygon.view.drawables;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by stevan on 7/27/17.
 */

abstract class ColoredDrawable extends Drawable{
    Paint paint;

    ColoredDrawable(int color) {
        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
