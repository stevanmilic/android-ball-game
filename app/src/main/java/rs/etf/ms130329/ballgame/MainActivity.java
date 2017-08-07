package rs.etf.ms130329.ballgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rs.etf.ms130329.ballgame.database.ScoreDbHelper;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.game.controller.GameController;
import rs.etf.ms130329.ballgame.polygon.controller.PolygonController;
import rs.etf.ms130329.ballgame.polygon.model.PolygonModel;
import rs.etf.ms130329.ballgame.settings.SettingsActivity;
import rs.etf.ms130329.ballgame.statistics.controller.StatisticsActivity;
import rs.etf.ms130329.ballgame.statistics.model.StatisticsModel;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView
        .OnItemClickListener {

    PolygonModel polygonModel;
    StatisticsModel statisticsModel;
    public static final String GAME_PARAMETER_KEY = "polygon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_ball_game);

        polygonModel = new PolygonModel(this);
        statisticsModel = new StatisticsModel(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                polygonModel.getAllPolygons());

        ListView listView = (ListView) findViewById(R.id.polygon_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.polygon_list);
        ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(polygonModel.getAllPolygons());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        ScoreDbHelper.closeInstance();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.new_polygon:
                intent = new Intent(this, PolygonController.class);
                startActivity(intent);
                return true;
            case R.id.statistics:
                intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) parent.getAdapter();
        Polygon polygon = polygonModel.importPolygonFromFile(arrayAdapter.getItem(position));
        Intent intent = new Intent(getApplicationContext(), GameController.class);
        intent.putExtra(GAME_PARAMETER_KEY, polygon);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.delete_polygon_dialog);

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) parent.getAdapter();
                String polygonName = arrayAdapter.getItem(position);
                polygonModel.deletePolygon(polygonName);
                statisticsModel.deletePolygonStatistics(polygonName);
                arrayAdapter.remove(polygonName);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

        return true;
    }
}
