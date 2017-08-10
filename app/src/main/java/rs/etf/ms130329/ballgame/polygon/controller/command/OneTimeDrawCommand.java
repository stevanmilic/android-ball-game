package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.app.Activity;
import android.content.Context;
import android.widget.Button;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/10/17.
 */

abstract class OneTimeDrawCommand extends DrawCommand {

    private boolean drawed = false;

    OneTimeDrawCommand(Context context, Object receiver, String methodName, Class... parameterTypes) {
        super(context, receiver, methodName, parameterTypes);
    }

    abstract int getButtonResId();

    @Override
    public boolean execute() {
        if (drawed) {
            showInfo(context.getResources().getString(R.string.one_time_draw));
            return false;
        }


        if(!super.execute()) {
           return false;
        }

        drawed = true;

        Activity activity = (Activity) context;
        Button ballButton = activity.findViewById(getButtonResId());
        ballButton.setEnabled(false);

        return  drawed;
    }

}
