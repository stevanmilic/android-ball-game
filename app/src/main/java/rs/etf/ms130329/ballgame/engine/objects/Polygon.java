package rs.etf.ms130329.ballgame.engine.objects;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.engine.drawables.Drawables;
import rs.etf.ms130329.ballgame.engine.drawables.ExtendedDrawable;
import rs.etf.ms130329.ballgame.engine.drawables.Figure;

/**
 * Created by stevan on 7/27/17.
 */

public class Polygon extends Drawables {

    static final long serialVersionUID = 10L;

    private Box box;
    private Ball ball;
    private List<Obstacle> obstacles = new LinkedList<>();
    private List<BlackHole> blackHoles = new LinkedList<>();
    private WinningHole winningHole;

    private float frictionFactor;
    private float collisionFactor;

    public Ball getBall() {
        return ball;
    }

    public boolean setBall(Ball ball) {
        if (!isPositionValid(ball)) {
            return false;
        }
        super.add(ball);
        this.ball = ball;
        return true;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public boolean addObstacle(Obstacle obstacle) {
        if (!isPositionValid(obstacle)) {
            return false;
        }
        super.add(obstacle);
        obstacles.add(obstacle);
        return true;
    }

    public List<BlackHole> getBlackHoles() {
        return blackHoles;
    }

    public boolean addBlackHole(BlackHole blackHole) {
        if (!isPositionValid(blackHole)) {
            return false;
        }
        super.add(blackHole);
        blackHoles.add(blackHole);
        return true;
    }

    public WinningHole getWinningHole() {
        return winningHole;
    }

    public boolean setWinningHole(WinningHole winningHole) {
        if (!isPositionValid(winningHole)) {
            return false;
        }
        super.add(winningHole);
        this.winningHole = winningHole;
        return true;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
        super.add(box);
    }

    public float getFrictionFactor() {
        return frictionFactor;
    }

    public void setFrictionFactor(float frictionFactor) {
        this.frictionFactor = frictionFactor;
    }

    public float getCollisionFactor() {
        return collisionFactor;
    }

    public void setCollisionFactor(float collisionFactor) {
        this.collisionFactor = collisionFactor;
    }

    private boolean isPositionValid(Figure newFigure) {
        if (box == null || !box.inBounds(newFigure)) {
            return false;
        }
        for (ExtendedDrawable drawable : drawables) {
            if (drawable instanceof Figure) {
                Figure figure = (Figure) drawable;
                if (newFigure.isIntersecting(figure)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        box.draw(canvas);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
        for (BlackHole blackHole : blackHoles) {
            blackHole.draw(canvas);
        }
        winningHole.draw(canvas);
        ball.draw(canvas);
    }
}
