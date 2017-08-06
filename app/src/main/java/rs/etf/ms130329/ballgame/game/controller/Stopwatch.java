package rs.etf.ms130329.ballgame.game.controller;

import android.os.SystemClock;

import java.io.Serializable;

/**
 * Created by stevan on 8/6/17.
 */

class Stopwatch implements Serializable{

   private static final long serialVersionUID = 100L;

    private long startTime;
    private long stopTime;

    private long pauseTime = 0;
    private long pauseTimeDuration = 0;

    private boolean running;

    void start() {
        startTime = SystemClock.elapsedRealtime();
        running = true;
    }

    void pause() {
        pauseTime = SystemClock.elapsedRealtime();
        running = false;
    }

    void resume() {
        if(pauseTime != 0) {
            pauseTimeDuration += SystemClock.elapsedRealtime() - pauseTime;
        }
        running = true;
    }

    void stop() {
        stopTime = SystemClock.elapsedRealtime();
        running = false;
    }

    long getElapsedTime() {
        if (running) {
            return SystemClock.elapsedRealtime() - startTime - pauseTimeDuration;
        }
        return stopTime - startTime - pauseTimeDuration;
    }

    double getElapsedTimeInSeconds() {
        double elapsedTimeInSeconds = getElapsedTime() / 1000.00f;
        return Math.round(elapsedTimeInSeconds * 100.0) / 100.0;
    }
}
