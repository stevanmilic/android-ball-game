package rs.etf.ms130329.ballgame.engine.drawables;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Created by stevan on 7/27/17.
 */

public abstract class ExtendedDrawable extends Drawable implements Serializable{

    static final long serialVersionUID = 1L;

    protected transient Paint paint;
    private int color;

    ExtendedDrawable(int color) {
        this.color = color;
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    @CallSuper
    protected void setBounds(float left, float top, float right, float bottom) {
       super.setBounds((int)left, (int)top, (int)right, (int)bottom);
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        paint = new Paint();
        paint.setColor(color);
    }
}
