package rs.etf.ms130329.ballgame.engine.drawables;

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

public class Drawables extends Drawable implements Serializable {

    static final long serialVersionUID = 6L;

    protected List<ExtendedDrawable> drawables = new LinkedList<>();

    protected boolean add(ExtendedDrawable drawable) {
        return drawables.add(drawable);
    }

    protected boolean remove(ExtendedDrawable drawable) {
        return drawables.remove(drawable);
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
