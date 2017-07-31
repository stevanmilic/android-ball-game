package rs.etf.ms130329.ballgame.model.physics.collision;

import rs.etf.ms130329.ballgame.model.objects.Ball;
import rs.etf.ms130329.ballgame.model.objects.Obstacle;

/**
 * Created by stevan on 7/31/17.
 */

public class ObstacleCollision extends Collision {
    ObstacleCollision(float collisionFactor, Ball ball, Obstacle obstacle) {
        super(collisionFactor);
    }
}
