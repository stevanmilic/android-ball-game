package rs.etf.ms130329.ballgame.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/4/17.
 */

public class SettingsActivity extends Activity {

    public static final String KEY_PREF_BALL_RADIUS = "pref_ballRadius";
    public static final String KEY_PREF_OBSTACLE_WIDTH = "pref_obstacleWidth";
    public static final String KEY_PREF_OBSTACLE_HEIGHT = "pref_obstacleHeight";
    public static final String KEY_PREF_BLACK_HOLE_RADIUS = "pref_blackHoleRadius";
    public static final String KEY_PREF_WINNING_HOLE_RADIUS = "pref_winningHoleRadius";
    public static final String KEY_PREF_NO_OBSTACLES = "pref_noObstacles";
    public static final String KEY_PREF_NO_BLACK_HOLES = "pref_noBlackHoles";

    public static final String KEY_PREF_FRICTION_FACTOR = "pref_frictionFactor";
    public static final String KEY_PREF_COLLISION_FACTOR = "pref_collisionFactor";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.default_values:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                sharedPreferences.edit().clear().apply();
                PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
                getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
