package rs.etf.ms130329.ballgame.game.controller;

import java.util.Observable;

/**
 * Created by stevan on 8/5/17.
 */

public class BallStateObservable extends Observable {

    public enum BallState {
        IN_BLACK_HOLE,
        IN_WINNING_HOLE,
        COLLISION_BOX,
        COLLISION_OBSTACLE,
        RUNNING
    }

    private static BallStateObservable mInstance = null;

    private volatile BallState ballState = BallState.RUNNING;

    public static BallStateObservable getInstance() {
        if (mInstance == null) {
            mInstance = new BallStateObservable();
        }
        return mInstance;
    }

    public void setInBlackHoleState() {
        ballState = BallState.IN_BLACK_HOLE;
        setChanged();
        notifyObservers();
    }

    public void setInWinningHoleState() {
        ballState = BallState.IN_WINNING_HOLE;
        setChanged();
        notifyObservers();
    }

    public void setCollisionBoxState() {
        ballState = BallState.COLLISION_BOX;
        setChanged();
        notifyObservers();
    }

    public void setCollisionObstacleState() {
        ballState = BallState.COLLISION_OBSTACLE;
        setChanged();
        notifyObservers();
    }

    public void setRunningState(boolean newGame) {
        if((ballState == BallState.IN_BLACK_HOLE || ballState == BallState.IN_WINNING_HOLE ) && !newGame) {
            return;
        }
        ballState = BallState.RUNNING;
    }

    public BallState getBallState() {
        return ballState;
    }
}
