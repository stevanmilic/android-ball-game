package rs.etf.ms130329.ballgame.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.engine.objects.Collidable;
import rs.etf.ms130329.ballgame.engine.objects.Polygon;
import rs.etf.ms130329.ballgame.engine.physics.collision.Collision;
import rs.etf.ms130329.ballgame.engine.physics.motion.Acceleration;
import rs.etf.ms130329.ballgame.game.controller.BallStateObservable;

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

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public String getPolygonName() {
        return polygon.getName();
    }

    public synchronized void setBallToStartingPosition() {
        polygon.getBall().resetPosition();
        BallStateObservable.getInstance().setRunningState();
        startWorkerThread();
    }

    public synchronized void update(float[] s, float dT) {
        List<Collision> collisionList = new LinkedList<>();

        for (Collidable collidable : polygon.getCollidablesObjects()) {
            collidable.detectCollisions(collisionList, polygon.getBall());
        }

        Acceleration acceleration = new Acceleration(-s[0], -s[1]);

        BallStateObservable ballStateObservable = BallStateObservable.getInstance();

        for (Collision collision : collisionList) {
            collision.resolve(ballStateObservable);
        }

        if (ballStateObservable.getBallState() == BallStateObservable.BallState.RUNNING) {
            polygon.getBall().accelerate(acceleration, dT, collisionList);
        }

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

    public void startWorkerThread() {
        if (workerThread == null || !workerThread.isRunning()) {
            workerThread = new WorkerThread(getHolder());
            workerThread.setRunning(true);
            workerThread.start();
        }
    }

    public void stopWorkerThread() {
        if (workerThread != null && workerThread.isRunning()) {
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
