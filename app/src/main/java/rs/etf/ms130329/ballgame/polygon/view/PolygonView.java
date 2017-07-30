package rs.etf.ms130329.ballgame.polygon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.TypedValue;
import android.view.View;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.model.objects.Ball;
import rs.etf.ms130329.ballgame.model.objects.BlackHole;
import rs.etf.ms130329.ballgame.model.objects.Box;
import rs.etf.ms130329.ballgame.model.objects.Obstacle;
import rs.etf.ms130329.ballgame.model.objects.Polygon;
import rs.etf.ms130329.ballgame.model.objects.WinningHole;


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
        Box box = new Box(getResources().getColor(R.color.box), width, height);
        polygon.setBox(box);
    }

    public boolean drawBall(float x, float y) {
        Ball ball = new Ball(getResources().getColor(R.color.ball), x, y, getResources().getInteger(R.integer.ball_radius));
        boolean isValidDraw = polygon.setBall(ball);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }

    public boolean drawObstacle(float x, float y) {
        Obstacle obstacle = new Obstacle(getResources().getColor(R.color.obstacle), x, y,
                getResources().getInteger(R.integer.obstacle_width), getResources().getInteger(R.integer.obstacle_height));
        boolean isValidDraw = polygon.addObstacle(obstacle);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }

    public boolean drawBlackHole(float x, float y) {
        BlackHole blackHole = new BlackHole(getResources().getColor(R.color.black_hole), x, y,
                getResources().getInteger(R.integer.black_hole_radius));
        boolean isValidDraw = polygon.addBlackHole(blackHole);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }

    public boolean drawWinningHole(float x, float y) {
        WinningHole winningHole = new WinningHole(getResources().getColor(R.color.winning_hole), x, y,
                getResources().getInteger(R.integer.winning_hole_radius));
        boolean isValidDraw = polygon.setWinningHole(winningHole);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }
}
