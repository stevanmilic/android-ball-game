package rs.etf.ms130329.ballgame.game.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.MotionEvent;
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

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private WindowManager windowManager;

    private static final float FRAME_RATE = 0.35f;

    private static final String TAG = "GameController";

    private long gameStartTime;
    private double gameEndTimeInSeconds;

    public static final String STATISTICS_PARAMETER_KEY = "polygon_name";

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

        BallStateObservable ballStateObservable = BallStateObservable.getInstance();
        ballStateObservable.addObserver(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //TODO: onPause, onResume handler for elapsed time
        gameStartTime = SystemClock.elapsedRealtime();
    }

    //TODO: on lock screen handler for saving the state of the game
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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
        mSensorManager.unregisterListener(this);
        gameEndTimeInSeconds = (SystemClock.elapsedRealtime() - gameStartTime) / 1000.0;
        gameEndTimeInSeconds = Math.round(gameEndTimeInSeconds * 100.0) / 100.0;
        gameSoundController.playWinningSound();
        this.runOnUiThread(new Runnable() {
            public void run() {
                showInfo(getResources().getString(R.string.won_the_game));
                showSaveDialog();
            }
        });
    }

    private void gameLostAction() {
        mSensorManager.unregisterListener(this);
        gameSoundController.playLosingSound();
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
            gameStartTime = SystemClock.elapsedRealtime();
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
        builder.setView(input);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameModel.insertScoreEntry(gameSurfaceView.getPolygonName(), gameEndTimeInSeconds, input.getText().toString());
                Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                intent.putExtra(STATISTICS_PARAMETER_KEY, gameSurfaceView.getPolygonName());
                finish();
                startActivity(intent);
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
