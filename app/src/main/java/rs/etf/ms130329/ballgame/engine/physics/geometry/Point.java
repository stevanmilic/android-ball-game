package rs.etf.ms130329.ballgame.model.physics.geometry;

import android.annotation.SuppressLint;

import java.io.Serializable;

import static android.util.FloatMath.pow;
import static android.util.FloatMath.sqrt;

/**
 * Created by stevan on 7/31/17.
 */

public class Point implements Serializable{

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

    @SuppressLint("FloatMath")
    public float getDistance(Point point) {
       return sqrt(pow(point.pointX - pointX, 2) + pow(point.pointY - pointY, 2));
    }
}
