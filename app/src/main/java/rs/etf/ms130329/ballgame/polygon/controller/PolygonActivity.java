package rs.etf.ms130329.ballgame.polygon.controller;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.polygon.model.PolygonModel;
import rs.etf.ms130329.ballgame.polygon.view.PolygonView;

//TODO: add input dimensions dialog for each object on click
public class PolygonActivity extends AppCompatActivity {

    //TODO: -> State pattern
    private enum State {
        DRAW_BALL,
        DRAW_OBSTACLES,
        DRAW_BLACK_HOLES,
        DRAW_WINNING_HOLE,
        DRAW_NONE;

        static int noObstacles;
        static int noBlackHoles;
    }

    State state = State.DRAW_NONE;

    PolygonView polygonView;
    PolygonModel polygonModel;

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
        showInfo(getResources().getString(R.string.toolbar_info));
    }

    private void setupToolbarButtons() {
        Button ballButton = (Button) findViewById(R.id.draw_ball_button);

        ballButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDrawBallState();
            }
        });

        Button obstacleButton = (Button) findViewById(R.id.draw_obstacle_button);

        obstacleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDrawObstacleState();
            }
        });

        Button blackHoleButton = (Button) findViewById(R.id.draw_black_hole_button);

        blackHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDrawBlackHoleState();
            }
        });

        Button winningHoleButton = (Button) findViewById(R.id.draw_winning_hole_button);

        winningHoleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDrawWinningHoleState();
            }
        });

        Button savePolygonButton = (Button) findViewById(R.id.save_polygon_button);

        savePolygonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSaveDialog();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void setDrawBallState() {
        state = State.DRAW_BALL;
        showInfo(getResources().getString(R.string.draw_ball));
        getSupportActionBar().hide();
    }

    private void setDrawObstacleState() {
        state = State.DRAW_OBSTACLES;
        showInfo(getResources().getString(R.string.draw_obstacles));
        getSupportActionBar().hide();
    }

    private void setDrawBlackHoleState() {
        state = State.DRAW_BLACK_HOLES;
        showInfo(getResources().getString(R.string.draw_black_holes));
        getSupportActionBar().hide();
    }

    private void setDrawWinningHoleState() {
        state = State.DRAW_WINNING_HOLE;
        showInfo(getResources().getString(R.string.draw_winning_hole));
        getSupportActionBar().hide();
    }

    private void ballDrawAction() {
        Button ballButton = (Button) findViewById(R.id.draw_ball_button);
        ballButton.setEnabled(false);
        state = State.DRAW_NONE;
    }

    private void obstacleDrawAction() {
    }

    private void blackHoleDrawAction() {

    }

    private void winningHoleDrawAction() {
        Button winningHoleButton = (Button) findViewById(R.id.draw_winning_hole_button);
        winningHoleButton.setEnabled(false);
        state = State.DRAW_NONE;
    }

    private void showInfo(String info) {
        Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.save_polygon_dialog));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.input_polygon_name);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String polygonName = input.getText().toString();
                if (polygonName.isEmpty()) {
                    showInfo(getResources().getString(R.string.empty_input));
                    return;
                }
                polygonView.getPolygon().setName(polygonName);
                polygonModel.exportPolygonToFile(polygonView.getPolygon());
                dialog.dismiss();
                finish();
            }
        });
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
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                switch (state) {
                    case DRAW_BALL:
                        if (polygonView.drawBall(x, y, polygonModel.getBallRadius())) {
                            ballDrawAction();
                        } else {
                            showInfo(getResources().getString(R.string.invalid_draw));
                        }
                        break;
                    case DRAW_OBSTACLES:
                        if (polygonView.drawObstacle(x, y, polygonModel.getObstacleWidth(), polygonModel.getObstacleHeight())) {
                            obstacleDrawAction();
                        } else {
                            showInfo(getResources().getString(R.string.invalid_draw));
                        }
                        break;
                    case DRAW_BLACK_HOLES:
                        if (polygonView.drawBlackHole(x, y, polygonModel.getBlackHoleRadius())) {
                            blackHoleDrawAction();
                        } else {
                            showInfo(getResources().getString(R.string.invalid_draw));
                        }
                        break;
                    case DRAW_WINNING_HOLE:
                        if (polygonView.drawWinningHole(x, y, polygonModel.getWinningHoleRadius())) {
                            winningHoleDrawAction();
                        } else {
                            showInfo(getResources().getString(R.string.invalid_draw));
                        }
                        break;
                    case DRAW_NONE:
                        showInfo(getResources().getString(R.string.toolbar_info));
                        break;
                }
            }

            return true;
        }
    }

}
