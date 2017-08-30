package rs.etf.ms130329.ballgame.game.controller;

/**
 * Created by stevan on 8/26/17.
 */

class LowPassFilter {

    private float oldValues[];
    private boolean noOldValues;
    private float alfa;

    LowPassFilter(float alfa) {
        this.alfa = alfa;
        oldValues = new float[3];
        noOldValues = true;
    }

    void filter(float[] values) {
        for (int i = 0; i < values.length; i++) {
            if (noOldValues) {
                oldValues[i] = values[i];
            } else {
                values[i] = values[i] * alfa + (1 - alfa) * oldValues[i];
                oldValues[i] = values[i];
            }
        }
        noOldValues = false;
    }

}
