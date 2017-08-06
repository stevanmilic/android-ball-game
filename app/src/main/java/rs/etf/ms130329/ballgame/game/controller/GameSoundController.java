package rs.etf.ms130329.ballgame.game.controller;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/5/17.
 */

class GameSoundController {

    private static final int SOUND_POOL_MAX_STREAMS = 4;

    private static final int BOUNCE_BOX_SOUND_ID = 0;
    private static final int BOUNCE_OBSTACLE_SOUND_ID = 1;
    private static final int BLACK_ENERGY_SOUND_ID = 2;
    private static final int ULTRA_VIOLET_SOUND_ID = 3;

    private static final float LEFT_VOLUME = 1.0f;
    private static final float RIGHT_VOLUME = 1.0f;

    private static final int BOUNCE_BOX_SOUND_PRIORITY = 0;
    private static final int BOUNCE_OBSTACLE_SOUND_PRIORITY = 1;
    private static final int BLACK_ENERGY_SOUND_PRIORITY = 2;
    private static final int ULTRA_VIOLET_SOUND_PRIORITY = 3;

    private static final int LOOP = 0;
    private static final float PLAYBACK_RATE = 1.0f;

    private SoundPool soundPool;

    private int[] soundIds;

    GameSoundController(Context context) {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(SOUND_POOL_MAX_STREAMS)
                .setAudioAttributes(audioAttributes)
                .build();

        soundIds = new int[SOUND_POOL_MAX_STREAMS];

        loadSound(BOUNCE_BOX_SOUND_ID, context, R.raw.bounce_box);
        loadSound(BOUNCE_OBSTACLE_SOUND_ID, context, R.raw.bounce_obstacle);
        loadSound(BLACK_ENERGY_SOUND_ID, context, R.raw.black_energy);
        loadSound(ULTRA_VIOLET_SOUND_ID, context, R.raw.ultra_violet);
    }

    void playBounceBoxSound() {
        soundPool.play(soundIds[BOUNCE_BOX_SOUND_ID], LEFT_VOLUME, RIGHT_VOLUME, BOUNCE_BOX_SOUND_PRIORITY, LOOP, PLAYBACK_RATE);
    }

    void playBounceObstacleSound() {
        soundPool.play(soundIds[BOUNCE_OBSTACLE_SOUND_ID], LEFT_VOLUME, RIGHT_VOLUME, BOUNCE_OBSTACLE_SOUND_PRIORITY, LOOP, PLAYBACK_RATE);

    }

    void playLosingSound() {
        soundPool.play(soundIds[BLACK_ENERGY_SOUND_ID], LEFT_VOLUME, RIGHT_VOLUME, BLACK_ENERGY_SOUND_PRIORITY, LOOP, PLAYBACK_RATE);
    }

    void playWinningSound() {
        soundPool.play(soundIds[ULTRA_VIOLET_SOUND_ID], LEFT_VOLUME, RIGHT_VOLUME, ULTRA_VIOLET_SOUND_PRIORITY, LOOP, PLAYBACK_RATE);
    }

    void releaseSoundPool() {
        soundPool.release();
    }

    private void loadSound(int soundId, Context context, int resId) {
        soundIds[soundId] = soundPool.load(context, resId, 1);
    }
}
