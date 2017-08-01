package rs.etf.ms130329.ballgame.engine.physics.collision;

import rs.etf.ms130329.ballgame.engine.physics.geometry.Vector;

/**
 * Created by stevan on 7/31/17.
 */

public abstract  class Collision {

    Vector normal;
    float penetration;
    private float collisionFactor;

    Collision(float collisionFactor) {
        this.collisionFactor = collisionFactor;
    }

    public float getCollisionFactor() {
        return collisionFactor;
    }

    public Vector getNormal() {
        return normal;
    }

    public float getPenetration() {
        return penetration;
    }
}
