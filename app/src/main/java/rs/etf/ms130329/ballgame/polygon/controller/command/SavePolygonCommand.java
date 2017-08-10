package rs.etf.ms130329.ballgame.polygon.controller.command;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import rs.etf.ms130329.ballgame.R;
import rs.etf.ms130329.ballgame.polygon.model.PolygonModel;
import rs.etf.ms130329.ballgame.polygon.view.PolygonView;

/**
 * Created by stevan on 8/10/17.
 */

public class SavePolygonCommand implements Command {

    private Activity activity;
    private PolygonView polygonView;
    private PolygonModel polygonModel;

    public SavePolygonCommand(Activity activity, PolygonView polygonView, PolygonModel polygonModel) {
        this.activity = activity;
        this.polygonView = polygonView;
        this.polygonModel = polygonModel;
    }

    @Override
    public boolean execute() {
        if(polygonView.getPolygon().getBall() == null) {
            showInfo(activity.getResources().getString(R.string.no_ball_draw));
            return false;
        }
        if(polygonView.getPolygon().getWinningHole() == null) {
            showInfo(activity.getResources().getString(R.string.no_winning_hole_draw));
            return false;
        }
        showSaveDialog();
        return true;
    }

    private void showInfo(String info) {
        Toast toast = Toast.makeText(activity, info, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(activity.getResources().getString(R.string.save_polygon_dialog));

        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint(R.string.input_polygon_name);
        builder.setView(input);

        builder.setPositiveButton(R.string.save, null);

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String polygonName = input.getText().toString();
                if (polygonName.isEmpty()) {
                    showInfo(activity.getResources().getString(R.string.empty_input));
                    return;
                }
                polygonView.getPolygon().setName(polygonName);
                polygonModel.exportPolygonToFile(polygonView.getPolygon());
                dialog.dismiss();
                activity.finish();
            }
        });
    }

}
