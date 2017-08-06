package rs.etf.ms130329.ballgame.engine.physics.collision;

import android.os.SystemClock;

import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 8/6/17.
 */

class CollisionMap {

    //TODO: Should be set from GameController(GAME_DELAY)
    private static final long DELAY = 200;

    private class Encounter {
        Point point;
        int id;
        long time;
        boolean ballGlued = false;

        private Encounter(Point point, int id, long time) {
            this.point = point;
            this.id = id;
            this.time = time;
        }

        private void setStatus(Point closest) {
            if (point != null && (point.getPointX() == closest.getPointX() || point.getPointY() == closest.getPointY())) {
                long lastClosestEncounter = SystemClock.elapsedRealtime() - time;
                ballGlued = lastClosestEncounter <= DELAY;
            } else {
                ballGlued = false;
            }

        }
    }

    private List<Encounter> encounters = new LinkedList<>();

    void newEncounter(Point closest, int id) {

        boolean exists = false;

        for (Encounter encounter : encounters) {
            if (encounter.id == id) {
                encounter.setStatus(closest);
                encounter.point = closest;
                encounter.time = SystemClock.elapsedRealtime();
                exists = true;
            }
        }

        if (!exists) {
            encounters.add(new Encounter(closest, id, SystemClock.elapsedRealtime()));
        }

    }

    boolean isBallGlued(int id) {
        for (Encounter encounter : encounters) {
            if (encounter.id == id && encounter.ballGlued) {
                return true;
            }
        }
        return false;
    }

}
