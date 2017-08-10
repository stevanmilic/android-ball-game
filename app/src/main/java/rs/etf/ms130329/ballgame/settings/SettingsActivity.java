package rs.etf.ms130329.ballgame.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/4/17.
 */

public class SettingsActivity extends AppCompatActivity {

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
