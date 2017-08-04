package rs.etf.ms130329.ballgame.polygon.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.settings.SettingsActivity;

/**
 * Created by stevan on 7/28/17.
 */

public class PolygonModel {

    private final static String POLYGON_EXTENSION = ".pyg";

    private Context context;

    private int ballRadius;
    private int obstacleWidth;
    private int obstacleHeight;
    private int blackHoleRadius;
    private int winningHoleRadius;
    private int noObstacles;
    private int noBlackHoles;

    public PolygonModel(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        ballRadius = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_BALL_RADIUS,
                String.valueOf(context.getResources().getInteger(R.integer.ball_radius))));

        obstacleWidth = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_OBSTACLE_WIDTH,
                String.valueOf(context.getResources().getInteger(R.integer.obstacle_width))));

        obstacleHeight = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_OBSTACLE_HEIGHT,
                String.valueOf(context.getResources().getInteger(R.integer.obstacle_height))));

        blackHoleRadius = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_BLACK_HOLE_RADIUS,
                String.valueOf(context.getResources().getInteger(R.integer.black_hole_radius))));

        winningHoleRadius = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_WINNING_HOLE_RADIUS,
                String.valueOf(context.getResources().getInteger(R.integer.winning_hole_radius))));

        noObstacles = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_NO_OBSTACLES,
                String.valueOf(context.getResources().getInteger(R.integer.no_obstacles))));

        noBlackHoles = Integer.parseInt(sharedPreferences.getString(SettingsActivity.KEY_PREF_NO_BLACK_HOLES,
                String.valueOf(context.getResources().getInteger(R.integer.no_black_holes))));
    }

    public int getBallRadius() {
        return ballRadius;
    }

    public int getObstacleWidth() {
        return obstacleWidth;
    }

    public int getObstacleHeight() {
        return obstacleHeight;
    }

    public int getBlackHoleRadius() {
        return blackHoleRadius;
    }

    public int getWinningHoleRadius() {
        return winningHoleRadius;
    }

    public int getNoObstacles() {
        return noObstacles;
    }

    public int getNoBlackHoles() {
        return noBlackHoles;
    }

    public void exportPolygonToFile(Polygon polygon) {
        String fileName = polygon.getName() + POLYGON_EXTENSION;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(polygon);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Polygon importPolygonFromFile(String name) {
        String fileName = name + POLYGON_EXTENSION;
        Polygon polygon = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            polygon = (Polygon) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return polygon;
    }

    public void deletePolygon(String name) {
        String fileName = name + POLYGON_EXTENSION;
        context.deleteFile(fileName);
    }

    public List<String> getAllPolygons() {
        List<String> files = new ArrayList<>(Arrays.asList(context.getFilesDir().list()));
        for (ListIterator<String> iterator = files.listIterator(); iterator.hasNext(); ) {
            String file = iterator.next();
            if (!file.contains(POLYGON_EXTENSION)) {
                iterator.remove();
            } else {
                iterator.set(file.substring(0, file.lastIndexOf(".")));
            }
        }
        return files;
    }
}
