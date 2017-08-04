package rs.etf.ms130329.ballgame.polygon.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.view.View;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.objects.Ball;
import rs.etf.ms130329.ballgame.engine.objects.BlackHole;
import rs.etf.ms130329.ballgame.engine.objects.Box;
import rs.etf.ms130329.ballgame.engine.objects.Obstacle;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.engine.objects.WinningHole;
import rs.etf.ms130329.ballgame.settings.SettingsActivity;


/**
 * Created by stevan on 7/27/17.
 */

public class PolygonView extends View {

    Polygon polygon;
    SharedPreferences sharedPreferences;

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

    public boolean drawBall(float x, float y, int ballRadius) {

        Ball ball = new Ball(getResources().getColor(R.color.ball), x, y, ballRadius);

        boolean isValidDraw = polygon.setBall(ball);
        if (isValidDraw) {
            invalidate();
        }

        return isValidDraw;
    }

    public boolean drawObstacle(float x, float y,int obstacleWidth, int obstacleHeight) {

        Obstacle obstacle = new Obstacle(getResources().getColor(R.color.obstacle), x, y, obstacleWidth, obstacleHeight);

        boolean isValidDraw = polygon.addObstacle(obstacle);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }

    public boolean drawBlackHole(float x, float y, int blackHoleRadius) {
        BlackHole blackHole = new BlackHole(getResources().getColor(R.color.black_hole), x, y, blackHoleRadius);
        boolean isValidDraw = polygon.addBlackHole(blackHole);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }

    public boolean drawWinningHole(float x, float y, int winningHoleRadius) {
        WinningHole winningHole = new WinningHole(getResources().getColor(R.color.winning_hole), x, y, winningHoleRadius);
        boolean isValidDraw = polygon.setWinningHole(winningHole);
        if (isValidDraw) {
            invalidate();
        }
        return isValidDraw;
    }
}
