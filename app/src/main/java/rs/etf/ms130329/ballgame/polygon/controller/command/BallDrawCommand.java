package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.content.Context;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/10/17.
 */

public class BallDrawCommand extends OneTimeDrawCommand {

    public BallDrawCommand(Context context, Object receiver, String methodName, Class... parameterTypes) {
        super(context, receiver, methodName, parameterTypes);
        showInfo(context.getResources().getString(R.string.draw_ball));
    }

    @Override
    int getButtonResId() {
        return R.id.draw_ball_button;
    }

    @Override
    void addArguments() {
        getInput(context.getResources().getString(R.string.input_ball_radius));
    }

}
