package rs.etf.ms130329.ballgame.statistics.controller;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.database.ScoreContract.ScoreEntry;
import rs.etf.ms130329.ballgame.game.controller.GameController;
import rs.etf.ms130329.ballgame.statistics.model.StatisticsModel;

public class StatisticsActivity extends Activity implements AdapterView.OnItemClickListener{

    private enum StatisticsState {
        POLYGON_LIST,
        POLYGON_STATISTICS,
        POLYGON_STATISTICS_AFTER_GAME
    }

    StatisticsState state = StatisticsState.POLYGON_LIST;

    private StatisticsModel statisticsModel;
    private String currentPolygonName;

    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        statisticsModel = new StatisticsModel(this);

        listView = (ListView) findViewById(R.id.statistics_polygon_list);

        textView = (TextView) findViewById(R.id.statistics_title);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setPolygonListState(this);
        } else {
            currentPolygonName = extras.getString(GameController.STATISTICS_PARAMETER_KEY);
            setPolygonStatisticsState(this, true);
        }

        listView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.statistics_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ListView listView;
        SimpleCursorAdapter simpleCursorAdapter;
        switch (item.getItemId()) {
            case R.id.reset_polygon_statistics:
                statisticsModel.deletePolygonStatistics(currentPolygonName);
                listView = (ListView) findViewById(R.id.statistics_polygon_list);
                simpleCursorAdapter = (SimpleCursorAdapter) listView.getAdapter();
                simpleCursorAdapter.swapCursor(statisticsModel.getPolygonStatisticCursor(currentPolygonName));
                simpleCursorAdapter.notifyDataSetChanged();
                return true;
            case R.id.reset_statistics:
                statisticsModel.deleteStatistics();
                listView = (ListView) findViewById(R.id.statistics_polygon_list);
                simpleCursorAdapter = (SimpleCursorAdapter) listView.getAdapter();
                simpleCursorAdapter.swapCursor(statisticsModel.getPolygonCursor());
                simpleCursorAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(state == StatisticsState.POLYGON_LIST) {
            menu.getItem(0).setEnabled(false);
        } else {
            menu.getItem(0).setEnabled(true);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(state == StatisticsState.POLYGON_LIST) {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            currentPolygonName = cursor.getString(cursor.getColumnIndex(ScoreEntry.COLUMN_NAME_POLYGON_NAME));
            setPolygonStatisticsState(parent.getContext(), false);
        }
    }

    @Override
    public void onBackPressed() {
        if(state == StatisticsState.POLYGON_STATISTICS) {
            setPolygonListState(this);
        } else {
            super.onBackPressed();
        }
    }

    private void setPolygonListState(Context context) {
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1,
                statisticsModel.getPolygonCursor(), new String[]{ScoreEntry.COLUMN_NAME_POLYGON_NAME},
                new int[]{android.R.id.text1}, 0);

        state = StatisticsState.POLYGON_LIST;

        listView.setAdapter(simpleCursorAdapter);
        textView.setText(getResources().getString(R.string.choose_polygon_statistics));
    }

    private void setPolygonStatisticsState(Context context, boolean fromGame) {
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.score_list_item,
                statisticsModel.getPolygonStatisticCursor(currentPolygonName),
                new String[]{ScoreEntry.COLUMN_NAME_PLAYER_NAME, ScoreEntry.COLUMN_NAME_TIME},
                new int[]{R.id.item_player_name, R.id.item_time}, 0);

        if(fromGame) {
            state = StatisticsState.POLYGON_STATISTICS_AFTER_GAME;
        } else {
            state = StatisticsState.POLYGON_STATISTICS;
        }

        listView.setAdapter(simpleCursorAdapter);
        textView.setText(currentPolygonName);
    }
}
