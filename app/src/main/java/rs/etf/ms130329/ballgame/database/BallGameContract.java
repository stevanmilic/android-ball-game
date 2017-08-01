package rs.etf.ms130329.ballgame.database;

/**
 * Created by stevan on 8/1/17.
 */

public final class BallGameContract {

    private BallGameContract() {}

    public static class ScoreEntry {
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_NAME_POLYGON_NAME = "polygon_name";
        public static final String COLUMN_NAME_PLAYER_NAME = "player_name";
        public static final String COLUMN_NAME_TIME = "time";
    }

    public static class PreferencesEntry {
        public static final String TABLE_NAE = "preferences";
        public static final String COLUMN_NAME_BALL_RADIUS = "ball_radius";
        public static final String COLUMN_NAME_OBSTACLE_WIDTH = "obstacle_width";
        public static final String COLUMN_NAME_OBSTACLE_HEIGHT = "obstacle_height";
        public static final String COLUMN_NAME_WINNING_HOLE_RADIUS = "winning_hole_radius";
        public static final String COLUMN_NAME_NO_OBSTACLES = "no_obstacles";
        public static final String COLUMN_NAME_NO_BLACK_HOLES = "no_black_holes";
        public static final String COLUMN_NAME_FRICTION_FACTOR = "friction_factor";
        public static final String COLUMN_NAME_COLLISION_FACTOR = "collision_factor";

    }
}
