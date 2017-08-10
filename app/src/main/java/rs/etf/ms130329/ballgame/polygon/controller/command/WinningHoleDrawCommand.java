package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.content.Context;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/10/17.
 */

public class WinningHoleDrawCommand extends OneTimeDrawCommand {

    public WinningHoleDrawCommand(Context context, Object receiver, String methodName, Class... parameterTypes) {
        super(context, receiver, methodName, parameterTypes);
        showInfo(context.getResources().getString(R.string.draw_winning_hole));
    }

    @Override
    int getButtonResId() {
        return R.id.draw_winning_hole_button;
    }

    @Override
    void addArguments() {
        getInput(context.getResources().getString(R.string.input_winning_hole_radius));
    }
}
