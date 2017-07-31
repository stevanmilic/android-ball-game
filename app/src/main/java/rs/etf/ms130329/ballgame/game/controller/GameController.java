package rs.etf.ms130329.ballgame.game.controller;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import rs.etf.ms130329.ballgame.MainActivity;
import rs.etf.ms130329.ballgame.game.model.GameModel;
import rs.etf.ms130329.ballgame.game.view.GameView;
import rs.etf.ms130329.ballgame.model.objects.Polygon;

public class GameController extends Activity implements SensorEventListener {

    GameModel gameModel;
    GameView gameView;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private WindowManager windowManager;

    private static final float FRAME_RATE = 0.333f;

    private static final String TAG = "GameController";

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

        Polygon polygon = (Polygon) extras.get(MainActivity.PARAMETER_KEY);

        gameView = new GameView(this, polygon);
        gameModel = new GameModel(this, polygon);

        setContentView(gameView);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
        gameView.update(sensorAcceleration, FRAME_RATE);
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
}
