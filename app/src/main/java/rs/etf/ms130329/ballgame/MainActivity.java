package rs.etf.ms130329.ballgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rs.etf.ms130329.ballgame.game.controller.GameController;
import rs.etf.ms130329.ballgame.polygon.controller.PolygonController;
import rs.etf.ms130329.ballgame.polygon.model.PolygonModel;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.statistics.controller.StatisticsActivity;

public class MainActivity extends Activity {

    PolygonModel polygonModel;
    public static final String GAME_PARAMETER_KEY = "polygon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_game);
        polygonModel = new PolygonModel(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                polygonModel.getAllPolygons());

        ListView listView = (ListView) findViewById(R.id.polygon_list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> arrayAdapter = (ArrayAdapter) parent.getAdapter();
                String polygonName = arrayAdapter.getItem(position);
                polygonModel.deletePolygon(polygonName);
                arrayAdapter.remove(polygonName);
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> arrayAdapter = (ArrayAdapter) parent.getAdapter();
                Polygon polygon = polygonModel.importPolygonFromFile(arrayAdapter.getItem(position));
                Intent intent = new Intent(getApplicationContext(), GameController.class);
                intent.putExtra(GAME_PARAMETER_KEY, polygon);
                startActivity(intent);
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.new_polygon:
                intent = new Intent(this, PolygonController.class);
                startActivity(intent);
                return true;
            case R.id.statistics:
                intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
