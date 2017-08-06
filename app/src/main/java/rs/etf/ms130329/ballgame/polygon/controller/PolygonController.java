package rs.etf.ms130329.ballgame.polygon.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
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

        polygonView = new PolygonView(this);
        polygonModel = new PolygonModel(this);

        setDrawBallState();

        setContentView(polygonView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
                case DRAW_DONE:
                    showSaveDialog();
                    break;
            }

        }

        return true;
    }

    private void setDrawBallState() {
        showInfo(getResources().getString(R.string.draw_ball));
        State.noObstacles = polygonModel.getNoObstacles();
        State.noBlackHoles = polygonModel.getNoBlackHoles();
    }

    private void setDrawObstacleState() {
        state = State.DRAW_OBSTACLES;
        showInfo(getResources().getString(R.string.draw_obstacles));
    }

    private void setDrawBlackHoleState() {
        state = State.DRAW_BLACK_HOLES;
        showInfo(getResources().getString(R.string.draw_black_holes));
    }

    private void setDrawWinningHoleState() {
        state = State.DRAW_WINNING_HOLE;
        showInfo(getResources().getString(R.string.draw_winning_hole));
    }

    private void ballDrawAction() {
        if (State.noObstacles > 0) {
            setDrawObstacleState();
        } else if (State.noBlackHoles > 0) {
            setDrawBlackHoleState();
        } else {
            setDrawWinningHoleState();
        }
    }

    private void obstacleDrawAction() {
        State.noObstacles--;
        if (State.noObstacles == 0) {
            if (State.noBlackHoles > 0) {
                setDrawBlackHoleState();
            } else {
                setDrawWinningHoleState();
            }
        } else {
            showInfo(State.noObstacles + " " + getResources().getString(R.string.obstacles_left));
        }

    }

    private void blackHoleDrawAction() {
        State.noBlackHoles--;
        if (State.noBlackHoles == 0) {
            setDrawWinningHoleState();
        } else {
            showInfo(State.noBlackHoles + " " + getResources().getString(R.string.black_holes_left));
        }

    }

    private void winningHoleDrawAction() {
        state = State.DRAW_DONE;
        showInfo(getResources().getString(R.string.save_polygon));
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
                polygonView.getPolygon().setName(input.getText().toString());
                polygonModel.exportPolygonToFile(polygonView.getPolygon());
                finish();
            }
        });
    }

}
