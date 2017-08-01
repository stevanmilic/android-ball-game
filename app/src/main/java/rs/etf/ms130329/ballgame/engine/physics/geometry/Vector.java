package rs.etf.ms130329.ballgame.engine.physics.geometry;

import static android.util.FloatMath.sqrt;

/**
 * Created by stevan on 7/31/17.
 */

public class Vector extends Point {

    static final long serialVersionUID = 14L;

    public Vector(float pointX, float pointY) {
        super(pointX, pointY);
    }

    public float dotProduct(Vector vector) {
        return this.pointX * vector.pointX + this.pointY * vector.pointY;
    }

    public void multiply(float constant) {
        pointX *= constant;
        pointY *= constant;
    }

    public void add(Vector vector) {
        pointX += vector.getPointX();
        pointY += vector.getPointY();
    }

    public Vector getUnitVector() {
        return new Vector(Math.signum(pointX), Math.signum(pointY));
    }

    public float getMagnitude() {
        return sqrt(pointX*pointX + pointY*pointY);
    }

    public static Vector add(Vector firstVector, Vector secondVector) {
        float newPointX = firstVector.getPointX() + secondVector.getPointX();
        float newPointY = firstVector.getPointY() + secondVector.getPointY();
        return new Vector(newPointX, newPointY);
    }

    public static Vector subtract(Vector firstVector, Vector secondVector) {
        float newPointX = firstVector.getPointX() - secondVector.getPointX();
        float newPointY = firstVector.getPointY() - secondVector.getPointY();
        return new Vector(newPointX, newPointY);
    }

    public static Vector multiply(Vector firstVector, float constant) {
        float newPointX = firstVector.getPointX() * constant;
        float newPointY = firstVector.getPointY() * constant;
        return new Vector(newPointX, newPointY);
    }

}
