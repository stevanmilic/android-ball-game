package rs.etf.ms130329.ballgame.engine.objects;

import java.util.List;

import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;

/**
 * Created by stevan on 8/5/17.
 */

public interface Collidable {
    void detectCollisions(List<Collision> collisionList, Ball ball);
}
