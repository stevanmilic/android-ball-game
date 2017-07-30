package rs.etf.ms130329.ballgame.model.objects;

import rs.etf.ms130329.ballgame.model.drawables.Circle;

/**
 * Created by stevan on 7/27/17.
 */

public class Ball extends Circle {

    static final long serialVersionUID = 7L;

    private float vx, vy;
    private float frictionFactor;
    private float collisionFactor;

    public Ball(int color, float x, float y, int radius) {
        super(color, x, y, radius);
    }

    public void setFactors(float frictionFactor, float collisionFactor) {
        this.collisionFactor = collisionFactor;
        this.frictionFactor = frictionFactor;
    }

    public void accelerate(float sx, float sy, float dT, Box.CollisionState collisionState) {
        final float ax = -sx;
        final float ay = -sy;

        switch(collisionState) {
            case LEFT:
                vx = -vx*collisionFactor;
                vy = vy + ay * dT;
                break;
            case TOP:
                vx = vx + ax * dT;
                vy = -vy*collisionFactor;
                break;
            case RIGHT:
                vx = -vx*collisionFactor;
                vy = vy + ay * dT;
                break;
            case BOTTOM:
                vx = vx + ax * dT;
                vy = -vy*collisionFactor;
                break;
            case NONE:
                vx = vx + ax * dT;
                vy = vy + ay * dT;
                break;
        }

        x = x + vx * dT + ax * dT * dT / 2;
        y = y + vy * dT + ax * dT * dT / 2;


        super.setCircleBounds(radius);
    }

}
