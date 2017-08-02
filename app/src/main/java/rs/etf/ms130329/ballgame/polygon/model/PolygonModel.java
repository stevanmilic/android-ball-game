package rs.etf.ms130329.ballgame.polygon.model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import rs.etf.ms130329.ballgame.engine.objects.Polygon;

/**
 * Created by stevan on 7/28/17.
 */

public class PolygonModel {

    private final static String POLYGON_EXTENSION = ".pyg";

    private Context context;

    public PolygonModel(Context context) {
        this.context = context;
    }

    public void exportPolygonToFile(Polygon polygon) {
        String fileName = polygon.getName() + POLYGON_EXTENSION;
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(polygon);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Polygon importPolygonFromFile(String name) {
        String fileName = name + POLYGON_EXTENSION;
        Polygon polygon = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            polygon = (Polygon) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return polygon;
    }

    public void deletePolygon(String name) {
        String fileName = name + POLYGON_EXTENSION;
        context.deleteFile(fileName);
    }

    public List<String> getAllPolygons() {
        List<String> files = new ArrayList<>(Arrays.asList(context.getFilesDir().list()));
        for (ListIterator<String> iterator = files.listIterator(); iterator.hasNext(); ) {
            String file = iterator.next();
            if (!file.contains(POLYGON_EXTENSION)) {
                iterator.remove();
            } else {
                iterator.set(file.substring(0, file.lastIndexOf(".")));
            }
        }
        return files;
    }
}
