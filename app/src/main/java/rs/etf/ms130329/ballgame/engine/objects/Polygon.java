package rs.etf.ms130329.ballgame.engine.objects;

import android.graphics.drawable.Drawable;

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

    private String name;
    private Box box;
    private Ball ball;
    private List<Obstacle> obstacles = new LinkedList<>();
    private List<BlackHole> blackHoles = new LinkedList<>();
    private WinningHole winningHole;

    private List<Collidable> collidables = new LinkedList<>();

    private float frictionFactor;
    private float collisionFactor;

    public Ball getBall() {
        return ball;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean setBall(Ball ball) {
        if (!isPositionValid(ball)) {
            return false;
        }
        drawables.add(ball);
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
        drawables.add(1, obstacle);
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
        drawables.add(1, blackHole);
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
        drawables.add(1, winningHole);
        this.winningHole = winningHole;
        return true;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
        drawables.add(box);
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

    public List<Collidable> getCollidablesObjects() {
        if(collidables.isEmpty()) {
            for (Drawable drawable : drawables) {
                if (drawable instanceof Collidable) {
                    collidables.add((Collidable) drawable);
                }
            }
        }

        return collidables;
    }
}
