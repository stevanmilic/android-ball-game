package rs.etf.ms130329.ballgame.polygon.view.objects;

import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.polygon.view.drawables.Drawables;
import rs.etf.ms130329.ballgame.polygon.view.drawables.ExtendedDrawable;
import rs.etf.ms130329.ballgame.polygon.view.drawables.Figure;

/**
 * Created by stevan on 7/27/17.
 */

public class Polygon extends Drawables {

    private Surface surface;
    private Ball ball;
    private List<Obstacle> obstacles = new LinkedList<>();
    private List<BlackHole> blackHoles = new LinkedList<>();
    private WinningHole winningHole;

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

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
        super.add(surface);
    }

    private boolean isPositionValid(Figure newFigure) {
        if (surface == null || !surface.inBounds(newFigure)) {
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
}
