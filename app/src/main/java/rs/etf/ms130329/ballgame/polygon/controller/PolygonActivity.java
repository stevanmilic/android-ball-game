package rs.etf.ms130329.ballgame.polygon.controller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;
import rs.etf.ms130329.ballgame.polygon.controller.command.BallDrawCommand;
import rs.etf.ms130329.ballgame.polygon.controller.command.BlackHoleDrawCommand;
import rs.etf.ms130329.ballgame.polygon.controller.command.Command;
import rs.etf.ms130329.ballgame.polygon.controller.command.DrawCommand;
import rs.etf.ms130329.ballgame.polygon.controller.command.InfoCommand;
import rs.etf.ms130329.ballgame.polygon.controller.command.ObstacleDrawCommand;
import rs.etf.ms130329.ballgame.polygon.controller.command.SavePolygonCommand;
import rs.etf.ms130329.ballgame.polygon.controller.command.WinningHoleDrawCommand;
import rs.etf.ms130329.ballgame.polygon.model.PolygonModel;
import rs.etf.ms130329.ballgame.polygon.view.PolygonView;

public class PolygonActivity extends AppCompatActivity {

    PolygonView polygonView;
    PolygonModel polygonModel;

    Command command;

    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.polygon_toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupToolbarButtons();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        polygonView = (PolygonView) findViewById(R.id.polygon_view);
        polygonModel = new PolygonModel(this);

        mDetector = new GestureDetectorCompat(this, new PolygonGestureListener());

        command = new InfoCommand(this);
        command.execute();
    }

    private void setupToolbarButtons() {

        final Activity activity = this;

        Button ballButton = (Button) findViewById(R.id.draw_ball_button);

        ballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = new BallDrawCommand(activity, polygonView, "drawBall",
                        float.class, float.class, int.class);
            }
        });

        Button obstacleButton = (Button) findViewById(R.id.draw_obstacle_button);

        obstacleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = new ObstacleDrawCommand(activity, polygonView, "drawObstacle",
                        float.class, float.class, int.class, int.class);
            }
        });

        Button blackHoleButton = (Button) findViewById(R.id.draw_black_hole_button);

        blackHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = new BlackHoleDrawCommand(activity, polygonView, "drawBlackHole",
                        float.class, float.class, int.class);
            }
        });

        Button winningHoleButton = (Button) findViewById(R.id.draw_winning_hole_button);

        winningHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = new WinningHoleDrawCommand(activity, polygonView, "drawWinningHole",
                        float.class, float.class, int.class);
            }
        });

        Button savePolygonButton = (Button) findViewById(R.id.save_polygon_button);

        savePolygonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command = new SavePolygonCommand(activity, polygonView, polygonModel);
                command.execute();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class PolygonGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            //noinspection ConstantConditions
            if (getSupportActionBar().isShowing()) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().show();
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (command instanceof DrawCommand) {
                    Point point = new Point(motionEvent.getX(), motionEvent.getY());
                    DrawCommand drawCommand = (DrawCommand) command;
                    drawCommand.execute(point);
                } else {
                    command.execute();
                }
            }
            return true;
        }

    }

}
