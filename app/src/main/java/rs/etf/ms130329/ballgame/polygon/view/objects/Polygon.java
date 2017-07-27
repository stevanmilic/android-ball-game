package rs.etf.ms130329.ballgame.polygon.view.objects;

import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.polygon.view.drawables.Drawables;

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

    public void setBall(Ball ball) {
        this.ball = ball;
        super.add(ball);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
        super.add(obstacle);
    }

    public List<BlackHole> getBlackHoles() {
        return blackHoles;
    }

    public void addBlackHole(BlackHole blackHole) {
        blackHoles.add(blackHole);
        super.add(blackHole);
    }

    public WinningHole getWinningHole() {
        return winningHole;
    }

    public void setWinningHole(WinningHole winningHole) {
        this.winningHole = winningHole;
        super.add(winningHole);
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
        super.add(surface);
    }
}
