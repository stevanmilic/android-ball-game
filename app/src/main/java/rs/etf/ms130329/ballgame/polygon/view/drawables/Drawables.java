package rs.etf.ms130329.ballgame.polygon.view.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by stevan on 7/27/17.
 */

public class Drawables extends Drawable {
    private List<Drawable> drawables = new LinkedList<>();

    public void add(Drawable drawable) {
        drawables.add(drawable);
    }

    public void remove(Drawable drawable) {
        drawables.remove(drawable);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for (Drawable drawable : drawables) {
            drawable.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
