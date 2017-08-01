package rs.etf.ms130329.ballgame.engine.physics.geometry;

import android.annotation.SuppressLint;

import java.io.Serializable;

import static android.util.FloatMath.pow;
import static android.util.FloatMath.sqrt;

/**
 * Created by stevan on 7/31/17.
 */

public class Point implements Serializable {

    static final long serialVersionUID = 13L;

    protected float pointX;
    protected float pointY;

    public Point(float pointX, float pointY) {
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public float getPointX() {
        return pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointX(float pointX) {
        this.pointX = pointX;
    }

    public void setPointY(float pointY) {
        this.pointY = pointY;
    }

    @SuppressLint("FloatMath")
    public float getDistance(Point point) {
        return sqrt(getDistanceSquared(point));
    }

    public float getDistanceSquared(Point point) {
       return pow(point.pointX - pointX, 2) + pow(point.pointY - pointY, 2);
    }

    static public Point clampPoint(Point point, float minX, float maxX, float minY, float maxY) {
        float x = clamp(point.getPointX(), minX, maxX);
        float y = clamp(point.getPointY(), minY, maxY);
        return new Point(x, y);
    }

    static public float clamp(float x, float min, float max) {
        if (x < min) {
            x = min;
        } else if (x > max) {
            x = max;
        }

        return x;
    }
}
