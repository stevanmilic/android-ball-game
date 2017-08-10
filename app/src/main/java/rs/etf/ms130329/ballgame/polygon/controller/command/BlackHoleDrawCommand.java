package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/10/17.
 */

public class BlackHoleDrawCommand extends DrawCommand {

    public BlackHoleDrawCommand(Context context, Object receiver, String methodName, Class... parameterTypes) {
        super(context, receiver, methodName, parameterTypes);
        showInfo(context.getResources().getString(R.string.draw_black_holes));
    }

    @Override
    void addArguments() {
        getInput(context.getResources().getString(R.string.input_black_hole_radius));
    }

}
