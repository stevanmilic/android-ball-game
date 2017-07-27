package rs.etf.ms130329.ballgame.polygon.view;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.polygon.view.objects.Ball;
import rs.etf.ms130329.ballgame.polygon.view.objects.BlackHole;
import rs.etf.ms130329.ballgame.polygon.view.objects.Obstacle;
import rs.etf.ms130329.ballgame.polygon.view.objects.Polygon;
import rs.etf.ms130329.ballgame.polygon.view.objects.Surface;
import rs.etf.ms130329.ballgame.polygon.view.objects.WinningHole;


/**
 * Created by stevan on 7/27/17.
 */

public class PolygonView extends View {

    Polygon polygon;

    public Polygon getPolygon() {
        return polygon;
    }

    public PolygonView(Context context) {
        super(context);
        polygon = new Polygon();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        drawSurface(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        polygon.draw(canvas);
    }

    private void drawSurface(int width, int height) {
        Surface surface = new Surface(getResources().getColor(R.color.surface), width, height);
        polygon.setSurface(surface);
    }

    public void drawBall(float x, float y) {
        Ball ball = new Ball(getResources().getColor(R.color.ball), x, y, getResources().getInteger(R.integer
                .ball_radius));
        polygon.setBall(ball);
        invalidate();
    }

    public void drawObstacle(float x, float y) {
        Obstacle obstacle = new Obstacle(getResources().getColor(R.color.obstacle), x, y,
                getResources().getInteger(R.integer.obstacle_width), getResources().getInteger(R.integer.obstacle_height));
        polygon.addObstacle(obstacle);
        invalidate();
    }

    public void drawBlackHole(float x, float y) {
        BlackHole blackHole = new BlackHole(getResources().getColor(R.color.black_hole), x, y,
                getResources().getInteger(R.integer.black_hole_radius));
        polygon.addBlackHole(blackHole);
        invalidate();
    }

    public void drawWinningHole(float x, float y) {
        WinningHole winningHole = new WinningHole(getResources().getColor(R.color.winning_hole), x, y,
                getResources().getInteger(R.integer.winning_hole_radius));
        polygon.setWinningHole(winningHole);
        invalidate();
    }
}
