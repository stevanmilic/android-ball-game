package rs.etf.ms130329.ballgame.game.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import rs.etf.ms130329.ballgame.MainActivity;
import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.game.model.GameModel;
import rs.etf.ms130329.ballgame.game.view.GameSurfaceView;
import rs.etf.ms130329.ballgame.statistics.controller.StatisticsActivity;

public class GameController extends Activity implements SensorEventListener, Observer {

    GameModel gameModel;
    GameSurfaceView gameSurfaceView;
    GameSoundController gameSoundController;

    Stopwatch stopwatch;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private WindowManager windowManager;

    private static final float FRAME_RATE = 0.35f;

    public static final String STATISTICS_PARAMETER_KEY = "polygon_name";

    public static final String GAME_POLYGON_PARAMETER_KEY = "game_polygon";
    public static final String GAME_STOPWATCH_PARAMETER_KEY = "game_stopwatch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        Polygon polygon = (Polygon) extras.get(MainActivity.GAME_PARAMETER_KEY);

        gameModel = new GameModel(this, polygon);
        gameSurfaceView = new GameSurfaceView(this, polygon);
        gameSoundController = new GameSoundController(this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setContentView(gameSurfaceView);

        BallStateObservable.getInstance().addObserver(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        stopwatch = new Stopwatch();
        stopwatch.start();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
        if (gameSurfaceView != null) {
            gameSurfaceView.startWorkerThread();
        }
        stopwatch.resume();
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if (gameSurfaceView != null) {
            gameSurfaceView.stopWorkerThread();
        }
        stopwatch.pause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(GAME_POLYGON_PARAMETER_KEY, gameSurfaceView.getPolygon());

        stopwatch.pause();
        outState.putSerializable(GAME_STOPWATCH_PARAMETER_KEY, stopwatch);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Polygon polygon = (Polygon) savedInstanceState.get(GAME_POLYGON_PARAMETER_KEY);
        gameSurfaceView.setPolygon(polygon);

        stopwatch = (Stopwatch) savedInstanceState.get(GAME_STOPWATCH_PARAMETER_KEY);
        if (stopwatch != null) {
            stopwatch.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameSoundController.releaseSoundPool();
        BallStateObservable.getInstance().deleteObserver(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float[] sensorAcceleration = adjustAccelerationOrientation(windowManager.getDefaultDisplay().getRotation(), event.values);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                gameSurfaceView.update(sensorAcceleration, FRAME_RATE);
            }
        });
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof BallStateObservable) {
            BallStateObservable ballStateObservable = (BallStateObservable) observable;
            switch (ballStateObservable.getBallState()) {
                case COLLISION_BOX:
                    gameSoundController.playBounceBoxSound();
                    break;
                case COLLISION_OBSTACLE:
                    gameSoundController.playBounceObstacleSound();
                    break;
                case IN_BLACK_HOLE:
                    gameLostAction();
                    break;
                case IN_WINNING_HOLE:
                    gameWonAction();
                    break;
                default:
                    break;
            }
        }
    }

    public static float[] adjustAccelerationOrientation(int displayRotation, float[] eventValues) {
        float[] adjustedValues = new float[3];

        final int axisSwap[][] = {
                {1, -1, 0, 1},     // ROTATION_0
                {-1, -1, 1, 0},     // ROTATION_90
                {-1, 1, 0, 1},     // ROTATION_180
                {1, 1, 1, 0}}; // ROTATION_270

        final int[] as = axisSwap[displayRotation];
        adjustedValues[0] = (float) as[0] * eventValues[as[2]];
        adjustedValues[1] = (float) as[1] * eventValues[as[3]];
        adjustedValues[2] = eventValues[2];

        return adjustedValues;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void gameWonAction() {
        gameSurfaceView.stopWorkerThread();
        mSensorManager.unregisterListener(this);
        gameSoundController.playWinningSound();
        stopwatch.stop();
        this.runOnUiThread(new Runnable() {
            public void run() {
                showInfo(getResources().getString(R.string.won_the_game));
                showSaveDialog();
            }
        });
    }

    private void gameLostAction() {
        gameSurfaceView.stopWorkerThread();
        mSensorManager.unregisterListener(this);
        gameSoundController.playLosingSound();
        stopwatch.stop();
        this.runOnUiThread(new Runnable() {
            public void run() {
                showInfo(getResources().getString(R.string.lost_the_game));
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (BallStateObservable.getInstance().getBallState() == BallStateObservable.BallState.IN_BLACK_HOLE) {
            gameSurfaceView.setBallToStartingPosition();
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
            stopwatch.start();
        }
        return true;
    }

    private void showInfo(String info) {
        Toast toast = Toast.makeText(this, info, Toast.LENGTH_LONG);
        toast.show();
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getResources().getString(R.string.save_score_dialog));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.input_player_name);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = input.getText().toString();
                if (playerName.isEmpty()) {
                    showInfo(getResources().getString(R.string.empty_input));
                    return;
                }
                gameModel.insertScoreEntry(gameSurfaceView.getPolygonName(), stopwatch.getElapsedTimeInSeconds(),
                        input.getText().toString());
                Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                intent.putExtra(STATISTICS_PARAMETER_KEY, gameSurfaceView.getPolygonName());
                finish();
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }
}
