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
import rs.etf.ms130329.ballgame.polygon.view.PolygonView;

public class PolygonActivity extends Activity {

    private enum State {
        DRAW_BALL,
        DRAW_OBSTACLES,
        DRAW_BLACK_HOLES,
        DRAW_WINNING_HOLE,
        DRAW_DONE
    }

    State state = State.DRAW_BALL;
    PolygonView polygonView;
    int noObstacles;
    int noBlackHoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        polygonView = new PolygonView(this);
        setContentView(polygonView);
        showInfo(getResources().getString(R.string.draw_ball));
        noObstacles = getResources().getInteger(R.integer.no_obstacles);
        noBlackHoles = getResources().getInteger(R.integer.no_black_holes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (state) {
                case DRAW_BALL:
                    polygonView.drawBall(x, y);
                    state = State.DRAW_OBSTACLES;
                    showInfo(getResources().getString(R.string.draw_obstacles));
                    break;
                case DRAW_OBSTACLES:
                    polygonView.drawObstacle(x, y);
                    noObstacles--;
                    if (noObstacles == 0) {
                        state = State.DRAW_BLACK_HOLES;
                        showInfo(getResources().getString(R.string.draw_black_holes));
                    } else {
                        showInfo(noObstacles + " " + getResources().getString(R.string.obstacles_left));
                    }
                    break;
                case DRAW_BLACK_HOLES:
                    polygonView.drawBlackHole(x, y);
                    noBlackHoles--;
                    if (noBlackHoles == 0) {
                        state = State.DRAW_WINNING_HOLE;
                        showInfo(getResources().getString(R.string.draw_winning_hole));
                    } else {
                        showInfo(noBlackHoles + " " + getResources().getString(R.string.black_holes_left));
                    }
                    break;
                case DRAW_WINNING_HOLE:
                    polygonView.drawWinningHole(x, y);
                    state = State.DRAW_DONE;
                    showInfo(getResources().getString(R.string.save_polygon));
                    break;
                case DRAW_DONE:
                    showSaveDialog();
                    break;
            }

        }

        return true;
    }


    private void showInfo(String info) {
        Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
        toast.show();
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exportPolygonToFile(input.getText().toString());
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

    private void exportPolygonToFile(String name) {
        //TODO: export polygon to file via gson or import/export method for each class
        String fileName = name + ".pyg";
    }
}
