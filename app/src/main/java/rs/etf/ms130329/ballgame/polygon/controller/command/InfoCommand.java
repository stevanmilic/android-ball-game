package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.content.Context;
import android.widget.Toast;

import rs.etf.ms130329.ballgame.R;

/**
 * Created by stevan on 8/10/17.
 */

public class InfoCommand implements Command {

    private Context context;

    public InfoCommand(Context context) {
        this.context = context;
    }

    @Override
    public boolean execute() {
        Toast toast = Toast.makeText(context, context.getResources().getString(R.string.toolbar_info), Toast.LENGTH_SHORT);
        toast.show();
        return  true;
    }
}
