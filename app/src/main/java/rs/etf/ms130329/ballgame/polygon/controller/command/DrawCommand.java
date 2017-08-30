package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.engine.physics.geometry.Point;

/**
 * Created by stevan on 8/10/17.
 */

public abstract class DrawCommand implements Command {

    Context context;
    private Object receiver;
    private Method action;

    private List<Object> arguments = new LinkedList<>();

    DrawCommand(Context context, Object receiver, String methodName, Class... parameterTypes) {
        this.context = context;
        this.receiver = receiver;
        try {
            action = receiver.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        AppCompatActivity compatActivity = (AppCompatActivity) context;
        compatActivity.getSupportActionBar().hide();
        addArguments();
    }

    abstract void addArguments();

    public void execute(Point point) {
        arguments.add(0, point.getPointX());
        arguments.add(1, point.getPointY());
        execute();
        arguments.remove(0);
        arguments.remove(0);
    }

    @Override
    public boolean execute() {

        boolean validDraw = false;
        try {
            validDraw = (boolean) action.invoke(receiver, arguments.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (!validDraw) {
            showInfo(context.getResources().getString(R.string.invalid_draw));
        }

        return  validDraw;
    }

    void getInput(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getResources().getString(R.string.input_dimension_dialog));

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint(message);
        builder.setView(input);

        builder.setPositiveButton(R.string.set, null);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = input.getText().toString();
                if (text.isEmpty()) {
                    showInfo(context.getResources().getString(R.string.empty_input));
                    return;
                }
                arguments.add(Integer.parseInt(text));
                dialog.dismiss();
            }
        });
    }

    void showInfo(String info) {
        Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        toast.show();
    }
}
