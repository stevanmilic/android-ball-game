package rs.etf.ms130329.ballgame.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import rs.etf.ms130329.ballgame.engine.objects.BlackHole;
import rs.etf.ms130329.ballgame.engine.objects.Box;
import rs.etf.ms130329.ballgame.engine.objects.Hole;
import rs.etf.ms130329.ballgame.engine.objects.Obstacle;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.engine.physics.collision.BoxCollision;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.collision.ObstacleCollision;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.game.controller.GameController.GameState;

/**
 * Created by stevan on 8/3/17.
 */

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Polygon polygon;

    private WorkerThread workerThread;

    public GameSurfaceView(Context context, Polygon polygon) {
        super(context);
        this.polygon = polygon;
        getHolder().addCallback(this);
    }

    public String getPolygonName() {
        return polygon.getName();
    }

    public void setBallToStartingPosition() {
        polygon.getBall().resetPosition();
        startWorkerThread();
    }

    public GameState update(final float[] s, final float dT) {

        polygon.getWinningHole().setCollisionState(polygon.getBall());
        if (polygon.getWinningHole().getCollisionState() != Hole.CollisionState.NONE) {
            stopWorkerThread();
            return GameState.WON;
        } else {
            for (BlackHole blackHole : polygon.getBlackHoles()) {
                blackHole.setCollisionState(polygon.getBall());
                if (blackHole.getCollisionState() != Hole.CollisionState.NONE) {
                    stopWorkerThread();
                    return GameState.LOST;
                }
            }
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Acceleration acceleration = new Acceleration(-s[0], -s[1]);
                Collision collision = null;

                polygon.getBox().setCollisionState(polygon.getBall());
                if (polygon.getBox().getCollisionState() != Box.CollisionState.NONE) {
                    collision = new BoxCollision(polygon.getCollisionFactor(), polygon.getBall(), polygon.getBox());
                } else {
                    for (Obstacle obstacle : polygon.getObstacles()) {
                        obstacle.setCollisionState(polygon.getBall());
                        if (obstacle.getCollisionState() != Obstacle.CollisionState.NONE) {
                            collision = new ObstacleCollision(polygon.getCollisionFactor(), polygon.getBall(), obstacle);
                            break;
                        }
                    }
                }

                polygon.getBall().accelerate(acceleration, dT, polygon.getFrictionFactor(), collision);
            }
        });

        return GameState.RUNNING;
    }

    public void doDraw(Canvas canvas) {
        polygon.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startWorkerThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopWorkerThread();
    }

    private void startWorkerThread() {
        if(workerThread == null || !workerThread.isRunning()) {
            workerThread = new WorkerThread(getHolder());
            workerThread.setRunning(true);
            workerThread.start();
        }
    }

    private void stopWorkerThread() {
        if(workerThread != null && workerThread.isRunning()) {
            boolean retry = true;
            workerThread.setRunning(false);
            while (retry) {
                try {
                    workerThread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private class WorkerThread extends Thread {

        private final SurfaceHolder surfaceHolder;
        private volatile boolean running = false;

        WorkerThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        boolean isRunning() {
            return running;
        }

        void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        if (canvas != null) {
                            doDraw(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
