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
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import rs.etf.ms130329.ballgame.MainActivity;
import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.game.model.GameModel;
import rs.etf.ms130329.ballgame.game.view.GameView;
import rs.etf.ms130329.ballgame.statistics.controller.StatisticsActivity;

public class GameController extends Activity implements SensorEventListener{

    public enum GameState {
        RUNNING,
        LOST,
        WON
    }

    GameModel gameModel;
    GameView gameView;

    GameState gameState;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private WindowManager windowManager;

    private static final float FRAME_RATE = 0.333f;

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

        gameView = new GameView(this, polygon);
        gameModel = new GameModel(this, polygon);

        setContentView(gameView);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        gameStartTime = SystemClock.elapsedRealtime();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] sensorAcceleration = adjustAccelerationOrientation(windowManager.getDefaultDisplay().getRotation(), event.values);
        switch (gameView.update(sensorAcceleration, FRAME_RATE)) {
            case WON:
                gameWonAction();
                break;
            case LOST:
                gameLostAction();
                break;
            case RUNNING:
                gameState = GameState.RUNNING;
                break;
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
        gameState = GameState.WON;
        mSensorManager.unregisterListener(this);
        gameEndTimeInSeconds = (SystemClock.elapsedRealtime() - gameStartTime) / 1000.0;
        gameEndTimeInSeconds = Math.round(gameEndTimeInSeconds * 100.0) / 100.0;
        showInfo(getResources().getString(R.string.won_the_game));
        showSaveDialog();
    }

    private void gameLostAction() {
        showInfo(getResources().getString(R.string.lost_the_game));
        mSensorManager.unregisterListener(this);
        gameState = GameState.LOST;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gameState == GameState.LOST) {
            gameView.setBallToStartingPosition();
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
            gameStartTime = SystemClock.elapsedRealtime();
        }
        return true;
    }

    private void showInfo(String info) {
        Toast toast = Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG);
        toast.show();
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.save_dialog));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gameModel.insertScoreEntry(gameView.getPolygonName(), gameEndTimeInSeconds, input.getText().toString());
                Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                intent.putExtra(STATISTICS_PARAMETER_KEY, gameView.getPolygonName());
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
