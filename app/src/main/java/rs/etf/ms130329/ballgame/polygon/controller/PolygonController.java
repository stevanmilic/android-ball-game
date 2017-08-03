package rs.etf.ms130329.ballgame.polygon.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.polygon.model.PolygonModel;
import rs.etf.ms130329.ballgame.polygon.view.PolygonView;

public class PolygonController extends Activity {

    private enum State {
        DRAW_BALL,
        DRAW_OBSTACLES,
        DRAW_BLACK_HOLES,
        DRAW_WINNING_HOLE,
        DRAW_DONE;

        static int noObstacles;
        static int noBlackHoles;
    }

    State state = State.DRAW_BALL;

    PolygonView polygonView;
    PolygonModel polygonModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setDrawBallState();

        polygonView = new PolygonView(this);
        polygonModel = new PolygonModel(this);

        setContentView(polygonView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (state) {
                case DRAW_BALL:
                    if(polygonView.drawBall(x, y)) {
                        setDrawObstaclesState();
                    } else {
                        showInfo(getResources().getString(R.string.invalid_draw));
                    }
                    break;
                case DRAW_OBSTACLES:
                    if(polygonView.drawObstacle(x, y)) {
                        setDrawBlackHolesState();
                    } else {
                        showInfo(getResources().getString(R.string.invalid_draw));
                    }
                    break;
                case DRAW_BLACK_HOLES:
                    if(polygonView.drawBlackHole(x, y)) {
                        setDrawWinningHoleState();
                    } else {
                        showInfo(getResources().getString(R.string.invalid_draw));
                    }
                    break;
                case DRAW_WINNING_HOLE:
                    if(polygonView.drawWinningHole(x, y)) {
                        setDrawDoneState();
                    } else {
                        showInfo(getResources().getString(R.string.invalid_draw));
                    }
                    break;
                case DRAW_DONE:
                    showSaveDialog();
                    break;
            }

        }

        return true;
    }

    private void setDrawBallState() {
        showInfo(getResources().getString(R.string.draw_ball));
        State.noObstacles = getResources().getInteger(R.integer.no_obstacles);
        State.noBlackHoles = getResources().getInteger(R.integer.no_black_holes);
    }

    private void setDrawObstaclesState() {
        state = State.DRAW_OBSTACLES;
        showInfo(getResources().getString(R.string.draw_obstacles));
    }

    private void setDrawBlackHolesState() {
        State.noObstacles--;
        if (State.noObstacles == 0) {
            state = State.DRAW_BLACK_HOLES;
            showInfo(getResources().getString(R.string.draw_black_holes));
        } else {
            showInfo(State.noObstacles + " " + getResources().getString(R.string.obstacles_left));
        }

    }

    private void setDrawDoneState() {
        state = State.DRAW_DONE;
        showInfo(getResources().getString(R.string.save_polygon));
    }

    private void setDrawWinningHoleState() {
        State.noBlackHoles--;
        if (State.noBlackHoles == 0) {
            state = State.DRAW_WINNING_HOLE;
            showInfo(getResources().getString(R.string.draw_winning_hole));
        } else {
            showInfo(State.noBlackHoles + " " + getResources().getString(R.string.black_holes_left));
        }

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
        builder.setView(input);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                polygonView.getPolygon().setName(input.getText().toString());
                polygonModel.exportPolygonToFile(polygonView.getPolygon());
                finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
