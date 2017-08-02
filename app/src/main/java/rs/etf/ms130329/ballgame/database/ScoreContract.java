package rs.etf.ms130329.ballgame.database;

import android.provider.BaseColumns;

/**
 * Created by stevan on 8/1/17.
 */

public final class ScoreContract {

    private ScoreContract() {}

    public static class ScoreEntry implements BaseColumns{
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_NAME_POLYGON_NAME = "polygon_name";
        public static final String COLUMN_NAME_PLAYER_NAME = "player_name";
        public static final String COLUMN_NAME_TIME = "time";
    }
}
